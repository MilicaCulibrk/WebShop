package services;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
import beans.Pretraga;
import dao.KorisnikDAO;
import dao.OglasiDAO;
import dao.PorukeDAO;
import beans.Status;
import beans.Uloga;

@Path("")
public class OglasiService {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("oglasDAO") == null) {
	    	String contextPath = ctx.getRealPath("/");
	    	//System.out.println(contextPath);			
			ctx.setAttribute("oglasDAO", new OglasiDAO(contextPath));
		}
		
		if (ctx.getAttribute("korisnikDAO") == null) {
	    	String contextPath = ctx.getRealPath("/");
			ctx.setAttribute("korisnikDAO", new KorisnikDAO(contextPath));
		}
		
		if (ctx.getAttribute("porukeDAO") == null) {
	    	String contextPath = ctx.getRealPath("/");
			ctx.setAttribute("porukeDAO", new PorukeDAO(contextPath));
		}
		
		
	
	}
	
	@POST
	@Path("/pretrazi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response pretragaa (Pretraga p ) throws ParseException{
		OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");
		ArrayList<Oglas> lista = dao.izlistaj();
		
		ArrayList<Oglas> odgNaziv = new ArrayList<>();
		ArrayList<Oglas> odgCena = new ArrayList<>();
		ArrayList<Oglas> odgOcena = new ArrayList<>();
		ArrayList<Oglas> odgGrad = new ArrayList<>();
		ArrayList<Oglas> odgDatum = new ArrayList<>();
		
		System.out.println(p.getNaziv());
		
		if (p.getNaziv()!=null && !p.getNaziv().equals("") ){
			for (Oglas oglas : lista) {
				if (oglas.getNaziv().equals(p.getNaziv())){
					odgNaziv.add(oglas);
				}
			}
		}else {
			odgNaziv=lista;
		}
		if (p.getMaxcena()!=null && p.getMincena()!=null && !p.getMaxcena().equals("") && !p.getMincena().equals("") ){
			for (Oglas oglas : odgNaziv) {
				if (oglas.getCena() <= Double.parseDouble(p.getMaxcena()) && oglas.getCena()>= Double.parseDouble(p.getMincena())){
					odgCena.add(oglas);
				}
			}
		}else {
			odgCena=odgNaziv;
		}
		if (p.getMaxocena()!=null && p.getMinocena()!=null && !p.getMaxocena().equals("") && !p.getMinocena().equals("") ){
			for (Oglas oglas : odgCena) {
				if (oglas.getBrLajkova() <= Integer.parseInt(p.getMaxocena()) && oglas.getBrLajkova()>= Integer.parseInt(p.getMinocena())){
					odgOcena.add(oglas);
				}
			}
		}else {
			odgOcena=odgCena;
		}
		
		if (p.getGrad()!=null && !p.getGrad().equals("") ){
			for (Oglas oglas : odgOcena) {
				if (oglas.getGrad().equals(p.getGrad())){
					odgGrad.add(oglas);
				}
			}
		}else {
			odgGrad=odgOcena;
		}
		if (p.getMinDatum()!=null && !p.getMinDatum().equals("") && p.getMaxDatum()!=null && !p.getMaxDatum().equals("")){
			//System.out.println(p.getMinDatum().replaceAll("-", "/"));
			//System.out.println(odgGrad.size());
			
			Date dateMin=new SimpleDateFormat("yyyy-MM-dd").parse(p.getMinDatum());
			Date dateMax=new SimpleDateFormat("yyyy-MM-dd").parse(p.getMaxDatum());
			
			for (Oglas oglas : odgGrad) {
				Date dateO=new SimpleDateFormat("yyyy-MM-dd").parse(oglas.getDatumIsticanja());
						
				if (dateO.before(dateMax) && dateO.after(dateMin)){
					odgDatum.add(oglas);
				}
			
			}
		}else {
			odgDatum=odgGrad;
		}
		
		for(Oglas o : odgDatum){
			System.out.println(o.getNaziv());
		}
		
		return Response.status(200).entity(odgDatum).build();

		}
	
		@GET
		@Path("/izlistajObjavljene")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response objavljeni (@Context HttpServletRequest request){
			OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");	
			
			HttpSession session = request.getSession();
			
			Korisnik k= (Korisnik) session.getAttribute("user");
			
			ArrayList<Oglas> izlistani= new ArrayList<>();
			
			if ( k==null || !k.getUloga().equals(Uloga.PRODAVAC)){ //nije ulogovan ili nije prodavac
				return Response.status(200).build();
			}
			
			for (String id : k.getObjavljeniOglasi() ){
				if (dao.getOglasi().get(id).isObrisan()==false)
						izlistani.add(dao.getOglasi().get(id));
				}
			return Response.status(200).entity(izlistani).build();	
	
		}
	
		@GET
		@Path("/izlistajPoKat/{kat}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response kat (@PathParam ("kat") String kat){
			
			OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");		
			ArrayList<Oglas> izlistani  = dao.izlistaPoKat(kat);
			
			//System.out.println("marija");
				
			for(Oglas o : izlistani){
				System.out.println(o.getNaziv());
			}
		
			return Response.status(200).entity(izlistani).build();
		}	
		
		@POST
		@Path("/dodajOmiljen/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response omiljen (@PathParam ("id") String id, @Context HttpServletRequest request ){
			OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");
			KorisnikDAO daoK = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
			Oglas o= dao.izlistaPoIdu(id);
			if (o==null){
				return Response.status(400).entity("Ne postoji").build();			
			}
			HttpSession session = request.getSession();		
			Korisnik k= (Korisnik) session.getAttribute("user");
			
			if (k==null){
				return Response.status(401).entity("niste prijavljeni").build();
			}
			if (k.postojiOmiljeni(id)==false){   //ako mu to vec nije omiljeni
				o.setOmiljen(o.getOmiljen()+1);
				System.out.println(o.getOmiljen());
				k.getOmiljeniOglasi().add(id);
				dao.saveOglas();
				daoK.saveUser();
				return Response.status(200).entity(k.getOmiljeniOglasi()).build();
			} else
				return Response.status(401).entity("vec je dodat u omiljene").build();
				
		}
		
		@GET
		@Path("/izlistajSve")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response kat (){
			
			
			OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");		
		
			return Response.status(200).entity(dao.izlistaj()).build();
		}	

		@GET
		@Path("/izlistajtop9")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response top9 (){
			OglasiDAO dao = (OglasiDAO) ctx.getAttribute("oglasDAO");	
			ArrayList<Oglas> sortLista= new ArrayList<Oglas>();
			for (Oglas oglas : dao.getOglasi().values()) {
				if (oglas.isObrisan() == false){
					sortLista.add(oglas);
				}
			}
			sortLista.sort(Comparator.comparingInt(Oglas::getOmiljen).reversed());
			if (sortLista.size()<=10){
				for(Oglas o : sortLista){
					System.out.println(o.getNaziv());
				}
				return Response.status(200).entity(sortLista).build();
			}else {
				ArrayList<Oglas> top = new ArrayList<Oglas>(sortLista.subList(0, 10));
				for(Oglas o : top){
					System.out.println(o.getNaziv());
				}
				return Response.status(200).entity(top).build();	
				
			}

		}
		
		@POST
		@Path("/lajkuj/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response like (@PathParam ("id") String id, @Context HttpServletRequest request ){
			OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");
			
			KorisnikDAO daoK = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
			
			Oglas o=dao.getOglasi().get(id);  //uzmi lajkovan oglas
			
			HttpSession session = request.getSession();
			
			Korisnik k= (Korisnik) session.getAttribute("user");	//uzmi logovanog usera
			
			if (k==null){
			
				return Response.status(401).entity("Niste prijavljeni!").build();
				
			}
			
			if (daoK.lajkovao(id, k)){
			
				return Response.status(401).entity("Vec ste lajkovali oglas!").build();
					
			}
			
			if (daoK.dislajkovao(id, k)){
				System.out.println("haaaaaaaaaaaahahahhaah");
				o.setBrDislajkova(o.getBrDislajkova( ) - 1);
				k.getDislajkovaniOglasi().remove(o.getId()); 
					
			}
			
			o.setBrLajkova(o.getBrLajkova()+1);
			k.getLajkovaniOglasi().add(o.getId()); 
			dao.saveOglas();
			daoK.saveUser();
			return Response.status(200).entity(o.getBrLajkova()).build();
		}
		
		@POST
		@Path("/dislajkuj/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response dilike (@PathParam ("id") String id, @Context HttpServletRequest request ){
			OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");
			
			KorisnikDAO daoK = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
			
			Oglas o=dao.getOglasi().get(id);
			
			HttpSession session = request.getSession();
			
			Korisnik k= (Korisnik) session.getAttribute("user");
			
			if (k==null){
				return Response.status(401).entity("Niste prijavljeni!").build();
			}
			
			if (daoK.dislajkovao(id, k)){
				
				return Response.status(401).entity("Vec ste dislajkovali oglas!").build();
					
			}
			
			if (daoK.lajkovao(id, k)){
				System.out.println("haaaaaaaaaaaahahahhaah");
				o.setBrLajkova(o.getBrLajkova( ) - 1);
				k.getLajkovaniOglasi().remove(o.getId()); 
					
			}
			
			
			
			k.getDislajkovaniOglasi().add(o.getId());
			o.setBrDislajkova(o.getBrDislajkova()+1);
			dao.saveOglas();
			daoK.saveUser();
			return Response.status(200).entity(o.getBrDislajkova()).build();
		}
		
		@POST
	    @Path("/dodajO")
	    @Consumes(value = {MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
	    @Produces(MediaType.APPLICATION_JSON)
	    public Response DodajOglas (@FormDataParam ("fajl") InputStream uploadedInputStream,
	            @FormDataParam("Oglas") String ogl,
	            @FormDataParam("slika") String ime,  @Context HttpServletRequest request ){
		
			OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO"); 
	    	KorisnikDAO daoK = (KorisnikDAO) ctx.getAttribute("korisnikDAO");		
	    	PorukeDAO daoP = (PorukeDAO) ctx.getAttribute("porukeDAO");
	    	
	    	ObjectMapper mapper = new ObjectMapper();
	        Oglas o;
	        try {
	            o = mapper.readValue(ogl, Oglas.class);
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	       
	            return Response.status(500).build();
	           
	        }
	        
	        HttpSession session = request.getSession();
	       
	        if (session.getAttribute("user")==null){
	            return Response.status(401).entity("niste prijavljeni").build();
	        }
	       // o.setSlika(ctx.getContextPath()+ "/images/" + ime );
	        Korisnik k= (Korisnik) session.getAttribute("user");
	        if (dao.izlistaPoIdu(o.getId()) == null){  
	        		if (!k.getUloga().equals(Uloga.PRODAVAC)){
	        			    return Response.status(403).entity("Morate biti pridavac").build();        
	        	    }
	        		
	        		
	        		writeToFile(uploadedInputStream, ctx.getRealPath("/")+ "/images/" + ime);
	        		o.setSlika(ctx.getContextPath()+ "/images/" + ime );
	        		
	        		
	                o.setId(UUID.randomUUID().toString());         
	                DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
	                String datum = dateFormat.format(new Date());
	                o.setDatumPostavljanja(datum);
	                o.setStatus(Status.AKTIVAN);
	                dao.getOglasi().put(o.getId(), o);
	                k.getObjavljeniOglasi().add(o.getId()); 
	        		o.setKorisnik(k.getId());
	        		dao.saveOglas();
	        		daoK.saveUser();
	        } else {
	        		if (!ime.equals("")){
	        			writeToFile(uploadedInputStream, ctx.getRealPath("/")+ "/images/" + ime);  //sta je ovo?
	            		o.setSlika(ctx.getContextPath()+ "/images/" + ime );
	        		}
	                Oglas oglas = dao.izmeni(o); 
	                System.out.println("ogl: " + oglas.getNaziv());
	                if (k.getUloga().equals(Uloga.ADMINISTRATOR)){
	                	Poruka p= new Poruka();
	            		p.setId(UUID.randomUUID().toString());
	            		p.setNazivOglasa(oglas.getNaziv());
	            		p.setPosiljalac(k.getKorisnickoIme());
	            		p.setNaslov("Automatska poruka");
	            		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
	            		String datum = simpleDateFormat.format(new Date());
	            		p.setDatum(datum);    
	            		p.setSadrzaj("Oglas je izmenjen!");
	            		daoP.getPoruke().put(p.getId(), p);
	            		daoP.savePoruku();
	            		
	            		HashMap<String, Korisnik> kor = new HashMap<String, Korisnik>();
	    				kor = daoK.getKorisnici();
	    				Korisnik novi = new Korisnik();
	            		
	            		for(Korisnik kr : kor.values()){
	    					if(kr.getId().equals(o.getKorisnik()))
	    					novi = kr;	
	    				}
	    				
	  
		    				novi.getPrimljenePoruke().add(p.getId());  // prodavcu stize poruka ako je admin obrisao
	            		
	            	
	            		System.out.println("br primljenik: " + novi.getPrimljenePoruke().size() );
	            		System.out.println("id je: " + p.getId());
	            		daoK.saveUser();
	    	    }
	       }
	        return Response.status(200).entity(o).build();
		}
	
	    	private void writeToFile(InputStream uploadedInputStream,

	    			String uploadedFileLocation) {
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
	    	
	    	@POST
	    	@Path("/filtriraj/{status}")
	    	@Consumes(MediaType.APPLICATION_JSON)
	    	@Produces(MediaType.APPLICATION_JSON)
	    	public Response filtriraj (@PathParam ("status") Status st, @Context HttpServletRequest request ){
	    		OglasiDAO dao = (OglasiDAO) ctx.getAttribute("oglasDAO");
	    		
	    		HttpSession session = request.getSession();
	    		
	    		Korisnik k= (Korisnik) session.getAttribute("user");
	    		
	    		ArrayList<Oglas> izlistani = new ArrayList<>();
	    		
	    		
	    			for (String oglas : k.getObjavljeniOglasi()) {
	    				if (dao.getOglasi().get(oglas).getStatus().equals(st)){
	    					izlistani.add(dao.getOglasi().get(oglas));
	    				}
	    			}
	    	
	    		
	    		return Response.status(200).entity(izlistani).build();
	    	}
	    	
	    	@DELETE
	    	@Path("/obrisi/{id}")
	    	@Consumes(MediaType.APPLICATION_JSON)
	    	@Produces(MediaType.APPLICATION_JSON)
	    	public Response obrisi (@PathParam ("id") String id, @Context HttpServletRequest request){
	    		OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");			
	    		
	    		HttpSession session = request.getSession();
	    	
	    		Korisnik k= (Korisnik) session.getAttribute("user");
	    		PorukeDAO daoP= (PorukeDAO) ctx.getAttribute("porukeDAO");
	    		KorisnikDAO daoK= (KorisnikDAO) ctx.getAttribute("korisnikDAO");
	    		
	    		
	    		
	    		//public Poruka(String id, String nazivOglasa, String posiljalac, String naslov, String datum, String sadrzaj) {
	    				
	    		if (k.getUloga().equals(Uloga.PRODAVAC) || k.getUloga().equals(Uloga.ADMINISTRATOR)){
	    			
	    			 Oglas o = dao.getOglasi().get(id) ;  //uzmemo oglas koji zelimo da obrisemo
	    			 o.setObrisan(true);
	    			// automatska poruka koja se salje	
	    			 Poruka p = new Poruka();
	    			 p.setId(UUID.randomUUID().toString());
	    			 p.setNazivOglasa(o.getNaziv());
	    			 p.setPosiljalac(k.getKorisnickoIme());  //taj prodavac??
	    			 p.setNaslov("Automatska poruka");
	    			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
	    			 String datum = simpleDateFormat.format(new Date());
	    			 p.setDatum(datum);          
	    			if (k.getUloga().equals(Uloga.PRODAVAC)){
	    				p.setSadrzaj("Prodavac je obrisao ovaj oglas!");
	    				Korisnik kor = daoK.findUser("admin");
	    				kor.getPrimljenePoruke().add(p.getId());  //adminu stize poruka ako je prodavac obrisao
	    					
	    			}else {
	    				p.setSadrzaj("Administrator je obrisao ovaj oglas!");

	    				HashMap<String, Korisnik> kor = new HashMap<String, Korisnik>();
	    				kor = daoK.getKorisnici();
	    				Korisnik novi = new Korisnik();
	    				
	    				for(Korisnik kr : kor.values()){
	    					if(kr.getId().equals(o.getKorisnik()))
	    					novi = kr;	
	    				}
	    				
	  
		    				novi.getPrimljenePoruke().add(p.getId());  // prodavcu stize poruka ako je admin obrisao
		    				
	    			}
	    					
	    			
	    			if(daoP.getPoruke() != null){
	    				 daoP.getPoruke().put(p.getId(), p);	
	    			}else{
	    				System.out.println("MINA MARAAAAAAAAAAS");
	    			}
	    			
	    		
	    		} 
	    		daoP.savePoruku();
	    		daoK.saveUser(); 
	    		dao.saveOglas();
	    		return Response.status(200).build();
	    	}
	    	

		@GET
		@Path("/izlistajDostavljene")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response dostaveljeno (@Context HttpServletRequest request){
			
			OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");	
			
			HttpSession session = request.getSession();
			Korisnik k= (Korisnik) session.getAttribute("user");
			
			ArrayList<Oglas> izlistani = new ArrayList<>();
			for (String id : k.getDostavljeniProizvodi()) {
					izlistani.add(dao.getOglasi().get(id));
			}
			return Response.status(200).entity(izlistani).build();	
		
		}
		@GET
		@Path("/izlistajOmiljene")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response omiljeni (@Context HttpServletRequest request){
			OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");	
			HttpSession session = request.getSession();
			Korisnik k= (Korisnik) session.getAttribute("user");
			ArrayList<Oglas> izlistani= new ArrayList<>();
			for (String id : k.getOmiljeniOglasi()) {
				if (dao.getOglasi().get(id).isObrisan()==false){
					izlistani.add(dao.getOglasi().get(id));
				}
			}
			return Response.status(200).entity(izlistani).build();	
			
			}
		
		@GET
		@Path("/izlistajPorucene")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response poruceni (@Context HttpServletRequest request){
			
			OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");	
			
			HttpSession session = request.getSession();
			Korisnik k= (Korisnik) session.getAttribute("user");
			
			ArrayList<Oglas> izlistani= new ArrayList<>();
			for (String id : k.getPoruceniProizvodi()) {
					izlistani.add(dao.getOglasi().get(id));
			}
			return Response.status(200).entity(izlistani).build();	
			
			}
		
		@POST
		@Path("/poruci/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response poruci (@PathParam ("id") String id, @Context HttpServletRequest request ){
			OglasiDAO dao = (OglasiDAO) ctx.getAttribute("oglasDAO");
			 
		    KorisnikDAO daoK = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
			
			Oglas o = dao.izlistaPoIdu(id);
			if (o == null){
				return Response.status(400).entity("nepostoji").build();			
			}
			HttpSession session = request.getSession();
			
			Korisnik k= (Korisnik) session.getAttribute("user");
			
			if (k==null){
				return Response.status(401).entity("niste prijavljeni").build();
			}
			
			o.setStatus(Status.UREALIZACIJI);
			o.setObrisan(true);
			k.getPoruceniProizvodi().add(o.getId());
			dao.saveOglas();
			daoK.saveUser();
			return Response.status(200).entity(k.getPoruceniProizvodi()).build();
		}
		
		@POST
		@Path("/dostavljeno/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response dostavi (@PathParam ("id") String id, @Context HttpServletRequest request ){
			OglasiDAO dao= (OglasiDAO) ctx.getAttribute("oglasDAO");
			
			KorisnikDAO daoK = (KorisnikDAO) ctx.getAttribute("korisnikDAO");
			
			PorukeDAO daoP = (PorukeDAO) ctx.getAttribute("porukaDAO");
			
			Oglas o= dao.getOglasi().get(id);
			
			HttpSession session = request.getSession();
			Korisnik k= (Korisnik) session.getAttribute("user");
			
			//public Poruka(String id, String nazivOglasa, String posiljalac, String naslov, String datum, String sadrzaj) {
			Poruka p= new Poruka();
			p.setId(UUID.randomUUID().toString());
			p.setNazivOglasa(o.getNaziv());
			p.setPosiljalac(k.getKorisnickoIme());
			p.setNaslov("Automatska poruka");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
			String datum = simpleDateFormat.format(new Date());
			p.setDatum(datum);    
			p.setSadrzaj("Proizvod je dostavljen!");
			daoP.getPoruke().put(p.getId(), p);
			
			HashMap<String, Korisnik> kor = new HashMap<String, Korisnik>();
			kor = daoK.getKorisnici();
			Korisnik novi = new Korisnik();
			
			for(Korisnik kr : kor.values()){
				if(kr.getId().equals(o.getKorisnik()))
				novi = kr;	
			}
			

			novi.getPrimljenePoruke().add(p.getId());  // prodavcu stize poruka ako je admin obrisao
			
			o.setStatus(Status.DOSTAVLJEN);
			//o.setObrisan(true);
			k.getDostavljeniProizvodi().add(o.getId());
			k.getPoruceniProizvodi().remove(o.getId());
			dao.saveOglas();
			daoK.saveUser();
			daoP.savePoruku();
			
			return Response.status(200).entity(k.getDostavljeniProizvodi()).build();
		}
	    	
	
}
