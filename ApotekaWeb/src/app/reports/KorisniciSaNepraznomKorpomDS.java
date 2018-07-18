package app.reports;

import java.util.ArrayList;
import java.util.List;

import app.repository.KorisnikRepo;
import model.Korisnik;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class KorisniciSaNepraznomKorpomDS extends JRAbstractBeanDataSourceProvider{

	private List<Korisnik> korisnici;
	private KorisnikRepo kr;
	
	public KorisniciSaNepraznomKorpomDS(KorisnikRepo kr) {
		super(Korisnik.class);
		this.kr = kr;
	}

	@Override
	public JRDataSource create(JasperReport arg0) throws JRException {
		korisnici = kr.getKorisniciSaNepraznomKorpom();
		if (korisnici == null) {
			korisnici = new ArrayList<>();
		}	
		return new JRBeanCollectionDataSource(korisnici);
	}

	@Override
	public void dispose(JRDataSource arg0) throws JRException {
		korisnici = null;
		
	}
	
	
	
}
