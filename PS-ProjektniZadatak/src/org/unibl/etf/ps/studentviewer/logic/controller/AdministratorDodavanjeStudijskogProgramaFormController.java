package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.AdministratorDodavanjeStudijskogProgramaForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;

public class AdministratorDodavanjeStudijskogProgramaFormController {
	
	private AdministratorDodavanjeStudijskogProgramaForm administratorDodavanjeStudijskogProgramaForm;
	
	public AdministratorDodavanjeStudijskogProgramaFormController(AdministratorDodavanjeStudijskogProgramaForm administratorDodavanjeStudijskogProgramaForm) {
		this.administratorDodavanjeStudijskogProgramaForm = administratorDodavanjeStudijskogProgramaForm;
	}
	
	public void dodajStudProg() {
		String nazivSP = administratorDodavanjeStudijskogProgramaForm.getNazivSP();
		int ects = administratorDodavanjeStudijskogProgramaForm.getEcts();
		short ciklus = administratorDodavanjeStudijskogProgramaForm.getCiklus();
		short trajanje = administratorDodavanjeStudijskogProgramaForm.getTrajanje();
		String zvanje = administratorDodavanjeStudijskogProgramaForm.getZvanje();
		
		if("".equals(nazivSP) || ects == -1 || ciklus == -1 || trajanje == -1 || "".equals(zvanje)) {
			JOptionPane.showMessageDialog(administratorDodavanjeStudijskogProgramaForm, "Niste popunili sva polja.");
		} else if(ects == 0 || trajanje == 0) {
			
		} else {
			MySQLDAOFactory factory = new MySQLDAOFactory();
			PredmetDAO predmetDAO = factory.getPredmetDAO();
			
			if(predmetDAO.checkStudijskiProgram(nazivSP, ciklus)) {
				JOptionPane.showMessageDialog(administratorDodavanjeStudijskogProgramaForm, "Na trazenom ciklusu vec postoji studijski program sa unesenim imenom");
			} else {
				if(predmetDAO.addStudijskiProgram(nazivSP, ects, ciklus, trajanje, zvanje)) {
					JOptionPane.showMessageDialog(administratorDodavanjeStudijskogProgramaForm, "Studijski program uspjesno dodan.");
					administratorDodavanjeStudijskogProgramaForm.dispose();
					AdministratorFormController.resetDodajStudProgOpened();
				} else {
					JOptionPane.showMessageDialog(administratorDodavanjeStudijskogProgramaForm, "Studijski program nije dodan.");
				}
			}
		}
	}

}
