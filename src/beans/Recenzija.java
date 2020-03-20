package beans;

public class Recenzija {
	
	private boolean obrisan;
	private String id;
	private String oglas;
	private String recenzent;
	private String naslov;
	private String sadrzaj;
	private String slika;
	private boolean tacnost;
	private boolean postovanjeDog;
	
	public Recenzija() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Recenzija(String id, String oglas, String naslov, String sadrzaj, String slika,
			boolean tacnost, boolean postovanjeDog) {
		super();
		this.id = id;
		this.oglas = oglas;
		this.naslov = naslov;
		this.sadrzaj = sadrzaj;
		this.slika = slika;
		this.tacnost = tacnost;
		this.postovanjeDog = postovanjeDog;
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

	public String getOglas() {
		return oglas;
	}

	public void setOglas(String oglas) {
		this.oglas = oglas;
	}

	public String getRecenzent() {
		return recenzent;
	}

	public void setRecenzent(String recenzent) {
		this.recenzent = recenzent;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}

	public boolean isTacnost() {
		return tacnost;
	}

	public void setTacnost(boolean tacnost) {
		this.tacnost = tacnost;
	}

	public boolean isPostovanjeDog() {
		return postovanjeDog;
	}

	public void setPostovanjeDog(boolean postovanjeDog) {
		this.postovanjeDog = postovanjeDog;
	}
	
	
	
	
}
