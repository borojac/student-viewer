package org.unibl.etf.ps.studentviewer.logic.controller.predmet;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.predmet.AdministratorDodavanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.gui.view.predmet.AdministratorDodavanjeStudijskogProgramaForm;
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
			JOptionPane.showMessageDialog(administratorDodavanjeStudijskogProgramaForm, "Niste popunili sva polja.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		} else if(ects == 0 || trajanje == 0) {
			
		} else {
			MySQLDAOFactory factory = new MySQLDAOFactory();
			PredmetDAO predmetDAO = factory.getPredmetDAO();
			
			if(predmetDAO.checkStudijskiProgram(nazivSP, ciklus)) {
				JOptionPane.showMessageDialog(administratorDodavanjeStudijskogProgramaForm, "Na trazenom ciklusu vec postoji studijski program sa unesenim imenom", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			} else {
				if(predmetDAO.addStudijskiProgram(nazivSP, ects, ciklus, trajanje, zvanje)) {
					JOptionPane.showMessageDialog(administratorDodavanjeStudijskogProgramaForm, "Studijski program uspjesno dodan.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
					AdministratorDodavanjePredmetaForm adpf = administratorDodavanjeStudijskogProgramaForm.getAdministratorDodavanjePredmetaForm();
					adpf.getAdministratorDodavanjePredmetaFormController().postaviStudijskePrograme(adpf.getStudijskiProgramiCB(), adpf.getCiklus());
					administratorDodavanjeStudijskogProgramaForm.dispose();
					AdministratorDodavanjePredmetaFormController.resetDodajSPOpened();
				} else {
					JOptionPane.showMessageDialog(administratorDodavanjeStudijskogProgramaForm, "Studijski program nije dodan.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

}
