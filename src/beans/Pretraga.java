package beans;

public class Pretraga {

	private String naziv;
	private String maxcena;
	private String mincena;
	private String minocena;
	private String maxocena;
	private String grad;
	private String uloga;
	private String minDatum;
	private String maxDatum;
	
	public Pretraga() {

	}

	public Pretraga(String naziv, String maxcena, String mincena, String minocena, String maxocena, String grad,
			String uloga) {
		super();
		this.naziv = naziv;
		this.maxcena = maxcena;
		this.mincena = mincena;
		this.minocena = minocena;
		this.maxocena = maxocena;
		this.grad = grad;
		this.uloga = uloga;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getMaxcena() {
		return maxcena;
	}

	public void setMaxcena(String maxcena) {
		this.maxcena = maxcena;
	}



	public String getMincena() {
		return mincena;
	}



	public void setMincena(String mincena) {
		this.mincena = mincena;
	}



	public String getMinocena() {
		return minocena;
	}



	public void setMinocena(String minocena) {
		this.minocena = minocena;
	}



	public String getMaxocena() {
		return maxocena;
	}



	public void setMaxocena(String maxocena) {
		this.maxocena = maxocena;
	}



	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getUloga() {
		return uloga;
	}
	public void setUloga(String uloga) {
		this.uloga = uloga;
	}

	public String getMinDatum() {
		return minDatum;
	}

	public void setMinDatum(String minDatum) {
		this.minDatum = minDatum;
	}

	public String getMaxDatum() {
		return maxDatum;
	}

	public void setMaxDatum(String maxDatum) {
		this.maxDatum = maxDatum;
	}
	
}	
	
	
	
