package app.domain;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import model.Kategorija;

public class ProizvodImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int idPro;
	private int cena;
	private String ime;
	private int kolicina;
	private String opis;
	private MultipartFile slika;
	private Kategorija kategorija;
	
	public int getIdPro() {
		return idPro;
	}
	public void setIdPro(int idPro) {
		this.idPro = idPro;
	}
	public int getCena() {
		return cena;
	}
	public void setCena(int cena) {
		this.cena = cena;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public int getKolicina() {
		return kolicina;
	}
	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public MultipartFile getSlika() {
		return slika;
	}
	public void setSlika(MultipartFile slika) {
		this.slika = slika;
	}
	
	public Kategorija getKategorija() {
		return kategorija;
	}
	
	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}
	//mozda ovde hash i equals
	
}
