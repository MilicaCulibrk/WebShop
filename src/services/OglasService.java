package services;

import java.util.Collection;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Oglas;
import dao.OglasDAO;


@Path("/")
public class OglasService {
	
	@Context
	ServletContext ctx;
	
	public OglasService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("oglasiDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("oglasiDAO", new OglasDAO(contextPath));
		}
	}
	
	@GET
	@Path("/oglasi")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Oglas> getRestorani() {
		OglasDAO dao = (OglasDAO) ctx.getAttribute("oglasiDAO");
		return dao.findAll();  //vraca sve, kolekciju oglasa
	}
	
	@GET
	@Path("/oglas/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Oglas getJedanOglas(@PathParam("id") UUID id) {
		OglasDAO dao = (OglasDAO) ctx.getAttribute("oglasiDAO");
		return dao.findRestoran(id);  //vraca jedan konkretan oglas
	}
	
	@POST
	@Path("/dodajOglas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dodajOglas(Oglas restoran, @Context HttpServletRequest request) {
		System.out.println(restoran);
		OglasDAO vozila = (OglasDAO) ctx.getAttribute("oglasiDAO");
		
		restoran.setIdOglas(UUID.randomUUID());
		
		String contextPath = ctx.getRealPath("");
		vozila.dodaj(restoran,contextPath);
		System.out.println(vozila);
		
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("/obrisiOglas/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") UUID id){
		System.out.println("knfska obrisi vozilo" + id);
		OglasDAO dao = (OglasDAO) ctx.getAttribute("OglasDAO");
		
	
		dao.deleteOglasi(ctx.getRealPath(""), id);
		
		dao.dodajuFile(dao.getOglasi(), ctx.getRealPath(""));
		return Response.status(200).build();

	} 
	
	@PUT   //menja
	@Path("/izmenaOglasa/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response proba(Oglas restoran, @Context HttpServletRequest request, @PathParam("id") UUID id){
		System.out.println("knfska proba vozilo" + id);
		OglasDAO dao = (OglasDAO) ctx.getAttribute("oglasiDAO");

		
		System.out.println("proba " + restoran);
		
		Oglas oglas = dao.findRestoran(id);
		
		oglas.setAktivan(restoran.isAktivan());
		oglas.setBrDislajkova(restoran.getBrDislajkova());
		oglas.setBrLajkova(restoran.getBrLajkova());
		oglas.setCena(restoran.getCena());
		oglas.setDatumIsticanja(restoran.getDatumIsticanja());
		oglas.setDatumPostavljanja(restoran.getDatumIsticanja());
		oglas.setGrad(restoran.getGrad());
		oglas.setNaziv(restoran.getNaziv());
		oglas.setOpis(restoran.getOpis());
		oglas.setRecenzije(restoran.getRecenzije());
		oglas.setSlika(restoran.getSlika());
		
		
		String contextPath = ctx.getRealPath("");
	
		
		dao.dodajuFile(dao.getOglasi(), contextPath); //da li staviti setAttribute
			
			
		System.out.println("izmena dao" +dao);
		
		return Response.status(200).build();

	}
	
}
