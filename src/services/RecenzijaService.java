package services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Korisnik;
import beans.Oglas;
import beans.Poruka;
import beans.Recenzija;
import dao.KorisnikDAO;
import dao.OglasiDAO;
import dao.PorukeDAO;
import dao.RecenzijaDAO;
import beans.Uloga;

@Path("")
public class RecenzijaService {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("recenzijaDAO") == null) {
	    	String contextPath = ctx.getRealPath("/");
			ctx.setAttribute("recenzijaDAO", new RecenzijaDAO(contextPath));
		}
		
		if (ctx.getAttribute("oglasDAO") == null) {
	    	String contextPath = ctx.getRealPath("/");
	    	//System.out.println(contextPath);			
			ctx.setAttribute("oglasDAO", new OglasiDAO(contextPath));
		}
		
		if (ctx.getAttribute("porukaDAO") == null) {
	    	String contextPath = ctx.getRealPath("/");
			ctx.setAttribute("porukaDAO", new PorukeDAO(contextPath));
		}
		
		if (ctx.getAttribute("korisnikDAO") == null) {
	    	String contextPath = ctx.getRealPath("/");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPath));
		}
	}
	
	@GET
	@Path("/izlistajRecenzije")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response recenzije (@Context HttpServletRequest request){
		
		RecenzijaDAO daoR= (RecenzijaDAO) ctx.getAttribute("recenzijaDAO");
		
		OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");
		
		HttpSession session = request.getSession();
		
		Korisnik k = (Korisnik) session.getAttribute("user");
		
		ArrayList<Recenzija> novaLista= new ArrayList<>();
		
		if ( k==null || !k.getUloga().equals(Uloga.PRODAVAC)){
			return Response.status(400).entity("Niste prijavljeni kao pordavac").build();
		}
	
		
		
		for (String s : k.getObjavljeniOglasi()) {
			
			if (!dao.getOglasi().get(s).getListaRecenzija().isEmpty()){  //ako moj oglas ima recenzije
				ArrayList<String> poOglasu = dao.getOglasi().get(s).getListaRecenzija();  //lista recenzija tog oglasa
				
				for (String r : poOglasu) {
					if(daoR.getRecenzije().get(r).isObrisan() == false){
						novaLista.add(daoR.getRecenzije().get(r));
					}
				}
			}
		}
		if (novaLista.isEmpty()){
			return Response.status(200).build();

		}else
			return Response.status(200).entity(novaLista).build();

	}
	
	@GET								//sve recenzije vezane za jednog korisnika
	@Path("/izlistajSveRecenzije")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response izlistajSve (@Context HttpServletRequest request){
		
		RecenzijaDAO dao= (RecenzijaDAO) ctx.getAttribute("recenzijaDAO");
		
		HttpSession session = request.getSession();
		Korisnik k= (Korisnik) session.getAttribute("user");
		
		ArrayList<Recenzija> recenzije = new ArrayList<>();
		for (Recenzija r : dao.getRecenzije().values()) {
			if (r.getRecenzent().equals(k.getId()) && r.isObrisan()==false){
				recenzije.add(r);
			}
		}
	
		return Response.status(200).entity(recenzije).build();

	}
	
	@POST
	@Path("/dodajR/{id}")
	@Consumes(value = {MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response Dodaj (@PathParam ("id") String id, @FormDataParam ("fajl") InputStream uploadedInputStream,
            @FormDataParam("Recenzija") String rec,
            @FormDataParam("slika") String ime,  @Context HttpServletRequest request ){

		RecenzijaDAO dao= (RecenzijaDAO) ctx.getAttribute("recenzijaDAO");	
		PorukeDAO daoP= (PorukeDAO) ctx.getAttribute("porukaDAO");
		KorisnikDAO daoK= (KorisnikDAO) ctx.getAttribute("korisnikDAO");	
		OglasiDAO daoO= (OglasiDAO) ctx.getAttribute("oglasDAO");
		
		Oglas o = daoO.getOglasi().get(id);
		HttpSession session = request.getSession();
		Korisnik k= (Korisnik) session.getAttribute("user");
				
		ObjectMapper mapper = new ObjectMapper();
		Recenzija r;
        try {
            r = mapper.readValue(rec, Recenzija.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
       
            return Response.status(500).build();
           
        }	
		
		if (!k.getUloga().equals(Uloga.KUPAC)){
			return Response.status(403).entity("niste ulogovani kao kupac").build();
					
		}
		//public Poruka(String id, String nazivOglasa, String posiljalac, String naslov, String datum, String sadrzaj) {
		// poruka
		 Poruka p= new Poruka();
		 p.setId(UUID.randomUUID().toString());
		 p.setNazivOglasa(o.getNaziv());
		 p.setPosiljalac(k.getKorisnickoIme());
		 p.setNaslov("Automatska poruka");
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
		 String datum = simpleDateFormat.format(new Date());
		 p.setDatum(datum);  
		
		if (r.getId().equals("")){
			
			if (!ime.equals("")){
    			writeToFile(uploadedInputStream, ctx.getRealPath("/")+ "/images/" + ime);
        		r.setSlika(ctx.getContextPath()+ "/images/" + ime );
    		}
			r.setId(UUID.randomUUID().toString());
			r.setRecenzent(k.getId());
			r.setOglas(id);
			p.setSadrzaj("Dodata je recenzija!");
			daoP.getPoruke().put(p.getId(), p);
			o.getListaRecenzija().add(r.getId());
			daoO.saveOglas();
			dao.getRecenzije().put(r.getId(), r);
			dao.saveRecenziju();
	
		}else {
			p.setSadrzaj("Izmenjena je recenzija!");
			daoP.getPoruke().put(p.getId(), p);
			if (!ime.equals("")){
    			writeToFile(uploadedInputStream, ctx.getRealPath("/")+ "/images/" + ime);
        		r.setSlika(ctx.getContextPath()+ "/images/" + ime );
			}
			dao.izmeni(r);	
		}
		
		daoP.savePoruku();
		
		HashMap<String, Korisnik> kor = new HashMap<String, Korisnik>();
		kor = daoK.getKorisnici();
		Korisnik novi = new Korisnik();
		
		for(Korisnik kr : kor.values()){
			if(kr.getId().equals(o.getKorisnik())){
				novi = kr;	
			}
		
		}

		//prodavac je obavesten da mu je neko recenzirao oglas
		novi.getPrimljenePoruke().add(p.getId());
		daoK.saveUser();
		return Response.status(200).entity(r).build();
	}
	
	
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@DELETE
	@Path("/obrisiRec/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response obrisi (@PathParam ("id") String id, @Context HttpServletRequest request){
		RecenzijaDAO dao= (RecenzijaDAO) ctx.getAttribute("recenzijaDAO");	
		
		Recenzija r = dao.izlistajPoIdu(id);
		r.setObrisan(true);
		dao.saveRecenziju();
		
		return Response.status(200).build();
	}

}
