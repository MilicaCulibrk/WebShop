package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Korisnik;
import beans.Recenzija;

public class RecenzijaDAO {
	
	private HashMap<String, Recenzija> recenzije = new HashMap<>();
	private String contextPath;
	
	public RecenzijaDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RecenzijaDAO(String putanja) {
		loadRecenzije(putanja);
		this.contextPath=putanja;
		
	}
	

	public RecenzijaDAO(HashMap<String, Recenzija> recenzije) {
		super();
		this.recenzije = recenzije;
	}
	
	public void loadRecenzije(String contextPath){
		try
		{
			File file = new File(contextPath + "/recenzija.json");
			System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); //nebitno, kopiramo, za mapiranje
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

			Recenzija[] rec = objectMapper.readValue(file, Recenzija[].class);  //iz fajla u listu
			//System.out.println("load User: " + car);
			
			//objectMapper.writeValue(new File(contextPath + "/proba.json"), new User("asfas","joasfasv","jov","jov",0,"jov","jov", "jov"));
			
			for(Recenzija r : rec)		//iz liste u mapu
			{
				recenzije.put(r.getId(), r);
			}
			
			//System.out.println(korisnici);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
	}

	public HashMap<String, Recenzija> getRecenzije() {
		return recenzije;
	}

	public void setRecenzije(HashMap<String, Recenzija> recenzije) {
		this.recenzije = recenzije;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	public void saveRecenziju(){
		try
		{
			File file = new File(contextPath + "/recenzija.json");
			//System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); //nebitno, kopiramo, za mapiranje
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

			ArrayList<Recenzija> listaR = new ArrayList<Recenzija>();
			
			for(Recenzija r : recenzije.values())		//iz liste u mapu
			{
				listaR.add(r);
			}
			
			objectMapper.writeValue(new File(contextPath + "/recenzija.json"), listaR);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
	}
	
	public Recenzija izlistajPoIdu (String id){
		if (recenzije.get(id)!= null && recenzije.get(id).isObrisan()==false){
			return recenzije.get(id);
		}
		else
			return null;
	}
	
	
	public Recenzija izmeni(Recenzija rec){
		
		Recenzija r = izlistajPoIdu(rec.getId());
		r .setNaslov(rec.getNaslov());
		r .setSadrzaj(rec.getSadrzaj());
		r .setSlika(rec.getSlika());
		r .setTacnost(rec.isTacnost());
		r .setPostovanjeDog(rec.isPostovanjeDog());
		saveRecenziju();
		return r ;
	}
	

}
