package dao;

import java.awt.List;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Korisnik;

/***
 * <p>Klasa namenjena da uèita korisnike iz fajla i pruža operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u èistu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
public class UserDAO {
	private HashMap<String, Korisnik> users = new HashMap<>();
	//ServletContext ctx;
	
	public UserDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public UserDAO(String contextPath) {
		loadUsers(contextPath);
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	
	
	public Korisnik find(String username, String password) {
		if (!users.containsKey(username)) {
			return null;
		}
		Korisnik user = users.get(username);
		if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}
	
	public HashMap<String, Korisnik> getUsers() {
		return users;
	}

	public void setUsers(HashMap<String, Korisnik> users) {
		this.users = users;
	}

	public boolean find(String username) {
		if (!users.containsKey(username)) {
			return false;
		}
		
		return true;
	}
	
	public Korisnik pretragaKorisnika(String u) {
		if (!users.containsKey(u)) {
			return null;
		}
		Korisnik user = users.get(u);
	
		return user;
	}
	
	public Collection<Korisnik> findAll() {
		return users.values();
	}
	
	public void dodaj(Korisnik u, String contextPath)
	{
				
		try
		{
			File file = new File(contextPath + "/users.json");
			System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			ArrayList<Korisnik> proba=new ArrayList<>();
			//proba.add(new User("pera","pera","Petar","Petrovic",0,"06055555","fasf@casc.com", "08-09-1995"));
			//proba.add(new User("jov","jov","jovana","jov",0,"0605020313","asfa@gmail.com", "06-06-1985"));
			//objectMapper.writeValue(new File(contextPath + "/users.json"), proba);
			
			Korisnik[] car = objectMapper.readValue(file, Korisnik[].class);
			System.out.println("register User: "+car);
			
			//objectMapper.writeValue(new File(contextPath + "/proba.json"), new User("asfas","joasfasv","jov","jov",0,"jov","jov", "jov"));
			
			for(Korisnik g : car)
			{
				proba.add(g);
			}
			proba.add(u);  //sve koji su bili + novi, mapa i fajl moraju biti uskladjeni
			objectMapper.writeValue(new File(contextPath + "/users.json"), proba);
			Korisnik r=users.put(u.getUsername(), u);
			
			System.out.println(users);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
		
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadUsers(String contextPath) {
		
		try
		{
			File file = new File(contextPath + "/users.json");
			System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); //nebitno, kopiramo
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			
			
			ArrayList<Korisnik> proba=new ArrayList<>();
			
			//OVO JE ZA DEFAULT VREDNOSTI, napravi sve sama pred projekat i zakomentarisi ovo
			proba.add(new Korisnik("pera","pera","Petar","Petrovic",0,"06055555","fasf@casc.com", "08-09-1995"));
			proba.add(new Korisnik("jov","jov","jovana","jov",1,"0605020313","asfa@gmail.com", "06-06-1985"));
			objectMapper.writeValue(new File(contextPath + "/users.json"), proba);
			
			
			Korisnik[] car = objectMapper.readValue(file, Korisnik[].class);
			System.out.println("load User: "+car);
			
			//objectMapper.writeValue(new File(contextPath + "/proba.json"), new User("asfas","joasfasv","jov","jov",0,"jov","jov", "jov"));
			
			for(Korisnik u : car)
			{
				users.put(u.getUsername(),u);
			}
			
			System.out.println(users);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
	}

	@Override
	public String toString() {
		return "UserDAO [users=" + users + "]";
	}
	
	public void stavi(Korisnik restoran) {
		// TODO Auto-generated method stub
		
		users.put(restoran.getUsername(), restoran);
		
	}
	
	public void dodajuFile(HashMap<String, Korisnik> users, String contextPath)
	{
				
		try
		{
			File file = new File(contextPath + "/users.json");
			System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<Korisnik> proba=new ArrayList<>();
			
					
			for(Korisnik g : users.values())
			{
				proba.add(g);
			}
			objectMapper.writeValue(new File(contextPath + "/users.json"), proba);
			
			System.out.println(users +" u file");
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
		
	}
	
}
