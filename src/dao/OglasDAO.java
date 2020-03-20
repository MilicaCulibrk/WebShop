package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Oglas;

public class OglasDAO {
	
private HashMap<UUID, Oglas> oglasi = new HashMap<UUID,Oglas>();
	
	public OglasDAO() {
		
	}

	public OglasDAO(String contextPath) {
		loadoglasi(contextPath);
	}
	
	
	@Override
	public String toString() {
		return "oglasiDAO [oglasi=" + oglasi + "]";
	}

	
	
public HashMap<UUID, Oglas> getOglasi() {
		return oglasi;
	}

	public void setOglasi(HashMap<UUID, Oglas> oglasi) {
		this.oglasi = oglasi;
	}

public void loadoglasi(String contextPath) {
		
		try
		{
			File file = new File(contextPath + "/oglasi.json");
			System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
			ArrayList<Oglas> proba=new ArrayList<>();
			
			
			/*Oglas vozilo= new Oglas();
			vozilo.setRegOznaka("HH-555YY");
			vozilo.setGodinaProizvodnje(2011);
			vozilo.setMarka("OPEL");
			vozilo.setModel("55f5");
			vozilo.setNapomena("bez");
			vozilo.setUpotreba(0);
			vozilo.setTip(1);
			
			*/
			
			Oglas oglas = new Oglas();
			oglas.setAktivan(true);
			oglas.setBrDislajkova(1);
			oglas.setBrLajkova(1);
			oglas.setCena(155);
			oglas.setDatumIsticanja("datum");
			oglas.setDatumPostavljanja("dadada");
			oglas.setGrad("Novi Sad");
			oglas.setNaziv("naziv");
			ArrayList<UUID> recenzije=new ArrayList<>();
			UUID recen = UUID.randomUUID();
			recenzije.add(recen);
			oglas.setRecenzije(recenzije);
			oglas.setOpis("OPIS");
			oglas.setSlika("URL");
			proba.add(oglas);
			objectMapper.writeValue(new File(contextPath + "/oglasi.json"), proba);
			
			
		
			Oglas[] Oglas = objectMapper.readValue(file, Oglas[].class);
			System.out.println("load Oglas: "+Oglas);
			
			
			for(Oglas u : Oglas)
			{
				oglasi.put(u.getIdOglas(),u);
			}
			
			System.out.println(oglasi);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
		
	}
	
	public void dodaj(Oglas u, String contextPath)
	{
				
		try
		{
			File file = new File(contextPath + "/oglasi.json");
			System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<Oglas> proba=new ArrayList<>();
			
			Oglas[] oglass = objectMapper.readValue(file, Oglas[].class);
					
			for(Oglas g : oglass)
			{
				proba.add(g);
			}
			
			proba.add(u);
			objectMapper.writeValue(new File(contextPath + "/oglasi.json"), proba);
			Oglas r=oglasi.put(u.getIdOglas(), u);
			
			System.out.println(oglasi);
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
		
	}
	
	public Collection<Oglas> findAll() {
		return oglasi.values();
	}

	/***
	 *  Vraca proizvod na osnovu njegovog id-a. 
	 *  @return Proizvod sa id-em ako postoji, u suprotnom null
	 */
	public Oglas findRestoran(UUID id) {
		return oglasi.containsKey(id) ? oglasi.get(id) : null;
	}
	
	public boolean pretragaRestorana(UUID id) {
		return oglasi.containsKey(id) ? true : false;
	}
	
	public void deleteOglasi(String contextPath, UUID id) {
		System.out.println("delete oglasi" + id);
	try
	{
		/*File file = new File(contextPath + "/oglasi.json");
		System.out.println(contextPath);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

		ArrayList<Oglas> proba=new ArrayList<>();
	
		Oglas[] Oglas = objectMapper.readValue(file, Oglas[].class);
		System.out.println("obrisi oglasi dao: "+Oglas);
		
		Oglas res = null;
		for(Oglas g : Oglas)
		{
			if(g.getRegOznaka().equals(id))
			{
				res=g;
			}
			proba.add(g);
		}
		proba.remove(res);
		objectMapper.writeValue(new File(contextPath + "/Oglass.json"), proba);
		Oglas rr = null;
		//for() */
		
			if(oglasi.containsKey((id)))
			{
				oglasi.remove(id);
			}
		//}
		
		
		System.out.println(oglasi);
	}
	catch (Exception ex) {
		System.out.println(ex);
		ex.printStackTrace();
	} finally {
		
	}
	
	}
	
	
	
	public void dodajuFile(HashMap<UUID, Oglas> oglasi, String contextPath) //pise u fajl
	{
				
		try
		{
			File file = new File(contextPath + "/oglasi.json");
			System.out.println(contextPath);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<Oglas> proba=new ArrayList<>();
			
		//	Restoran[] Oglas = objectMapper.readValue(file, Restoran[].class);
		//	System.out.println("register Restoran: "+Oglas);
					
			for(Oglas g : oglasi.values())
			{
				proba.add(g);
			}
			objectMapper.writeValue(new File(contextPath + "/oglasi.json"), proba);
			
			System.out.println(oglasi +" u file");
			
		}
		catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		} finally {
			
		}
		
		
	}


}
