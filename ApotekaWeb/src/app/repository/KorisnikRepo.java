package app.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.Korisnik;
import model.Korpa;
import model.Proizvod;

@Repository
@Transactional
public class KorisnikRepo {
	
	@PersistenceContext 
	private EntityManager em;
	
	public Korisnik loginUser(String username, String password) {
		try {
			Query q = em.createQuery("SELECT k FROM Korisnik k WHERE k.username = :username AND k.sifra = :password");
			q.setParameter("username", username);
			q.setParameter("password", password);
			List<Korisnik> lista = q.getResultList();
			if (lista.isEmpty()) {
				return null;
			}
			return lista.get(0);
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public boolean saveKorisnik(Korisnik k) {
		try {
			Query q = em.createQuery("SELECT k FROM Korisnik k WHERE k.username = :name");
			q.setParameter("name", k.getUsername());
			List<Korisnik> lista = q.getResultList();
			if (!lista.isEmpty()) {
				return false;
			}
			Korpa korpa = new Korpa();
			korpa.setKorisnikUsername(k.getUsername());
			korpa.setKorisnik(k);
			k.setKorpa(korpa);
			em.persist(k);
			em.persist(korpa);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Korisnik findByUsername(String username) {
		try {
			Query q = em.createQuery("SELECT k FROM Korisnik k WHERE k.username = :username");
			q.setParameter("username", username);
			List<Korisnik> lista = q.getResultList();
			if (lista.isEmpty()) {
				return null;
			}
			else {
				return lista.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean dodajUKorpu(String username, int idP) {
		try {
			Korisnik korisnik = em.find(Korisnik.class, username);
			Korpa korpa = korisnik.getKorpa();
			Proizvod p = em.find(Proizvod.class, idP);
			
			korpa.getProizvods();/*.size();*/
			
			if (korpa.getProizvods().contains(p)) {
				return false;
			}
			korpa.getProizvods().add(p);
			p.getKorpas().add(korpa);
			em.merge(korisnik);
			em.merge(korpa);
			em.merge(p);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public List<Proizvod> getProizvodiKorisnika(String username) {
		try {
			Korisnik korisnik = em.find(Korisnik.class, username);
			korisnik.getKorpa().getProizvods().size();
			List<Proizvod> rezultat = korisnik.getKorpa().getProizvods();
			return rezultat;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean obrisiJednogIzKorpe(String username, int idP) {
		try {
			Korisnik korisnik = em.find(Korisnik.class, username);
			Korpa korpa = korisnik.getKorpa();
			Proizvod proizvod = em.find(Proizvod.class, idP);

			korisnik.getKorpa().getProizvods().size();
			int i = 0;
			for(Proizvod p : korpa.getProizvods()) {
				if (p.getIdPro() == idP) {
					korpa.getProizvods().remove(i);
					break;
				}
				++i;
			}
			i=0;
			proizvod.getKorpas().size();
			for (Korpa k : proizvod.getKorpas()) {
				if (k.getKorisnikUsername().equals(username)) {
					proizvod.getKorpas().remove(i);
					break;
				}
				++i;
			}
			em.merge(korisnik);
			em.merge(korpa);
			em.merge(proizvod);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	public boolean obrisiSveIzKorpe(String username) {
		try {
			Korisnik korisnik = em.find(Korisnik.class, username);
			Korpa korpa = korisnik.getKorpa();
			korpa.setProizvods(null);
			
			Query q = em.createQuery("SELECT p FROM Proizvod p");
			List<Proizvod> proizvodi = q.getResultList();
			for(Proizvod p : proizvodi) {
				if (p.getKorpas().contains(korpa)) {
					p.getKorpas().remove(korpa);
					em.merge(p);
				}
			}
			em.merge(korisnik);
			em.merge(korpa);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	public boolean kupiProizvod(String username, int idP) {
		try {
			Korisnik korisnik = em.find(Korisnik.class, username);
			Korpa korpa = korisnik.getKorpa();
			Proizvod proizvod = em.find(Proizvod.class, idP);
			
			korpa.getProizvods().remove(proizvod);
			proizvod.setKolicina(proizvod.getKolicina()-1);
			
			proizvod.getKorpas().size();
			int i = 0;
			for (Korpa k : proizvod.getKorpas()) {
				if (k.getKorisnikUsername().equals(username)) {
					proizvod.getKorpas().remove(i);
					break;
				}
				++i;
			}
			em.merge(korisnik);
			em.merge(korpa);
			em.merge(proizvod);  
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	public List<Korisnik> getKorisniciSaNepraznomKorpom() {
		try {
			Query q = em.createQuery("SELECT k FROM Korisnik k");
			List<Korisnik> lista = q.getResultList();
			if(lista.isEmpty()) {
				System.out.println("Lista je prazna");
				return null;
			}
			else {
			}
			List<Korisnik> rezultat = new ArrayList<>();
			for(Korisnik k : lista) {
				if (!k.getRole().equals("ADMIN")) {
					k.getKorpa().getProizvods().size();
					if (!k.getKorpa().getProizvods().isEmpty()) {
						rezultat.add(k);
					}
					else {
					}
				}
			}
			return rezultat;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
