package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.BrisanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class BrisanjePredmetaFormController {
	
	private BrisanjePredmetaForm brisanjePredmetaForm;
	
	public BrisanjePredmetaFormController(BrisanjePredmetaForm brisanjePredmetaForm) {
		this.brisanjePredmetaForm = brisanjePredmetaForm;
	}
	
	public void ukloni() {
		NalogDTO nalogDTO = brisanjePredmetaForm.getNalogDTO();
		PredmetDTO predmetDTO = brisanjePredmetaForm.getSelectedPredmet();
		
		if(predmetDTO == null) {
			
		} else {
			MySQLDAOFactory nalogFactory = new MySQLDAOFactory();
			NalogDAO nalogDAO = nalogFactory.getNalogDAO();
		
			if(nalogDAO.removePredmet(predmetDTO, nalogDTO)) {
				JOptionPane.showMessageDialog(brisanjePredmetaForm, "Predmet uspjesno uklonjen.");
				brisanjePredmetaForm.getMainForm().resetPredmetiComboBox();
				brisanjePredmetaForm.dispose();
				AccountFormController.resetBrisanjePredmetaFormOpened();
			} else {
				JOptionPane.showMessageDialog(brisanjePredmetaForm, "Predmet nije uspjesno uklnonjen.");
			}
		}
	}

}
