package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.AdministratorForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ZahtjevDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDTO;

public class AdministratorFormController {
	
	private AdministratorForm administratorForm;
	
	public AdministratorFormController(AdministratorForm administratorForm) {
		this.administratorForm = administratorForm;
	}
	
	public void odbij() {
		ZahtjevDTO zahtjevDTO = administratorForm.getSelectedZahtjev();
		
		MySQLDAOFactory zahtjevFactory = new MySQLDAOFactory();
		ZahtjevDAO zahtjevDAO = zahtjevFactory.getZahtjevDAO();
		
		if(zahtjevDAO.deleteZahtjev(zahtjevDTO)) {
			JOptionPane.showMessageDialog(administratorForm, "Zahtjev odbijen.");
			administratorForm.removeSelectedRow();
		}
	}
	
	public void odobri() {
		ZahtjevDTO zahtjevDTO = administratorForm.getSelectedZahtjev();
		
		MySQLDAOFactory factory = new MySQLDAOFactory();
		ZahtjevDAO zahtjevDAO = factory.getZahtjevDAO();
		NalogDAO nalogDAO = factory.getNalogDAO();
		PredmetDAO predmetDAO = factory.getPredmetDAO();
		
		NalogDTO nalogDTO = nalogDAO.getNalog(zahtjevDTO.getNalogId());
		PredmetDTO predmetDTO = predmetDAO.getPredmet(zahtjevDTO.getPredmetId());
		
		if(zahtjevDAO.updateZahtjev(zahtjevDTO) && nalogDAO.addPredmet(predmetDTO, nalogDTO)) {
			JOptionPane.showMessageDialog(administratorForm, "Zahtjev odobren.");
			administratorForm.removeSelectedRow();
		}
	}

}
