package beans;

import java.util.ArrayList;

public class Kategorija {
	
	private boolean obrisan;
	private String id;
	private String naziv;
	private String opis;
	private ArrayList<String> listaO = new ArrayList<>();
	public Kategorija() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Kategorija(String id, String naziv, String opis, ArrayList<String> listaO) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.listaO = listaO;
	}
	
	public boolean isObrisan() {
		return obrisan;
	}
	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public ArrayList<String> getListaO() {
		return listaO;
	}
	public void setListaO(ArrayList<String> listaO) {
		this.listaO = listaO;
	}
	
	
}
