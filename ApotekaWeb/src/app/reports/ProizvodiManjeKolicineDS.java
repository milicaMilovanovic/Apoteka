package app.reports;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import app.repository.ProizvodiRepo;
import model.Proizvod;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ProizvodiManjeKolicineDS extends JRAbstractBeanDataSourceProvider {

	private List<Proizvod> proizvodi;
	private int kolicina;
	private  ProizvodiRepo pr;
	
	public ProizvodiManjeKolicineDS(ProizvodiRepo pr, int kolicina) {
		super(Proizvod.class);
		this.kolicina = kolicina;
		this.pr = pr;
	}

	@Override
	public JRDataSource create(JasperReport arg0) throws JRException {
		proizvodi = pr.getProizvodiManjeKolicine(kolicina);
		if (proizvodi == null) {
			proizvodi = new ArrayList<>();
		}
		proizvodi.sort(new KomparatorProizvodaPoKolicini());
		return new JRBeanCollectionDataSource(proizvodi);
	}

	@Override
	public void dispose(JRDataSource arg0) throws JRException {
		proizvodi = null;
	}
	
}

class KomparatorProizvodaPoKolicini implements Comparator<Proizvod> {
	
	public int compare(Proizvod jedan, Proizvod dva) {
		int rezultat = jedan.getKolicina() - dva.getKolicina();
		return rezultat;
	}
	
}
