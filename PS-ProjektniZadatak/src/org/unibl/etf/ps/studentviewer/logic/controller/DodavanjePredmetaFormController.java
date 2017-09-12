package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.DodavanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.ZahtjevDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDTO;

public class DodavanjePredmetaFormController {
	
	private DodavanjePredmetaForm dodavanjePredmetaForm;
	
	public DodavanjePredmetaFormController(DodavanjePredmetaForm dodavanjePredmetaForm) {
		this.dodavanjePredmetaForm = dodavanjePredmetaForm;
	}
	
	public void posaljiZahtjev() {
		PredmetDTO predmetDTO = dodavanjePredmetaForm.getSelectedPredmet();
		
		if(predmetDTO == null) {
			
		} else {
			NalogDTO nalogDTO = dodavanjePredmetaForm.getNalogDTO();
		
			ZahtjevDTO zahtjevDTO = new ZahtjevDTO(predmetDTO.getPredmetId(), nalogDTO.getNalogId());
		
			MySQLDAOFactory zahtjevFactory = new MySQLDAOFactory();
			ZahtjevDAO zahtjevDAO = zahtjevFactory.getZahtjevDAO();
		
			if(zahtjevDAO.addZahtjev(zahtjevDTO)) {
				JOptionPane.showMessageDialog(dodavanjePredmetaForm, "Zahtjev je uspjesno poslan.");
				dodavanjePredmetaForm.dispose();
				AccountFormController.resetDodavanjePredmetaFormOpened();
			} else {
				JOptionPane.showMessageDialog(dodavanjePredmetaForm, "Zahtjev nije uspjesno poslan.");
			}
		}
	}

}
