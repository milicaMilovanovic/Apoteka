package app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.repository.KorisnikRepo;
import app.repository.ProizvodiRepo;
import model.Kategorija;
import model.Korisnik;
import model.Proizvod;

@Controller
@RequestMapping(value="proizvodi")
public class ProizvodiControlor {

	@Autowired
	private ProizvodiRepo pr;
	
	@Autowired
	private KorisnikRepo kr;
	
	@RequestMapping(value="sortiraj", method=RequestMethod.GET)
	public String sort(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		
		String kriterijum = request.getParameter("kriterijum");
		List<Proizvod> proizvodi = new ArrayList<>();
		if (!request.getParameterMap().containsKey("kriterijum")) {
			proizvodi = pr.getSviProizvodi();
		}
		else if (kriterijum.equals("cena")) {
			proizvodi = sortPoCeni();
		}
		else if (kriterijum.equals("leksikografski")) {
			proizvodi = sortLeksikografski();
		}
		else {
			proizvodi = sortPoKolicini();
		}
		model.addAttribute("proizvodi", proizvodi);
		return "pocetna";
	}
	
	private List<Proizvod> sortPoCeni() {
		List<Proizvod> proizvodi = pr.getSviProizvodi();
		proizvodi.sort(new KomparatorPoCeni());
		return proizvodi;
	}
	
	private List<Proizvod> sortLeksikografski() {
		List<Proizvod> proizvodi = pr.getSviProizvodi();
		proizvodi.sort(new KomparatorLeksikografski());
		return proizvodi;
	}
	
	private List<Proizvod> sortPoKolicini() {
		List<Proizvod> proizvodi = pr.getSviProizvodi();
		proizvodi.sort(new KomparatorPoKolicini());
		return proizvodi;
	}
	
	@RequestMapping(value="prikazJednog", method=RequestMethod.GET)
	public String prikaziJednog(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		
		int idP = Integer.parseInt(request.getParameter("idP"));
		Proizvod proizvod = pr.getProizvod(idP);
		model.addAttribute("proizvod", proizvod);
		return "proizvodPrikaz";
	}
	
	@RequestMapping(value="getimage/{id}", method = RequestMethod.GET)
	public void getKnjiga(@PathVariable("id") int idPro, HttpServletResponse response) {	
		Proizvod p = pr.getProizvod(idPro);
		byte[] slika = p.getSlika();
		if (p.getSlika() != null) {
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			try {
				response.getOutputStream().write(slika);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value="dodajUKorpu", method=RequestMethod.GET)
	public String dodajUKorpu(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		
		int idP = Integer.parseInt(request.getParameter("idP"));
		String korisnik = (String) request.getSession().getAttribute("korisnik");
		Korisnik k = kr.findByUsername(korisnik);
		if (k.getRole().equals("ADMIN")) {
			Proizvod p = pr.getProizvod(idP);
			model.addAttribute("uspelo", false);
			model.addAttribute("pokusanoAdmin", true);
			model.addAttribute(p);
			return "proizvodPrikaz";
		}
		boolean uspelo = kr.dodajUKorpu(korisnik, idP);
		
		if (uspelo) {
			return prikaziKorpu(model, request);
		}
		else {
			Proizvod p = pr.getProizvod(idP);
			model.addAttribute(p);
			model.addAttribute("uspelo", false);
			model.addAttribute("pokusanoKupiti", true);
			return "proizvodPrikaz";
		}
		
	}
	
	@RequestMapping(value="prikaziKorpu", method=RequestMethod.GET)
	public String prikaziKorpu(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		if (request.getSession().getAttribute("korisnikAdmin") == (Boolean) true) {
			return "greska";
		}
		
		String korisnik = (String) request.getSession().getAttribute("korisnik");
		List<Proizvod> listaProizvoda = kr.getProizvodiKorisnika(korisnik);
		model.addAttribute("proizvodi", listaProizvoda);
		return "korpa";
	}
	
	@RequestMapping(value="obrisiJednogIzKorpe", method=RequestMethod.GET)
	public String obrisiJednogIzKorpe(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		if (request.getSession().getAttribute("korisnikAdmin") == (Boolean) true) {
			return "greska";
		}
		
		int idP = Integer.parseInt(request.getParameter("idP"));
		String korisnik = (String) request.getSession().getAttribute("korisnik");
		kr.obrisiJednogIzKorpe(korisnik, idP);
		return prikaziKorpu(model, request);
	}
	
	@RequestMapping(value="obrisiSveIzKorpe", method=RequestMethod.GET)
	public String obrisiSveIzKorpe(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		if (request.getSession().getAttribute("korisnikAdmin") == (Boolean) true) {
			return "greska";
		}
		
		String korisnik = (String) request.getSession().getAttribute("korisnik");
		kr.obrisiSveIzKorpe(korisnik);
		return prikaziKorpu(model, request);
	}
	
	@RequestMapping(value="kupiProizvod", method=RequestMethod.GET)
	public String kupiProizvod(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		if (request.getSession().getAttribute("korisnikAdmin") == (Boolean) true) {
			return "greska";
		}
		
		int idP = Integer.parseInt(request.getParameter("idP"));
		String korisnik = (String) request.getSession().getAttribute("korisnik");
		boolean dostupno = pr.proveriDostupnostProizvoda(idP);
		if (!dostupno) {
			model.addAttribute("pokusanoKupiti", true);
			model.addAttribute(dostupno);
		}
		else {
			kr.kupiProizvod(korisnik, idP);
		}
		return prikaziKorpu(model, request);
	}
	
	@RequestMapping(value="stranicaKategorije", method=RequestMethod.GET)
	public String stranicaKategorije(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		List<Kategorija> kategorije = pr.getSveKategorije();
		model.addAttribute("sveKategorije", kategorije);
		return "kategorije";
	}
	
	@RequestMapping(value="prikaziKategoriju", method=RequestMethod.GET)
	public String prikaziKategoriju(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		int idK = Integer.parseInt(request.getParameter("idK"));
		model.addAttribute("imaKategorija", true);
		String imeKat = pr.getImeKategorije(idK);
		model.addAttribute("imeKategorija", imeKat);
		List<Proizvod> proizvodiKategorije = pr.getProizvodiKategorije(idK);
		model.addAttribute("proizvodi", proizvodiKategorije);
		return "pocetna";
	}
	
	@RequestMapping(value="prikazPocetne", method=RequestMethod.GET)
	public String prikazPocetne(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		List<Proizvod> proizvodi = pr.getSviProizvodi();
		model.addAttribute("proizvodi", proizvodi);
		return "pocetna";
	}
	
	
}


class KomparatorPoCeni implements Comparator<Proizvod> {
	  
	  public int compare (Proizvod jedan, Proizvod dva) {
	    int rezultat=jedan.getCena() - dva.getCena();
	    return rezultat;
	  }
	  
}

class KomparatorLeksikografski implements Comparator<Proizvod> {
	  
	  public int compare (Proizvod jedan, Proizvod dva) {
	    int rezultat=jedan.getIme().compareTo(dva.getIme());
	    return rezultat;
	  }
	  
}

class KomparatorPoKolicini implements Comparator<Proizvod> {
	  
	  public int compare (Proizvod jedan, Proizvod dva) {
	    int rezultat=jedan.getKolicina() - dva.getKolicina();
	    return rezultat;
	  }
	  
}
