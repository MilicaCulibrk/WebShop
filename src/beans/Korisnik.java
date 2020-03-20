package beans;

import java.util.ArrayList;


public class Korisnik {
	
	private String id;
	private String korisnickoIme;
	private String lozinka;
	private String ime;
	private String prezime;
	private String telefon;
	private String grad;
	private String email;
	private String datum;
	private Uloga uloga;
	
	private ArrayList<String> poslatePoruke ;
	private ArrayList<String> primljenePoruke ;
	private ArrayList<String> lajkovaniOglasi ;
	private ArrayList<String> dislajkovaniOglasi ;
	
	
	
	// za kupca
	private ArrayList<String> poruceniProizvodi = new ArrayList<>();
	private ArrayList<String> dostavljeniProizvodi = new ArrayList<>();
	private ArrayList<String> omiljeniOglasi = new ArrayList<>();
	
	//za prodavca
	private ArrayList<String> objavljeniOglasi;
	private ArrayList<String> isporuceniProizvodi;
	private int likes;
	private int dislikes;
	private int prijave =0 ;
	
	public Korisnik() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Korisnik(String id, String korisnickoIme, String lozinka, String ime, String prezime, String telefon,
			String grad, String email, String datum, Uloga uloga) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.telefon = telefon;
		this.grad = grad;
		this.email = email;
		this.datum = datum;
		this.uloga = uloga;
	}

	
	public ArrayList<String> getPoslatePoruke() {
		return poslatePoruke;
	}

	public void setPoslatePoruke(ArrayList<String> poslatePoruke) {
		this.poslatePoruke = poslatePoruke;
	}

	public ArrayList<String> getPrimljenePoruke() {
		return primljenePoruke;
	}

	public void setPrimljenePoruke(ArrayList<String> primljenePoruke) {
		this.primljenePoruke = primljenePoruke;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public ArrayList<String> getPoruceniProizvodi() {
		return poruceniProizvodi;
	}

	public void setPoruceniProizvodi(ArrayList<String> poruceniProizvodi) {
		this.poruceniProizvodi = poruceniProizvodi;
	}

	public ArrayList<String> getDostavljeniProizvodi() {
		return dostavljeniProizvodi;
	}

	public void setDostavljeniProizvodi(ArrayList<String> dostavljeniProizvodi) {
		this.dostavljeniProizvodi = dostavljeniProizvodi;
	}

	public ArrayList<String> getOmiljeniOglasi() {
		return omiljeniOglasi;
	}

	public void setOmiljeniOglasi(ArrayList<String> omiljeniOglasi) {
		this.omiljeniOglasi = omiljeniOglasi;
	}

	public ArrayList<String> getObjavljeniOglasi() {
		return objavljeniOglasi;
	}

	public void setObjavljeniOglasi(ArrayList<String> objavljeniOglasi) {
		this.objavljeniOglasi = objavljeniOglasi;
	}

	public ArrayList<String> getIsporuceniProizvodi() {
		return isporuceniProizvodi;
	}

	public void setIsporuceniProizvodi(ArrayList<String> isporuceniProizvodi) {
		this.isporuceniProizvodi = isporuceniProizvodi;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
    public boolean postojiOmiljeni(String id){
		
		for (String s : omiljeniOglasi ) {
			if (s.equals(id))
				return true;
		}
		
		return false;
	}

	public ArrayList<String> getLajkovaniOglasi() {
		return lajkovaniOglasi;
	}

	public void setLajkovaniOglasi(ArrayList<String> lajkovaniOglasi) {
		this.lajkovaniOglasi = lajkovaniOglasi;
	}

	public ArrayList<String> getDislajkovaniOglasi() {
		return dislajkovaniOglasi;
	}

	public void setDislajkovaniOglasi(ArrayList<String> dislajkovaniOglasi) {
		this.dislajkovaniOglasi = dislajkovaniOglasi;
	}

	public int getPrijave() {
		return prijave;
	}

	public void setPrijave(int prijave) {
		this.prijave = prijave;
	}
	
	
	
	

}


	/*public ArrayList<String> getPoruceniProizvodi() {
		return poruceniProizvodi;
	}

	public void setPoruceniProizvodi(ArrayList<String> poruceniProizvodi) {
		this.poruceniProizvodi = poruceniProizvodi;
	}

	public ArrayList<String> getDostavljeniProizvodi() {
		return dostavljeniProizvodi;
	}

	public void setDostavljeniProizvodi(ArrayList<String> dostavljeniProizvodi) {
		this.dostavljeniProizvodi = dostavljeniProizvodi;
	}

	public ArrayList<String> getOmiljeniOglasi() {
		return omiljeniOglasi;
	}

	public void setOmiljeniOglasi(ArrayList<String> omiljeniOglasi) {
		this.omiljeniOglasi = omiljeniOglasi;
	}

	public ArrayList<String> getObjavljeniOglasi() {
		return objavljeniOglasi;
	}

	public void setObjavljeniOglasi(ArrayList<String> objavljeniOglasi) {
		this.objavljeniOglasi = objavljeniOglasi;
	}

	public ArrayList<String> getIsporuceniProizvodi() {
		return isporuceniProizvodi;
	}

	public void setIsporuceniProizvodi(ArrayList<String> isporuceniProizvodi) {
		this.isporuceniProizvodi = isporuceniProizvodi;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
    public boolean postojiOmiljeni(String id){
		
		for (String s : omiljeniOglasi ) {
			if (s.equals(id))
				return true;
		}
		
		return false;
	}

	public ArrayList<String> getLajkovaniOglasi() {
		return lajkovaniOglasi;
	}

	public void setLajkovaniOglasi(ArrayList<String> lajkovaniOglasi) {
		this.lajkovaniOglasi = lajkovaniOglasi;
	}

	public ArrayList<String> getDislajkovaniOglasi() {
		return dislajkovaniOglasi;
	}

	public void setDislajkovaniOglasi(ArrayList<String> dislajkovaniOglasi) {
		this.dislajkovaniOglasi = dislajkovaniOglasi;
	}

	public int getPrijave() {
		return prijave;
	}

	public void setPrijave(int prijave) {
		this.prijave = prijave;
	}
	
	
	
	

}


/*public class Korisnik {
	
	private String ime = "";
	private String prezime = "";
	private String email = "";
	private String kime = "";
	private String lozinka = "";
	private String telefon = "";
	private String grad = "";

	private int uloga = 0;  //korisnik = admin, kupac, prodavac
	
	private String datum = "";
	
	public Korisnik() {
	
	}

	public Korisnik(String username, String password, String ime, String prezime, int uloga, String grad, String telefon,
			String email, String datum) {
		super();
		this.kime = username;
		this.lozinka = password;
		this.ime = ime;
		this.prezime = prezime;
		this.uloga = uloga;
		this.telefon = telefon;
		this.grad = grad;
		this.email = email;
		this.datum = datum;
	}
	
	public Korisnik(String username, String password, String ime, String prezime, String grad, String telefon,  //bez uloge
			String email, String datum) {
		super();
		this.kime = username;
		this.lozinka = password;
		this.ime = ime;
		this.prezime = prezime;
		this.grad = grad;
		this.telefon = telefon;
		this.email = email;
		this.datum = datum;
	}

	public Korisnik(Korisnik u) {		//ceo korisnik
		super();
		this.kime = u.getUsername();
		this.lozinka = u.getPassword();
		this.ime = u.getIme();
		this.prezime = u.getPrezime();
		this.uloga = u.getUloga();
		this.grad = u.getGrad();
		this.telefon = u.getTelefon();
		this.email = u.getEmail();
		this.datum = u.getDatum();
	}

	public String getUsername() {		//geteri i seteri
		return kime;
	}



	public void setUsername(String username) {
		this.kime = username;
	}



	public String getPassword() {
		return lozinka;
	}



	public void setPassword(String password) {
		this.lozinka = password;
	}



	public String getIme() {
		return ime;
	}



	public void setIme(String ime) {
		this.ime = ime;
	}



	public String getPrezime() {
		return prezime;
	}



	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}



	public int getUloga() {
		return uloga;
	}



	public void setUloga(int uloga) {
		this.uloga = uloga;
	}



	public String getTelefon() {
		return telefon;
	}



	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getDatum() {
		return datum;
	}



	public void setDatum(String datum) {
		this.datum = datum;
	}
	
	



	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	@Override
	public String toString() {
		return "User [username=" + kime + ", password=" + lozinka + ", ime=" + ime + ", prezime=" + prezime
				+ ", uloga=" + uloga + ", telefon=" + telefon + ", email=" + email + ", datum=" + datum + "]";
	}  */



