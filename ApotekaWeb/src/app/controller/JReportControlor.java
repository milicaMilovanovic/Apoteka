package app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.reports.KorisniciSaNepraznomKorpomDS;
import app.reports.ProizvodiManjeKolicineDS;
import app.repository.KorisnikRepo;
import app.repository.ProizvodiRepo;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping(value="/reports")
public class JReportControlor {

	private JRDataSource jrds;
	
	@Autowired 
	ProizvodiRepo pr;
	
	@Autowired
	KorisnikRepo kr;
	
	public JReportControlor() throws JRException {
		
	}
		
	@RequestMapping(value="/ProizvodiManjeKolicine.pdf", method=RequestMethod.POST) 
	public String showProizvodi(Model model, HttpServletRequest request) throws JRException {
		int kolicina = Integer.parseInt(request.getParameter("kolicina"));
		ProizvodiManjeKolicineDS ds = new ProizvodiManjeKolicineDS(pr, kolicina);
		jrds = ds.create(null);
		model.addAttribute("datasource", jrds);
		model.addAttribute("format", "pdf");
		String proslediti = request.getParameter("kolicina");
		model.addAttribute("kolicina", proslediti);
		
		return "rpt_proizvodiManjeKolicine";
		
	}
	
	@RequestMapping(value="/KorisniciNeprazneKorpe.pdf", method=RequestMethod.POST) 
	public String showKorisnici(Model model, HttpServletRequest request) throws JRException {
		KorisniciSaNepraznomKorpomDS ds = new KorisniciSaNepraznomKorpomDS(kr);
		jrds = ds.create(null);
		model.addAttribute("datasource", jrds);
		model.addAttribute("format", "pdf");
		
		return "rpt_korisniciSaNepraznomKorpom";
	}
	
}
