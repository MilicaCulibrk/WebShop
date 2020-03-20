package services;

import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;
import beans.Korisnik;
import beans.Oglas;
import beans.Poruka;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;


import dao.KorisnikDAO;
import dao.OglasiDAO;
import dao.PorukeDAO;
import beans.Uloga;


@Path("")	 //??
public class KorisnikService {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("korisnikDAO") == null) {
	    	String contextPath = ctx.getRealPath("/");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPath));
		}
		
		if (ctx.getAttribute("oglasDAO") == null) {
	    	String contextPath = ctx.getRealPath("/");
			ctx.setAttribute("oglasDAO", new KorisnikDAO(contextPath));
		}
		
		if (ctx.getAttribute("parukaDAO") == null) {
	    	String contextPath = ctx.getRealPath("/");
			ctx.setAttribute("porukaDAO", new PorukeDAO(contextPath));
		}
		
	}
	
	@POST
	@Path("/registracija")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registracija(Korisnik korisnik){
		
		System.out.println(korisnik);
		
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		if (dao.exists(korisnik)){
			return Response.status(401).entity("Vec postoji korisnik sa istim korisnickim imenom ili lozinkom!").build();
		} else{
			dao.addUser(korisnik);
			System.out.println("proslo");
			return Response.status(200).build();
		}
	}
	
	@POST
	@Path("/izmeniPrava")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response izmena (Korisnik korisnik, @Context HttpServletRequest request){
		
		Korisnik k = (Korisnik) request.getSession().getAttribute("user");
		if ( !k.getUloga().equals(Uloga.ADMINISTRATOR)) {
			
			return Response.status(402).entity("niste admin").build();
		} else{
			KorisnikDAO dao= (KorisnikDAO) ctx.getAttribute("korisnikDAO");		
			Korisnik kor = dao.findUser(korisnik.getKorisnickoIme());
			kor.setUloga(korisnik.getUloga()); 
			if (korisnik.getUloga().equals(Uloga.PRODAVAC)){		//jel treba za kupca da ih obrisem??
				kor.setObjavljeniOglasi(new ArrayList<>());		//dodaj mu liste, sad je prodavac
				kor.setIsporuceniProizvodi(new ArrayList<>());
			}
			dao.saveUser();
			return Response.status(200).entity(kor).build();
			
		}
	}
	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Korisnik korisnik, @Context HttpServletRequest request){ //zasto ovde i request??
		
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		
		HttpSession session = request.getSession(); // postavi mu sesiju
		
		if (session.getAttribute("user") != null) {
			
			return Response.status(402).entity("Vec ste prijavljeni.").build();
		}
		
		
		Korisnik k = dao.findUser(korisnik.getKorisnickoIme());  //pronadji ga
		
		
		if (k!=null && k.getLozinka().equals(korisnik.getLozinka())){  //ako smo ga nasli
			session.setAttribute("user", k);
			return Response.status(200).entity(k).build();
		}else{
			return Response.status(401).entity("Neispravno korisnicko ime i/ili lozinka.").build();
		}
		
	}
	
	
	
	@POST
	@Path("/logout")
	public Response logout(@Context HttpServletRequest request){
		request.getSession().invalidate();
		return Response.status(200).build();
			
	}
	
	@POST		//zasto je ovo post zahtev
	@Path("/getUloga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUloga (Korisnik korisnik, @Context HttpServletRequest request){  //kako ovde imamo korisnika
		
		HttpSession session = request.getSession();
		Korisnik k = (Korisnik) session.getAttribute("user");
		   
	        if (session.getAttribute("user")==null){
	            return Response.status(200).entity("Niste ulogovani").build();
	        }
	        return Response.status(200).entity(k.getUloga().toString()).build();
	        
	}
	
	@GET
	@Path("/getUlogovanog")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ulogovan (@Context HttpServletRequest request){
		
		HttpSession session = request.getSession();
		
		Korisnik k = (Korisnik) session.getAttribute("user");
		
		KorisnikDAO dao = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		
		OglasiDAO daoO = (OglasiDAO) ctx.getAttribute("oglasDAO");
		
		int pozitivne = 0;
		int negativne = 0;
		
		for (String o : k.getObjavljeniOglasi()) {		//za svaki oglas koji sam objavio izbroj koliko imam lajkova i dislajkova
			pozitivne += daoO.getOglasi().get(o).getBrLajkova();
			negativne += daoO.getOglasi().get(o).getBrDislajkova();
			
		}
		
		k.setLikes(pozitivne);
		k.setDislikes(negativne);
		dao.saveUser();
		
		
		return Response.status(200).entity(k).build();


	}
	
	@GET
	@Path("/izlistajPrijave")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response prijave (){
		KorisnikDAO dao= (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		ArrayList<Korisnik> sumnjivi = new ArrayList<>();
		
		for (Korisnik korisnik : dao.getKorisnici().values()) {
			if (korisnik.getPrijave()>3){
				sumnjivi.add(korisnik);
			}
		}
		
		return Response.status(200).entity(sumnjivi).build();

	}
	
	@GET
	@Path("/izlistajSveKorisnike")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response izlistajSve (){
		KorisnikDAO dao= (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		
		return Response.status(200).entity(dao.getKorisnici().values()).build();

	}
	
	@POST
	@Path("/refresuj/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response refresh (@PathParam ("id") String id ){
		KorisnikDAO daoK = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		
		HashMap<String, Korisnik> kor = new HashMap<String, Korisnik>();
		kor = daoK.getKorisnici();
		Korisnik novi = new Korisnik();
		
		for(Korisnik kr : kor.values()){
			if(kr.getId().equals(id)){
				novi = kr;	
			}
		
		}
		

		novi.setPrijave(0);
		daoK.saveUser();
		
		return Response.status(200).entity(novi).build();
	}
	
	@POST
	@Path("/prijavi/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOglas (@PathParam ("id") String id ){
		OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");
		
		KorisnikDAO daoK = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		
		PorukeDAO daoP = (PorukeDAO) ctx.getAttribute("porukaDAO");
		
		Oglas o = dao.getOglasi().get(id);
		
		HashMap<String, Korisnik> kor = new HashMap<String, Korisnik>();
		kor = daoK.getKorisnici();
		Korisnik novi = new Korisnik();
		
		for(Korisnik kr : kor.values()){
			if(kr.getId().equals(o.getKorisnik())){
				novi = kr;	
			}
		
		}
			
		novi.setPrijave(novi.getPrijave()+1);
		
		//public Poruka(String id, String nazivOglasa, String posiljalac, String naslov, String datum, String sadrzaj) {
				Poruka p= new Poruka();
				p.setId(UUID.randomUUID().toString());
				p.setNazivOglasa(o.getNaziv());
				p.setPosiljalac("admin");
				p.setNaslov("Automatska poruka");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
				String datum = simpleDateFormat.format(new Date());
				p.setDatum(datum);    
				p.setSadrzaj("UPOZORENJE!!! Vas oglas je prijavljen.");
				daoP.getPoruke().put(p.getId(), p);
				novi.getPrimljenePoruke().add(p.getId());
				
				daoK.saveUser();
				daoP.savePoruku();
		
		return Response.status(200).entity(novi).build();
	}
	
	@GET
	@Path("/jelUlogovan")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response jelUlogovan (@Context HttpServletRequest request){
		
		HttpSession session = request.getSession();
		
		Korisnik k = (Korisnik) session.getAttribute("user");
		
		if(k == null){
			return Response.status(200).entity(false).build();
		}else{
			return Response.status(200).entity(true).build();
		}
		
		

	}
	

}
