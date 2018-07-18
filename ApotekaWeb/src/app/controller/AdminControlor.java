package app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import app.domain.ProizvodImage;
import app.repository.ProizvodiRepo;
import model.Kategorija;
import model.Proizvod;

@Controller
@RequestMapping(value="/admin")
public class AdminControlor {
	
	@Autowired
	private ProizvodiRepo pr;
	
	@RequestMapping(value="adminovaStranica", method=RequestMethod.GET)
	public String adminovaStranica(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		if (request.getSession().getAttribute("korisnikAdmin") == (Boolean) false) {
			return "greska";
		}
		
		List<Proizvod> proizvodi = pr.getSviProizvodi();
		List<Kategorija> kategorije = pr.getSveKategorije();
		
		model.addAttribute("sviProizvodi", proizvodi);
		model.addAttribute("sveKategorije", kategorije);
		
		return "adminStranica";
	}
	
	@RequestMapping(value="dodajProizvod", method=RequestMethod.GET) 
	public String dodajProizvoda(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		if (request.getSession().getAttribute("korisnikAdmin") == (Boolean) false) {
			return "greska";
		}
		
		List<Kategorija> kategorije = pr.getSveKategorije();
		model.addAttribute("kategorije", kategorije);
		model.addAttribute("proizvodI", new ProizvodImage());
		return "dodavanjeProizvoda";
	}
	
	@RequestMapping(value="dodavanjeProizvoda1", method=RequestMethod.POST)
	public String dodavanjeProizvoda(Model model, HttpServletRequest request, @ModelAttribute("proizvodI") ProizvodImage proizvodImage) {
		
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		if (request.getSession().getAttribute("korisnikAdmin") == (Boolean) false) {
			return "greska";
		}
		
		MultipartFile file = proizvodImage.getSlika();
		if (null != file) {
			String fileName = file.getOriginalFilename();
			String filePath;
			try {
				filePath = System.getProperty("user.dir");
				System.out.println("Putanja je "+filePath);
				File imageFile = new File(filePath+"\\slike", fileName);
				
				file.transferTo(imageFile);
				Proizvod p1 = new Proizvod();
				
				p1.setIme(proizvodImage.getIme());
				p1.setCena(proizvodImage.getCena());
				p1.setKolicina(proizvodImage.getKolicina());
				p1.setOpis(proizvodImage.getOpis());
				p1.setSlika(Files.readAllBytes(imageFile.toPath()));
				
				int idKategorije = -1;
				if (request.getParameterMap().containsKey("kat")) {
					idKategorije = Integer.parseInt(request.getParameter("kat"));
				}
					
				boolean uspelo = pr.dodajProizvod(p1, idKategorije);
				if (uspelo) {
					return adminovaStranica(model, request);
				}
				else {
					model.addAttribute("pokusano", true);
					model.addAttribute("uspelo", uspelo);
					return "dodavanjeProizvoda";
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("pokusano", true);
		return "dodavanjeProizvoda";
			
	}
	
	@RequestMapping(value="obrisiProizvod", method=RequestMethod.GET)
	public String obrisiProizvod (Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		if (request.getSession().getAttribute("korisnikAdmin") == (Boolean) false) {
			return "greska";
		}
		
		int idP = Integer.parseInt(request.getParameter("idP"));
		pr.obrisiProizvod(idP);
		return adminovaStranica(model, request);
	}
	
	@RequestMapping(value="dodajKategoriju", method=RequestMethod.GET) 
	public String dodajKategoriju(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		if (request.getSession().getAttribute("korisnikAdmin") == (Boolean) false) {
			return "greska";
		}
		
		List<Proizvod> proizvodi = pr.getProizvodiBezKategorije();
		model.addAttribute("proizvodiBezKategorije", proizvodi);
		return "dodavanjeKategorije";
	}
	
	@RequestMapping(value="dodavanjeKategorije1", method=RequestMethod.POST)
	public String dodavanjeKategorije(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		if (request.getSession().getAttribute("korisnikAdmin") == (Boolean) false) {
			return "greska";
		}
		
		String naziv = request.getParameter("naziv");
		String[] listaProizvoda;
		if (request.getParameterMap().containsKey("selectProizvoda")) {
			listaProizvoda = request.getParameterValues("selectProizvoda");
		}
		else {
			listaProizvoda = new String [0];
		}
		Kategorija k = new Kategorija();
		k.setNaziv(naziv);
		boolean uspelo = pr.dodajKategoriju(k, listaProizvoda);
		if (uspelo) {
			return adminovaStranica(model, request);
		}
		else {
			model.addAttribute("uspelo", uspelo);
			model.addAttribute("pokusano", true);
			return "dodavanjeKategorije";
		}
	}
	
	@RequestMapping(value="obrisiKategoriju", method=RequestMethod.GET)
	public String obrisiKategoriju(Model model, HttpServletRequest request) {
		if (request.getSession().getAttribute("korisnik").equals("")) {
			return "greska";
		}
		if (request.getSession().getAttribute("korisnikAdmin") == (Boolean) false) {
			return "greska";
		}
		
		int idK = Integer.parseInt(request.getParameter("idK"));
		pr.obrisiKategoriju(idK);
		adminovaStranica(model, request);
		return "adminStranica";
	}

	
}
