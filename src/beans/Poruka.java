package beans;

public class Poruka {
	
	private boolean obrisan;
	private String id;
	private String nazivOglasa;
	private String posiljalac;
	private String naslov;
	private String datum;
	private String sadrzaj;
	public Poruka() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Poruka(String id, String nazivOglasa, String posiljalac, String naslov, String datum, String sadrzaj) {
		super();
		this.id = id;
		this.nazivOglasa = nazivOglasa;
		this.posiljalac = posiljalac;
		this.naslov = naslov;
		this.datum = datum;
		this.sadrzaj = sadrzaj;
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
	public String getNazivOglasa() {
		return nazivOglasa;
	}
	public void setNazivOglasa(String nazivOglasa) {
		this.nazivOglasa = nazivOglasa;
	}
	public String getPosiljalac() {
		return posiljalac;
	}
	public void setPosiljalac(String posiljalac) {
		this.posiljalac = posiljalac;
	}
	public String getNaslov() {
		return naslov;
	}
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getSadrzaj() {
		return sadrzaj;
	}
	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}
	
	

}
