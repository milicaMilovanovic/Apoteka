package app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;*/
/*import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.repository.KorisnikRepo;
import app.repository.ProizvodiRepo;
import model.Korisnik;
import model.Proizvod;

@Controller
@RequestMapping(value="/prijava")
public class PrijavaControlor {

	@Autowired
	private KorisnikRepo kr;
	
	@Autowired
	private ProizvodiRepo pr;
	
		
	@RequestMapping(value="login", method=RequestMethod.POST) 
	public String login(Model model, HttpServletRequest request) {
		
		if (request.getParameter("username").equals("")) {
			model.addAttribute("pokusanlogin", true);
			model.addAttribute("nijeUneto", true);
			return "login";
		}
		if (request.getParameter("password").equals("")) {
			model.addAttribute("pokusanlogin", true);
			model.addAttribute("nijeUneto", true);
			return "login";
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Korisnik korisnik = kr.loginUser(username, password);
		if (korisnik != null) {
			model.addAttribute("korisnik", korisnik);
			
			boolean admin = false;
			if (korisnik.getRole().equals("ADMIN")) {
				admin = true;
			}
			request.getSession().setAttribute("korisnikAdmin", admin);
			request.getSession().setAttribute("korisnik", korisnik.getUsername());
			List<Proizvod> proizvodi = pr.getSviProizvodi();
			model.addAttribute("proizvodi", proizvodi);
			return "pocetna";
		}
		else {
			model.addAttribute("pokusanlogin", true);
			model.addAttribute("loginFailed", true);
			return "login";
		}
	}
	
	@RequestMapping(value="registracija", method=RequestMethod.POST)
	public String registracija(Model model, HttpServletRequest request) {
		Korisnik korisnik = new Korisnik();
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String ime = request.getParameter("ime");
		String prezime = request.getParameter("prezime");
		String sifra = request.getParameter("password");
				
		String role = "USER";
		
		if (korisnik.equals("") || korisnik.equals("") || ime.equals("") || email.equals("") || prezime.equals("") || sifra.equals("")) {
			model.addAttribute("nemaParam", true);
			model.addAttribute("pokusanoReg1", true);
			return "registracija";
		}
		
		korisnik.setUsername(username);
		korisnik.setEmail(email);
		korisnik.setIme(ime);
		korisnik.setPrezime(prezime);
		korisnik.setSifra(sifra);
		korisnik.setRole(role);
		
		Korisnik k = kr.findByUsername("username");
		if (k != null) {
			model.addAttribute("postojiReg", true);
			model.addAttribute("nadjenUser", true);
			return "registracija";
		}
		
		boolean uspelo = kr.saveKorisnik(korisnik);
		
		if (uspelo) {
			return "login";
		}
		else {
			model.addAttribute("uspelo", uspelo);
			model.addAttribute("pokusanoReg", true);
			return "registracija";
		}
	}
	
	@RequestMapping(value = "greska", method = RequestMethod.GET)
    public String deniedPage() {    
          return "greska";
    }
	
	@RequestMapping(value="logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request) {
        request.getSession().setAttribute("korisnik", "");
       	request.getSession().setAttribute("korisnikAdmin", false);
        return "login";
    }

}
