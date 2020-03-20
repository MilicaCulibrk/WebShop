package beans;

import java.util.ArrayList;



public class Oglas {
	
	private boolean obrisan;
	private Status status;	
	private String id;
	private String naziv;
	private double cena;
	private String opis;
	private int brLajkova;
	private int brDislajkova;
	private String datumPostavljanja;
	private String datumIsticanja;
	private String slika;
	private boolean aktivan;
	private String grad;
	private ArrayList<String> listaRecenzija = new ArrayList<>();
	private int omiljen; //kod koliko korisnika je oglas omiljen radi prikaza pocetne stranice
	private String kategorija;
	private String korisnik;

	
	public Oglas() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Oglas(Status status, String id, String naziv, double cena, String opis, int brLajkova, int brDislajkova,
			String datumPostavljanja, String datumIsticanja, String slika, boolean aktivan, String grad,
			ArrayList<String> listaRecenzija) {
		super();
		this.status = status;
		this.id = id;
		this.naziv = naziv;
		this.cena = cena;
		this.opis = opis;
		this.brLajkova = brLajkova;
		this.brDislajkova = brDislajkova;
		this.datumPostavljanja = datumPostavljanja;
		this.datumIsticanja = datumIsticanja;
		this.slika = slika;
		this.aktivan = aktivan;
		this.grad = grad;
		this.listaRecenzija = listaRecenzija;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
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
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public int getBrLajkova() {
		return brLajkova;
	}
	public void setBrLajkova(int brLajkova) {
		this.brLajkova = brLajkova;
	}
	public int getBrDislajkova() {
		return brDislajkova;
	}
	public void setBrDislajkova(int brDislajkova) {
		this.brDislajkova = brDislajkova;
	}
	public String getDatumPostavljanja() {
		return datumPostavljanja;
	}
	public void setDatumPostavljanja(String datumPostavljanja) {
		this.datumPostavljanja = datumPostavljanja;
	}
	public String getDatumIsticanja() {
		return datumIsticanja;
	}
	public void setDatumIsticanja(String datumIsticanja) {
		this.datumIsticanja = datumIsticanja;
	}
	public String getSlika() {
		return slika;
	}
	public void setSlika(String slika) {
		this.slika = slika;
	}
	public boolean isAktivan() {
		return aktivan;
	}
	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
	public String getGrad() {
		return grad;
	}
	public void setGrad(String grad) {
		this.grad = grad;
	}
	public ArrayList<String> getListaRecenzija() {
		return listaRecenzija;
	}
	public void setListaRecenzija(ArrayList<String> listaRecenzija) {
		this.listaRecenzija = listaRecenzija;
	}
	public int getOmiljen() {
		return omiljen;
	}
	public void setOmiljen(int omiljen) {
		this.omiljen = omiljen;
	}
	public String getKategorija() {
		return kategorija;
	}
	public void setKategorija(String kategorija) {
		this.kategorija = kategorija;
	}
	public String getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(String korisnik) {
		this.korisnik = korisnik;
	}

	
	

}
