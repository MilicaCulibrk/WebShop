package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Korisnik;
import dao.UserDAO;
import java.util.regex.Pattern;

@Path("")
public class RegisterService {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira viï¿½e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("userDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new UserDAO(contextPath));
		}
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(Korisnik user, @Context HttpServletRequest request) {
		System.out.println(user);
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		boolean loggedUser = userDao.find(user.getUsername());
		System.out.println(loggedUser+"register klasa");
		if (loggedUser== true) {
			//return Response.status(400).entity("Korisnicko ime vec postoji!").build();
			return Response.status(400).build();
		}
		
		boolean ime=isValidExpression(user.getIme());
		boolean prezime=isValidExpression(user.getPrezime());
		boolean broj=isValidNumber(user.getTelefon());
		
		if(!ime) return Response.status(401).build();
		if(!prezime) return Response.status(402).build();
		if(!broj) return Response.status(403).build();
		
		String contextPath = ctx.getRealPath("");
		userDao.dodaj(user,contextPath);
		System.out.println(userDao);
		
		return Response.status(200).build();
	}
	
	private boolean isValidExpression(String word) {
		String regex = "^[\\p{L} ]*$";
		return Pattern.matches(regex, word);
	}
	
	private boolean isValidNumber(String word) {
		String regex = "^[0-9]{3,20}$";
		return Pattern.matches(regex, word);
	}
	
	/*
	 * 
	 * ako menjas dostavljaca u:
- kupca - sve zajedničke podatke prebacujes, omiljene rest. i porudžbine kreiras kao prazne liste
- admina - sve zajedničke podatke prebacujes

ako menjas kupca u:
- admina - sve zajedničke podatke prebacujes, porudžbine i dalje postoje u dao porudžbina
- dostavljaca - sve zajedničke podatke prebacujes, porudžbine i dalje postoje u dao porudžbina, a i dodeljuješ mu vozilo

ako menjas admina u:
- kupca - sve zajedničke podatke prebacujes, omiljene rest. i porudžbine kreiras kao prazne liste
- dostavljaca - sve zajedničke podatke prebacujes, a i dodeljuješ mu vozilo
	 */
	
	
	
	public static boolean isOznaka(String str)  
	{  //NS-055PG
		System.out.println("is num"+str);
		
		char c=str.charAt(2);
		
		
		if(str.length()!=8 || !(c=='-'))
		{
			return false;
		}
		
		return true;
	}
}
