package dao;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Korisnik;
import beans.Oglas;
import beans.Uloga;


public class KorisnikDAO {
	
	private HashMap<String, Korisnik> korisnici = new HashMap<>();  //mapa korisnika
	private String contextPath;		//putanja
	
	public KorisnikDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public KorisnikDAO(String contextPath) {
		loadUsers(contextPath);
		this.contextPath = contextPath;
	}
	
	public void loadUsers(String contextPath){
		try
		{
			File file = new File(contextPath + "/users.json");
			System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); //nebitno, kopiramo, za mapiranje
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

			Korisnik[] users = objectMapper.readValue(file, Korisnik[].class);  //iz fajla u listu
			//System.out.println("load User: " + car);
			
			//objectMapper.writeValue(new File(contextPath + "/proba.json"), new User("asfas","joasfasv","jov","jov",0,"jov","jov", "jov"));
			
			for(Korisnik u : users)		//iz liste u mapu
			{
				korisnici.put(u.getKorisnickoIme(), u);
			}
			
			//System.out.println(korisnici);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
	}

	public boolean exists(Korisnik korisnik){		//ako mejl ili kime postoje u mapi
		
		for (Korisnik k : korisnici.values()) {
			if (korisnik.getEmail().toLowerCase().equals(k.getEmail().toLowerCase()) ||
					korisnik.getKorisnickoIme().toLowerCase().equals(k.getKorisnickoIme().toLowerCase()))
				return true; 
		}
		return false;
		
	}
	
	public void addUser(Korisnik korisnik)
	{
		
		DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
		String datum = dateFormat.format(new Date());
		korisnik.setDatum(datum);
		
		korisnik.setUloga(Uloga.KUPAC);
		
		korisnik.setId(UUID.randomUUID().toString());
		
		korisnik.setPoruceniProizvodi(new ArrayList<>());
		korisnik.setDostavljeniProizvodi(new ArrayList<>());
		korisnik.setPrimljenePoruke(new ArrayList<>());
		korisnik.setPoslatePoruke(new ArrayList<>());
		korisnik.setOmiljeniOglasi(new ArrayList<>());
		korisnik.setLajkovaniOglasi(new ArrayList<>());
		korisnik.setDislajkovaniOglasi(new ArrayList<>());
		
				
		try
		{
			File file = new File(contextPath + "/users.json");
			System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			
			ArrayList<Korisnik> tempUsers = new ArrayList<Korisnik>();
		
			
			Korisnik[] users = objectMapper.readValue(file, Korisnik[].class);  // iz fajla u niz
			//System.out.println("register User: "+users);
			
			//objectMapper.writeValue(new File(contextPath + "/proba.json"), new User("asfas","joasfasv","jov","jov",0,"jov","jov", "jov"));
			
			for(Korisnik g : users)
			{
				tempUsers.add(g);		//iz niza u temp listu
			}
			tempUsers.add(korisnik);  //sve koji su bili + novi, mapa i fajl moraju biti uskladjeni
			objectMapper.writeValue(new File(contextPath + "/users.json"), tempUsers);
			korisnici.put(korisnik.getKorisnickoIme(), korisnik);

		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
		
	}
	
	public Korisnik findUser(String korisnickoIme){
		
		for (Korisnik k : korisnici.values()) {
			if (k.getKorisnickoIme().equals(korisnickoIme))
				return k;
		}
		
		return null;
	}
	
	public boolean lajkovao(String oglasId, Korisnik k){
		for (String s : korisnici.get(k.getKorisnickoIme()).getLajkovaniOglasi()) {
			if (s.equals(oglasId))
				return true;
		}
		return false;
	}
	
	public boolean dislajkovao(String oglasId, Korisnik k){
		for (String s : korisnici.get(k.getKorisnickoIme()).getDislajkovaniOglasi()) {
			if (s.equals(oglasId))
				return true;
		}
		return false;
		
	}
	

	
	public HashMap<String, Korisnik> getKorisnici() {
		return korisnici;
	}
	public void setKorisnici(HashMap<String, Korisnik> korisnici) {
		this.korisnici = korisnici;
	}
	
	public void saveUser(){
		try
		{
			File file = new File(contextPath + "/users.json");
			//System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); //nebitno, kopiramo, za mapiranje
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

			ArrayList<Korisnik> listaK = new ArrayList<Korisnik>();
			
			for(Korisnik k : korisnici.values())		//iz liste u mapu
			{
				listaK.add(k);
			}
			
			objectMapper.writeValue(new File(contextPath + "/users.json"), listaK);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
	}
	
	
}
