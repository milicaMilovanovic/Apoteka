package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;


/**
 * The persistent class for the korpa database table.
 * 
 */
@Entity
@NamedQuery(name="Korpa.findAll", query="SELECT k FROM Korpa k")
public class Korpa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="korisnik_username")
	private String korisnikUsername;

	//bi-directional one-to-one association to Korisnik
	@OneToOne(mappedBy="korpa")
	private Korisnik korisnik;

	//bi-directional many-to-many association to Proizvod
	@ManyToMany(mappedBy="korpas")
	private List<Proizvod> proizvods;

	public Korpa() {
	}

	public String getKorisnikUsername() {
		return this.korisnikUsername;
	}

	public void setKorisnikUsername(String korisnikUsername) {
		this.korisnikUsername = korisnikUsername;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public List<Proizvod> getProizvods() {
		if (this.proizvods == null) {
			this.proizvods = new ArrayList<>();
		}
		return this.proizvods;
	}

	public void setProizvods(List<Proizvod> proizvods) {
		this.proizvods = proizvods;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((korisnikUsername == null) ? 0 : korisnikUsername.hashCode());
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
		Korpa other = (Korpa) obj;
		if (korisnikUsername == null) {
			if (other.korisnikUsername != null)
				return false;
		} else if (!korisnikUsername.equals(other.korisnikUsername))
			return false;
		return true;
	}
	

}