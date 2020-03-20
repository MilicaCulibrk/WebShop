package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Korisnik;
import beans.Oglas;
import beans.Poruka;
import beans.Uloga;

public class PorukeDAO {
	
	HashMap<String, Poruka> poruke = new HashMap<>();
	private String contextPath;
	
	public PorukeDAO(HashMap<String, Poruka> poruke) {
		super();
		this.poruke = poruke;
	}

	public PorukeDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PorukeDAO(String putanja) {
		loadPoruke(putanja);
		this.contextPath=putanja;
		
	}

	public HashMap<String, Poruka> getPoruke() {
		return poruke;
	}

	public void setPoruke(HashMap<String, Poruka> poruke) {
		this.poruke = poruke;
	}
	
	public void loadPoruke(String contextPath){
		try
		{
			File file = new File(contextPath + "/poruka.json");
			System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); //nebitno, kopiramo, za mapiranje
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

			Poruka[] por = objectMapper.readValue(file, Poruka[].class);  //iz fajla u listu
			//System.out.println("load User: " + car);
			
			//objectMapper.writeValue(new File(contextPath + "/proba.json"), new User("asfas","joasfasv","jov","jov",0,"jov","jov", "jov"));
			
			for(Poruka p : por)		//iz liste u mapu
			{
				poruke.put(p.getId(), p);
			}
			
			//System.out.println(korisnici);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
	}
	
	public void savePoruku(){
		try
		{
			File file = new File(contextPath + "/poruka.json");
			//System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true); //nebitno, kopiramo, za mapiranje
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

			ArrayList<Poruka> listaP = new ArrayList<Poruka>();
			
			for(Poruka p : poruke.values())		//iz liste u mapu
			{
				listaP.add(p);
			}
			
			objectMapper.writeValue(new File(contextPath + "/poruka.json"), listaP);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
	}
	
	
	public ArrayList<Poruka> izlistajPoslate (Korisnik k){
		ArrayList<Poruka> izlistane= new ArrayList<>();
		for (String s : k.getPoslatePoruke()) {
			if ( poruke.get(s).isObrisan()==false){
				izlistane.add(0, poruke.get(s));
			}
		}	
		return izlistane;
	}
	
	public ArrayList<Poruka> izlistajPrimljene (Korisnik k){
		ArrayList<Poruka> izlistane= new ArrayList<>();
		for (String s : k.getPrimljenePoruke()) {
			if ( poruke.get(s).isObrisan()==false){
				izlistane.add(0, poruke.get(s));
			}
		}	
		
		return izlistane;
	}
	
	public Poruka izlistajPoIdu (String id){
		if (poruke.get(id).isObrisan()==false){
			return poruke.get(id);
		} else
			return null;
	}

	public Poruka izmeni(Poruka p){
			
			Poruka o=izlistajPoIdu(p.getId());
			o.setNaslov(p.getNaslov());
			o.setSadrzaj(p.getSadrzaj());
			o.setNazivOglasa(p.getNazivOglasa());
			savePoruku();
			
			return p;
		}
	
	public boolean dozvola(Korisnik posiljalac, Korisnik primalac){
		if ((posiljalac.getUloga().equals(Uloga.KUPAC) && primalac.getUloga().equals(Uloga.PRODAVAC))){
			return true;
		}
		if ((posiljalac.getUloga().equals(Uloga.PRODAVAC) &&  odgovor(posiljalac, primalac.getKorisnickoIme()))){
			return true;
		}
		if ((posiljalac.getUloga().equals(Uloga.PRODAVAC) &&  primalac.getUloga().equals(Uloga.ADMINISTRATOR))){
			return true;
		}
		if (posiljalac.getUloga().equals(Uloga.ADMINISTRATOR))
			return true;
		
		return false;
		
	}
	
	public boolean odgovor(Korisnik posiljalac, String username){
		for (String s : posiljalac.getPrimljenePoruke()) {
			if (poruke.get(s).getPosiljalac().equals(username)){
				return true;
			}
		}
		return false;
	}


}
