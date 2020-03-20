package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Korisnik;
import beans.Oglas;


public class OglasiDAO {
	
	private HashMap<String, Oglas> oglasi = new HashMap<>();
	private String contextPath;
	

	public OglasiDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OglasiDAO(String putanja) {
		loadOglase(putanja);
		this.contextPath = putanja;
	}

	public OglasiDAO(HashMap<String, Oglas> oglasi) {
		super();
		this.oglasi = oglasi;
	}

	
	public void loadOglase(String contextPath){
		
		try
		{
			File file = new File(contextPath + "/oglas.json");
			//System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); //nebitno, kopiramo, za mapiranje
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

			Oglas[] ogl = objectMapper.readValue(file, Oglas[].class);  //iz fajla u listu
			//System.out.println("load User: " + car);
			
			//objectMapper.writeValue(new File(contextPath + "/proba.json"), new User("asfas","joasfasv","jov","jov",0,"jov","jov", "jov"));
			
			for(Oglas o : ogl)		//iz liste u mapu
			{
				oglasi.put(o.getId(), o);
			}
			
			//System.out.println(korisnici);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
	}
	
	
	public ArrayList<Oglas> izlistaj (){		
		ArrayList<Oglas> izlistaniOglasi = new ArrayList<>();
		for (Oglas oglas : oglasi.values()) {
			if (oglas.isObrisan()==false){
				izlistaniOglasi.add(oglas);
			}
		}	
		return izlistaniOglasi;
	}
	
	public ArrayList<Oglas> izlistaPoKat(String kat){
		ArrayList<Oglas> listaK = new ArrayList<>();
		for (Oglas oglas : oglasi.values()) {
			if (oglas.getKategorija().equals(kat) && oglas.isObrisan() == false){
				listaK.add(oglas);
			}
		}	
		return listaK;
	}
	
	public HashMap<String, Oglas> getOglasi() {
		return oglasi;
	}
	public void setOglasi(HashMap<String, Oglas> oglasi) {
		this.oglasi = oglasi;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	public void saveOglas(){
		try
		{
			File file = new File(contextPath + "/oglas.json");
			//System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); //nebitno, kopiramo, za mapiranje
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

			ArrayList<Oglas> listaO = new ArrayList<Oglas>();
			
			for(Oglas o : oglasi.values())		//iz liste u mapu
			{
				listaO.add(o);
			}
			
			objectMapper.writeValue(new File(contextPath + "/oglas.json"), listaO);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
	}
	
	public Oglas izlistaPoIdu(String id){
		if (oglasi.get(id)!= null && oglasi.get(id).isObrisan() == false){
			return oglasi.get(id);
		}
		else
			return null;
	}
	
	public Oglas izmeni(Oglas oglas){
		
		Oglas o=izlistaPoIdu(oglas.getId());
		o.setCena(oglas.getCena());
		o.setNaziv(oglas.getNaziv());
		o.setOpis(oglas.getOpis());
		o.setDatumIsticanja(oglas.getDatumIsticanja());
		o.setSlika(oglas.getSlika());
		o.setGrad(oglas.getGrad());
		o.setKategorija(oglas.getKategorija());
		saveOglas();
		return o;
	}
	

	
}
