package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the proizvod database table.
 * 
 */
@Entity
@NamedQuery(name="Proizvod.findAll", query="SELECT p FROM Proizvod p")
public class Proizvod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPro;

	private int cena;

	private String ime;

	private int kolicina;

	private String opis;

	@Lob
	private byte[] slika;

	//bi-directional many-to-one association to Kategorija
	@ManyToOne
	private Kategorija kategorija;

	//bi-directional many-to-many association to Korpa
	@ManyToMany
	@JoinTable(
		name="korpa_has_proizvod"
		, joinColumns={
			@JoinColumn(name="proizvod_idPro")
			}
		, inverseJoinColumns={
			@JoinColumn(name="korpa_korisnik_username")
			}
		)
	private List<Korpa> korpas;

	public Proizvod() {
	}

	public int getIdPro() {
		return this.idPro;
	}

	public void setIdPro(int idPro) {
		this.idPro = idPro;
	}

	public int getCena() {
		return this.cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public int getKolicina() {
		return this.kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public byte[] getSlika() {
		return this.slika;
	}

	public void setSlika(byte[] slika) {
		this.slika = slika;
	}

	public Kategorija getKategorija() {
		return this.kategorija;
	}

	public void setKategorija(Kategorija kategorija) {
		this.kategorija = kategorija;
	}

	public List<Korpa> getKorpas() {
		if (korpas == null) {
			korpas = new ArrayList<>();
		}
		return this.korpas;
	}

	public void setKorpas(List<Korpa> korpas) {
		this.korpas = korpas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPro;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Proizvod other = (Proizvod) obj;
		if (idPro != other.idPro)
			return false;
		return true;
	}
	

}