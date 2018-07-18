package managers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Kategorija;
import model.Korisnik;
import model.Korpa;
import model.Proizvod;

public class KorpaManager {

	public Proizvod saveProizvod(int cena, String ime, String opis, int kolicina, int kat) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			Proizvod p = new Proizvod();
			p.setCena(cena);
			p.setIme(ime);
			p.setOpis(opis);
			p.setKolicina(kolicina);
			Kategorija kategorija = em.find(Kategorija.class, kat);
			p.setKategorija(kategorija);
			em.persist(p);
			kategorija.getProizvods().add(p);
			em.getTransaction().commit();
			return p;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Kategorija saveKategorija(String naziv) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			Kategorija k = new Kategorija();
			k.setNaziv(naziv);
			em.persist(k);
			em.getTransaction().commit();
			return k;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Korisnik saveKorisnik(String username, String email, String ime,
			String prezime, String sifra) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			Korisnik k = new Korisnik();
			Korpa korpa = new Korpa();
			k.setUsername(username);
			k.setEmail(email);
			k.setIme(ime);
			k.setPrezime(prezime);
			k.setSifra(sifra);
			korpa.setKorisnikUsername(username);
			korpa.setKorisnik(k);
			k.setKorpa(korpa);
			em.persist(k);
			em.persist(korpa);
			em.getTransaction().commit();
			return k;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Korpa saveKorpa(String korisnik) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			Korpa k = new Korpa();
			Korisnik kor = em.find(Korisnik.class, korisnik);
			if (kor == null) {
				System.out.println("korisnik je null");
				return null;
			}
			k.setKorisnikUsername(korisnik);
			k.setKorisnik(kor);
            em.persist(k);
            kor.setKorpa(k);
            em.merge(kor);
			em.getTransaction().commit();
			return k;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*public boolean dodeliKorpuKorisniku(Korpa k, String username) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			Korisnik korisnik = em.find(Korisnik.class, username);
			korisnik.setKorpa(k);
			em.merge(korisnik);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
	public List<Korpa> getKorpeKorisnik(String username) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			System.out.println("1");
			Query q = em.createQuery("Select k from Korpa k");
			System.out.println("2 posle sql-a");
			List<Korpa> lista = q.getResultList();
			if (lista.isEmpty()) 
				System.out.println("Lista je prazna");
			else
				System.out.println("Lista nije prazna");
			System.out.println("4 posle dobijanja rezultata upita");
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("5 izuzetak");
			return null;
		}
	}
	
	public List<Korisnik> getKorisnici() {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			System.out.println("1");
			Query q = em.createQuery("Select k from Korisnik k");// where k.korisnik.username=:ime");
			System.out.println("2 posle sql-a");
			List<Korisnik> lista = q.getResultList();
			if (lista.isEmpty()) {
				System.out.println("Lista je prazna");
				return null;
			}
			else
				System.out.println("Lista nije prazna");
			System.out.println("4 posle dobijanja rezultata upita");
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("5 izuzetak");
			return null;
		}
	}
	
	public List<Proizvod> getProizvodi() {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			System.out.println("1");
			Query q = em.createQuery("Select p from Proizvod p");// where k.korisnik.username=:ime");
			System.out.println("2 posle sql-a");
			List<Proizvod> lista = q.getResultList();
			if (lista.isEmpty()) 
				System.out.println("Lista je prazna");
			else
				System.out.println("Lista nije prazna");
			System.out.println("4 posle dobijanja rezultata upita");
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("5 izuzetak");
			return null;
		}
	}
	
	public boolean obrisiKorisnika(String username) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			Korisnik k = em.find(Korisnik.class, username);
			em.getTransaction().begin();
			em.remove(k);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();getClass(); 
			return false;
		}
	}
	
	public boolean dodajProizvodUKorpu(int idP, Korisnik k) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			
			Proizvod p = em.find(Proizvod.class, idP);
			System.out.println( p.getIme());
			Korpa korpa = k.getKorpa();
			System.out.println(korpa.getKorisnikUsername());
			korpa.getProizvods().add(p);
			
			p.getKorpas().add(korpa);
			
			em.merge(korpa);
			
			em.merge(p);
			
			em.getTransaction().commit();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Proizvod> getProizvodiVeceKolicine(int kolicina) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			Query q = em.createQuery("SELECT p FROM Proizvod p WHERE p.kolicina >= :kol AND p.kategorija is not null");
			q.setParameter("kol", kolicina);
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public List<Korisnik> getKorisniciSaNepraznomKorpom() {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			System.out.println("baza 0 ");
			Query q = em.createQuery("SELECT k FROM Korisnik k");
			List<Korisnik> lista = q.getResultList();
			System.out.println("baza1");
			if(lista.isEmpty()) {
				System.out.println("Lista je prazna");
				return null;
			}
			else {
				System.out.println("Lista nije prazna");
			}
			List<Korisnik> rezultat = new ArrayList<>();
			for(Korisnik k : lista) {
				if (!k.getKorpa().getProizvods().isEmpty()) {
					rezultat.add(k);
					System.out.println("Dodat je korisnik" + k.getUsername());
				}
				else {
					System.out.println("nije dodat korisnik" + k.getUsername());
				}
			}
			return rezultat;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Korisnik getKorisnik(String username) {
		try {
			EntityManager em = JPAUtil.getEntityManager();
			Query q = em.createQuery("SELECT k FROM Korisnik k WHERE k.username = :use");
			q.setParameter("use", username);
			List<Korisnik> lista = q.getResultList();
			if (!lista.isEmpty()) {
				return lista.get(0);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	
	
	public static void main(String [] args) {
		KorpaManager km = new KorpaManager();
		
		Korisnik k = km.getKorisnik("jova");
		System.out.println(k.getKorpa().getProizvods().size());
		
		/*Kategorija k1 = km.saveKategorija("kategorija3");
		
		Proizvod p1 = km.saveProizvod(1000, "lek3", "opis lek1", 50, k1.getIdKat());
		
		if (p1 != null)
			System.out.println(p1.getIme() + " " + p1.getCena() + "din, ");
		else 
			System.out.println("nije uspelo dodavanje proizvoda"); 
		
		Korisnik korisnik = km.saveKorisnik("ivana11", "milica@gmail.com", "milica", "milovanovic", "1234");*/
		/*if (korisnik!=null) 
			System.out.println("uspesno unet korisnik");
		else 
			System.out.println("neuspesno unet korisnik");*/
		
		/*Korpa korpa1 = km.saveKorpa("ivana1");*/
		/*System.out.println(korisnik.getKorpa().getKorisnikUsername());*/
		
		/*if (korpa1 != null) {
			System.out.println("Korpa " + korpa1.getKorisnik().getUsername());
		}*/
		
		/*List<Korpa> korpe = km.getKorpeKorisnik("jova");
		for(Korpa k : korpe) {
			System.out.println(k.getProizvods().size() + " ");
		}*/
			
		/*List<Korisnik> korisnici = km.getKorisniciSaNepraznomKorpom();
		for(Korisnik k : korisnici) {
			System.out.println(k.getUsername() + " " + k.getPrezime());
		}*/
		
		/*List<Proizvod> proizvodi = km.getProizvodiVeceKolicine(0);
		for(Proizvod p : proizvodi) {
			System.out.println(p.getIme() + " " + p.getIdPro() + " " + p.getKolicina());
		} */
			
		/*if (km.obrisiKorisnika("jovanina"))
			System.out.println("uspelo");
		else
			System.out.println("nije uspelo");*/
		
		/*if (km.dodajProizvodUKorpu(1, korisnik)) {
			System.out.println("Uspesno dodeljeni proizvodi korpi i ima ih");
			
			System.out.println(korisnik.getKorpa().getProizvods().size());
			for (Proizvod p : korisnik.getKorpa().getProizvods()) {
				System.out.println(p.getIme() + " " + p.getIdPro());
			}
		}
	  else 
			System.out.println("NEUspesno dodeljeni proizvodi korpi"); */

		/*if (korisnik.getKorpa() == null) {
		System.out.println("korisnik nije dodeljen korpi");
	}
	else 
		System.out.println("KORISNIK USPESNO DODELJEN KORPI korpi"); */

		/*String s = "1  :23 :544:3  :5  :";
		String [] niz = s.split(":");
		System.out.println(niz[1]);
		String str = niz[1].trim();
		System.out.println(str);
		
		System.out.println();*/
		// od 0 do i * 4 
		/*String pod1 = s.substring(0, 3*4);*/
		/*String pod1 = s.substring(0, 0);
		System.out.println(pod1);
		String pod2 = s.substring(3*4+2+1, s.length());
		String pod2 = s.substring(0 * 4 +2 +1);
		System.out.println(pod2);
		System.out.println(pod1 + "33 " + pod2);*/
		
	/*	System.out.println(niz[1]);*/
		
		
		
	}
	
}
