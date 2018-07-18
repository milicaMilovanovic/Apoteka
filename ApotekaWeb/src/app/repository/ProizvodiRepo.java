package app.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.Kategorija;
import model.Proizvod;

@Repository
@Transactional
public class ProizvodiRepo {

	@PersistenceContext
	private EntityManager em;
	
	public List<Proizvod> getSviProizvodi() {
		try {
			Query q = em.createQuery("SELECT p FROM Proizvod p");
			return q.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Kategorija> getSveKategorije() {
		try {
			Query q = em.createQuery("SELECT k FROM Kategorija k");
			return q.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean dodajProizvod(Proizvod p, int idKat) {
		try {
			if (idKat != -1) {
				Kategorija k = em.find(Kategorija.class, idKat);
				p.setKategorija(k);
			}
			em.persist(p);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean obrisiProizvod(int idP) {
		try {
			Proizvod p = em.find(Proizvod.class, idP);
			em.remove(p);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean dodajKategoriju(Kategorija k, String [] nizProizvoda) {
		try {
			for (int i=0; i<nizProizvoda.length; i++) {
				int idProizvoda = Integer.parseInt(nizProizvoda[i]);
				Proizvod p = em.find(Proizvod.class, idProizvoda);
				p.setKategorija(k);
				k.getProizvods().add(p);
			}
			em.persist(k);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean obrisiKategoriju(int idK) { 
		try {
			Kategorija k = em.find(Kategorija.class, idK);
			Query q = em.createQuery("SELECT p FROM Proizvod p WHERE p.kategorija.idKat = :idKat");
			q.setParameter("idKat", idK);
			List<Proizvod> listaProizvoda = q.getResultList();
			for (Proizvod p : listaProizvoda) {
				p.setKategorija(null);
			}
			em.remove(k);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Proizvod> getProizvodiBezKategorije() {
		try {
			Query q = em.createQuery("SELECT p FROM Proizvod p");
			List<Proizvod> proizvodi = q.getResultList();
			List<Proizvod> rezultat = new ArrayList<>();
			for(Proizvod p : proizvodi) {
				if (p.getKategorija() == null) 
					rezultat.add(p);
			}
			return rezultat;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public Proizvod getProizvod(int idP) {
		try {
			Query q = em.createQuery("SELECT p FROM Proizvod p WHERE p.idPro = :idP");
			q.setParameter("idP", idP);
			List<Proizvod> lista = q.getResultList();
			if (lista.isEmpty()) {
				return null;
			}
			return lista.get(0);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean proveriDostupnostProizvoda(int idP) {
		try {
			Query q = em.createQuery("SELECT p FROM Proizvod p WHERE p.idPro = :idP");
			q.setParameter("idP", idP);
			List<Proizvod> lista = q.getResultList();
			if (lista.isEmpty()) {
				return false;
			}
			int kolicina = lista.get(0).getKolicina();
			if (kolicina != 0) {
				return true;
			}
			else {
				return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	public String getImeKategorije(int idK) {
		try {
			Query q = em.createQuery("SELECT k FROM Kategorija k WHERE k.idKat = :id");
			q.setParameter("id", idK);
			Kategorija k = (Kategorija) q.getResultList().get(0);
			return k.getNaziv();
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		} 
	}
	
	public List<Proizvod> getProizvodiKategorije(int idK) {
		try {
			Query q = em.createQuery("SELECT p FROM Proizvod p WHERE p.kategorija.idKat = :id");
			q.setParameter("id", idK);
			return q.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public List<Proizvod> getProizvodiManjeKolicine(int kolicina) {
		try {
			Query q = em.createQuery("SELECT p FROM Proizvod p WHERE p.kolicina <= :kol");
			q.setParameter("kol", kolicina);
			return q.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
