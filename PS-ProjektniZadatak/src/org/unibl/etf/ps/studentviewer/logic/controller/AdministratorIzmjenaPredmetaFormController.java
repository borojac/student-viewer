package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.AdministratorIzmjenaPredmetaForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class AdministratorIzmjenaPredmetaFormController {
	
	private AdministratorIzmjenaPredmetaForm administratorIzmjenaPredmetaForm;
	
	public AdministratorIzmjenaPredmetaFormController(AdministratorIzmjenaPredmetaForm administratorIzmjenaPredmetaForm) {
		this.administratorIzmjenaPredmetaForm = administratorIzmjenaPredmetaForm;
	}
	
	public void izmjeniPredmet() {
		String sifra = administratorIzmjenaPredmetaForm.getSifra();
		String nazivPredmeta = administratorIzmjenaPredmetaForm.getNazivPredmeta();
		short ects = administratorIzmjenaPredmetaForm.getEcts();
		short semestar = administratorIzmjenaPredmetaForm.getSemestar();
		char tipPredmeta = administratorIzmjenaPredmetaForm.getTipPredmeta();
		String nazivSP = administratorIzmjenaPredmetaForm.getStudijskiProgram();
		String skolskaGodina = administratorIzmjenaPredmetaForm.getSkolskaGodina();
		short ciklus = administratorIzmjenaPredmetaForm.getCiklus();
		if("".equals(sifra) || "".equals(nazivPredmeta) || ects == -1 || semestar == -1) {
			JOptionPane.showMessageDialog(administratorIzmjenaPredmetaForm, "Niste popunili sva polja.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		} else if(ects == 0 || semestar == 0) {
			
		} else {
			PredmetDTO predmetStari = administratorIzmjenaPredmetaForm.getPredmetDTO();
			PredmetDTO predmetNovi = new PredmetDTO(predmetStari.getPredmetId(), sifra, nazivPredmeta, ects, semestar, tipPredmeta, nazivSP, skolskaGodina, ciklus);
			
			MySQLDAOFactory factory = new MySQLDAOFactory();
			PredmetDAO predmetDAO = factory.getPredmetDAO();
			
			if(predmetDAO.updatePredmet(predmetStari, predmetNovi)) {
				administratorIzmjenaPredmetaForm.getAdminPredmetiForm().initTable();
				JOptionPane.showMessageDialog(administratorIzmjenaPredmetaForm, "Predmet uspjesno dodan.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
				administratorIzmjenaPredmetaForm.dispose();
				AdminPredmetiFormController.resetChooseAddTypeFormOpened();
				AdminPredmetiFormController.resetAddFormOpened();
			} else {
				JOptionPane.showMessageDialog(administratorIzmjenaPredmetaForm, "Predmet nije dodan.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

}
