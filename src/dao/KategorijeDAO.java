package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Kategorija;
import beans.Korisnik;


public class KategorijeDAO {
	
	private HashMap<String, Kategorija> kategorije = new HashMap<>();
	private String contextPath;

	public KategorijeDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public KategorijeDAO(String contextPath) {
		loadKategorije(contextPath);
		this.contextPath = contextPath;
	}


	public KategorijeDAO(HashMap<String, Kategorija> kategorije) {
		super();
		this.kategorije = kategorije;
	}
	
	public HashMap<String, Kategorija> getKategorije() {
		return kategorije;
	}

	public void setKategorije(HashMap<String, Kategorija> kategorije) {
		this.kategorije = kategorije;
	}
	
	public void loadKategorije(String contextPath) {
		try
		{
			File file = new File(contextPath + "/kategorija.json");
			//System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); //nebitno, kopiramo, za mapiranje
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

			Kategorija[] kat = objectMapper.readValue(file, Kategorija[].class);  //iz fajla u listu
			//System.out.println("load User: " + car);
			
			//objectMapper.writeValue(new File(contextPath + "/proba.json"), new User("asfas","joasfasv","jov","jov",0,"jov","jov", "jov"));
			
			for(Kategorija k : kat)		//iz liste u mapu
			{
				kategorije.put(k.getId(), k);
			}
			
			//System.out.println(korisnici);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		

	}
	
	
	public ArrayList<Kategorija> izlistaj () {
		ArrayList<Kategorija> izlistaneKategorije= new ArrayList<>();
		
		for (Kategorija kategorija : kategorije.values()) {
			if (kategorija.isObrisan() == false){
				izlistaneKategorije.add(kategorija);
			}
		}
		
		return izlistaneKategorije;
	}
	

	public void saveKategoriju(){
		try
		{
			File file = new File(contextPath + "/kategorija.json");
			//System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); //nebitno, kopiramo, za mapiranje
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

			ArrayList<Kategorija> listaK = new ArrayList<Kategorija>();
			
			for(Kategorija k : kategorije.values())		//iz liste u mapu
			{
				listaK.add(k);
			}
			
			objectMapper.writeValue(new File(contextPath + "/kategorija.json"), listaK);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
	}
	
	public Kategorija izlistajPoIdu (String id){
		if (kategorije.get(id)!= null && kategorije.get(id).isObrisan()==false)
			return kategorije.get(id);
		else {
			return null;
		}
					
	}
	
public Kategorija izmeni(Kategorija kat){
		
		Kategorija k = izlistajPoIdu(kat.getId());
		k.setNaziv(kat.getNaziv());
		k.setOpis(kat.getOpis());
		saveKategoriju();
		return k;
	}
	

}
