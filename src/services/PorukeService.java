package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import beans.Korisnik;
import beans.Poruka;
import dao.KorisnikDAO;
import dao.PorukeDAO;

@Path("")
public class PorukeService {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
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
	@Path("/izlistajPoslate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response poslate (@Context HttpServletRequest request){
		PorukeDAO dao= (PorukeDAO) ctx.getAttribute("porukaDAO");		
		HttpSession session = request.getSession();
		
		Korisnik k= (Korisnik) session.getAttribute("user");
		
		if (k==null){
			return Response.status(401).entity("Niste prijavljeni").build();
		}	
		
		return Response.status(200).entity(dao.izlistajPoslate(k)).build();

	}
	
	@GET
	@Path("/izlistajPrimljene")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response primljene (@Context HttpServletRequest request){
		PorukeDAO dao= (PorukeDAO) ctx.getAttribute("porukaDAO");		
		HttpSession session = request.getSession();
		
		Korisnik k= (Korisnik) session.getAttribute("user");
		
		if (k==null){
			return Response.status(401).entity("niste prijavljeni").build();
		}	
		
		return Response.status(200).entity(dao.izlistajPrimljene(k)).build();

	}
	
	@DELETE
	@Path("/obrisiP/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	// dodaj ptoveru da li su poslete ili primljene da znas odakle da obrises
	public Response obrisi (@PathParam ("id") String id, @Context HttpServletRequest request){
		PorukeDAO dao= (PorukeDAO) ctx.getAttribute("porukaDAO");	
		KorisnikDAO daoK= (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		
		HttpSession session = request.getSession();
	
		Korisnik k= (Korisnik) session.getAttribute("user");
		
		
		if ( k==null){
			return Response.status(401).entity("niste prijavljeni").build();
		}
		Poruka o = dao.izlistajPoIdu(id);
		o.setObrisan(true);
		dao.savePoruku();
		daoK.saveUser();
		
		return Response.status(200).build();
	}

	@POST
	@Path("/dodajP/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Dodaj (Poruka p, @PathParam ("username") String username, @Context HttpServletRequest request ){
		// dodaj proveri da li ulogovan, dodaj u prodavcevu listu
		// DATUM POSTAVLJANJA

		PorukeDAO dao= (PorukeDAO) ctx.getAttribute("porukeDAO");
		KorisnikDAO daoK= (KorisnikDAO) ctx.getAttribute("korisnikDAO");
		Korisnik primalac = daoK.findUser(username);  //nadji toga kome saljes poruku
		if(primalac==null){
			return Response.status(404).entity("Ne postoji taj korisnik!").build();
		}
		HttpSession session = request.getSession();
		
		if (session.getAttribute("user")==null){
			return Response.status(401).entity("niste prijavljeni").build();
		}
		
		Korisnik k= (Korisnik) session.getAttribute("user");
		if (p.getId().equals("")){  //ne menjam je nego je saljem
			if (dao.dozvola(k, primalac)){
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String datum = dateFormat.format(new Date());
				p.setDatum(datum);
				p.setId(UUID.randomUUID().toString());
				p.setPosiljalac(k.getKorisnickoIme());
				k.getPoslatePoruke().add(p.getId());
				primalac.getPrimljenePoruke().add(p.getId());
				dao.getPoruke().put(p.getId(), p);
				dao.savePoruku();
				daoK.saveUser();
			} else {
				return Response.status(401).entity("ne mozete poslati poruku ovom korisniku").build();
			}
		} else {
			dao.izmeni(p);	
		}
		return Response.status(200).entity(k.getPoslatePoruke()).build();
	}
		

}
