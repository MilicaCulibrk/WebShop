package beans;

import java.util.ArrayList;



public class Prodavac extends Korisnik {
	private ArrayList<String> objavljeniOglasi;
	private ArrayList<String> isporuceniProizvodi;
	private int likes;
	private int dislikes;
	public Prodavac() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Prodavac(String id, String korisnickoIme, String lozinka, String ime, String prezime, String telefon,
			String grad, String email, String datum, Uloga uloga) {
		super(id, korisnickoIme, lozinka, ime, prezime, telefon, grad, email, datum, uloga);
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
	
	

}
