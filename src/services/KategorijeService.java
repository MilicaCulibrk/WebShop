package services;

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

import beans.Kategorija;
import beans.Korisnik;
import dao.KategorijeDAO;
import beans.Uloga;

@Path("")
public class KategorijeService {
	
	@Context
	ServletContext ctx;

	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("kategorijaDAO") == null) {
	    	String contextPath = ctx.getRealPath("/");
			ctx.setAttribute("kategorijaDAO", new KategorijeDAO(contextPath));
		}
	}
	
	@GET
	@Path("/izlistajSveKategorije")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response izlistajSves (){
		System.out.println("Milica");
		KategorijeDAO dao= (KategorijeDAO) ctx.getAttribute("kategorijaDAO");	
		
	
		return Response.status(200).entity(dao.izlistaj()).build();

	}
	
	@POST
	@Path("/dodajK")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Dodaj (Kategorija kat, @Context HttpServletRequest request ){
		// dodaj proveri da li ulogovan, dodaj u prodavcevu listu
		// DATUM POSTAVLJANJA
		
		KategorijeDAO dao= (KategorijeDAO) ctx.getAttribute("kategorijaDAO");		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("user")==null){
			return Response.status(401).entity("niste prijavljeni").build();
		}
		
		Korisnik k= (Korisnik) session.getAttribute("user");
		if (k.getUloga().equals(Uloga.ADMINISTRATOR))
		{
			if (kat.getId()==null){
				kat.setId(UUID.randomUUID().toString());		
				dao.getKategorije().put(kat.getId(), kat);
				dao.saveKategoriju(); 
			} else {
				dao.izmeni(kat);
			}
		} else {
			return Response.status(403).entity("Morate biti administrator").build();			
		}
		
		return Response.status(200).entity(kat).build();
	}
	
	@DELETE
	@Path("/obrisiK/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response obrisi (@PathParam ("id") String id, @Context HttpServletRequest request){
		KategorijeDAO dao= (KategorijeDAO) ctx.getAttribute("kategorijaDAO");	
		HttpSession session = request.getSession();
	
		Korisnik k= (Korisnik) session.getAttribute("user");
		
		
		if ( k==null || !k.getUloga().equals(Uloga.ADMINISTRATOR)){
			return Response.status(401).entity("niste prijavljeni").build();
		}
		
		Kategorija kat = dao.izlistajPoIdu(id);  //nadji kategoriju
		kat.setObrisan(true);
		dao.saveKategoriju();
		
		return Response.status(200).build();
	}
	


}
