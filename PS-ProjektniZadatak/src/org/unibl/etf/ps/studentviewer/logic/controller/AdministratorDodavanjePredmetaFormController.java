package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.AdministratorDodavanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.gui.view.AdministratorDodavanjeStudijskogProgramaForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class AdministratorDodavanjePredmetaFormController {
	
	private AdministratorDodavanjePredmetaForm administratorDodavanjePredmetaForm;
	private static boolean dodajSPOpened = false;
	
	public AdministratorDodavanjePredmetaFormController(AdministratorDodavanjePredmetaForm administratorDodavanjePredmetaForm) {
		this.administratorDodavanjePredmetaForm = administratorDodavanjePredmetaForm;
	}
	
	public void dodajPredmet() {
		String sifra = administratorDodavanjePredmetaForm.getSifra();
		String nazivPredmeta = administratorDodavanjePredmetaForm.getNazivPredmeta();
		short ects = administratorDodavanjePredmetaForm.getEcts();
		short semestar = administratorDodavanjePredmetaForm.getSemestar();
		char tipPredmeta = administratorDodavanjePredmetaForm.getTipPredmeta();
		String nazivSP = administratorDodavanjePredmetaForm.getStudijskiProgram();
		String skolskaGodina = administratorDodavanjePredmetaForm.getSkolskaGodina();
		short ciklus = administratorDodavanjePredmetaForm.getCiklus();
		if("".equals(sifra) || "".equals(nazivPredmeta) || ects == -1 || semestar == -1) {
			JOptionPane.showMessageDialog(administratorDodavanjePredmetaForm, "Niste popunili sva polja.");
		} else if(ects == 0 || semestar == 0) {
			
		} else {
			PredmetDTO predmet = new PredmetDTO(sifra, nazivPredmeta, ects, semestar, tipPredmeta, nazivSP, skolskaGodina, ciklus);
			MySQLDAOFactory factory = new MySQLDAOFactory();
			PredmetDAO predmetDAO = factory.getPredmetDAO();
			
			if(predmetDAO.addPredmet(predmet)) {
				JOptionPane.showMessageDialog(administratorDodavanjePredmetaForm, "Predmet uspjesno dodan.");
				administratorDodavanjePredmetaForm.dispose();
				AdministratorFormController.resetChooseAddTypeFormOpened();
				AdministratorFormController.resetAddFormOpened();
			} else {
				JOptionPane.showMessageDialog(administratorDodavanjePredmetaForm, "Predmet nije dodan.");
			}
		}
	}
	
	public synchronized void postaviStudijskePrograme(JComboBox<String> studijskiProgramiCB, short ciklus) {
		studijskiProgramiCB.removeAllItems();
		ArrayList<String> studijskiProgramiList = new ArrayList<>();
		MySQLDAOFactory factory = new MySQLDAOFactory();
		PredmetDAO predmetDAO = factory.getPredmetDAO();
		ArrayList<PredmetDTO> predmeti = predmetDAO.getAllPredmet();
		for(int i = 0; i < predmeti.size(); i++) {
			if(predmeti.get(i).getCiklus() == ciklus && !studijskiProgramiList.contains(predmeti.get(i).getNazivSP())) {
				studijskiProgramiList.add(predmeti.get(i).getNazivSP());
			}
		}
		for(int i = 0; i < studijskiProgramiList.size(); i++) {
			studijskiProgramiCB.addItem(studijskiProgramiList.get(i));
		}
		administratorDodavanjePredmetaForm.setStudijskiProgramiList(studijskiProgramiList);
	}
	
	public void dodajSP() {
		if(dodajSPOpened)
			return;
		
		dodajSPOpened = true;
		AdministratorDodavanjeStudijskogProgramaForm ad = new AdministratorDodavanjeStudijskogProgramaForm(administratorDodavanjePredmetaForm);
		ad.setVisible(true);
	}
	
	public static void resetDodajSPOpened() {
		dodajSPOpened = false;
	}

}
