package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.view.AddForm;
import org.unibl.etf.ps.studentviewer.gui.view.AdministratorDodavanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.gui.view.AdministratorForm;
import org.unibl.etf.ps.studentviewer.gui.view.ChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.gui.view.PredmetChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ZahtjevDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDTO;

public class AdministratorFormController {
	
	private AdministratorForm administratorForm;
	private boolean predmetChooseAddTypeFormOpened = false;
	private boolean addFormOpened = false;
	
	public AdministratorFormController(AdministratorForm administratorForm) {
		this.administratorForm = administratorForm;
	}
	
	public void createPredmetChooseAddTypeForm(){
		if (predmetChooseAddTypeFormOpened)
			return;

		predmetChooseAddTypeFormOpened = true;

		PredmetChooseAddTypeForm catf = new PredmetChooseAddTypeForm(this);
		catf.setVisible(true);
	}
	
	public void createAddPredmetForm() {
		if (addFormOpened)
			return;

		addFormOpened = true;

		AdministratorDodavanjePredmetaForm af = new AdministratorDodavanjePredmetaForm(this);
		af.setVisible(true);
	}
	
	public MainTable getMainTable() {
		//return administratorForm.getMainTable();
		return null;
	}
	
	public void resetChooseAddTypeFormOpened() {
		predmetChooseAddTypeFormOpened = false;
	}
	
	public void resetAddFormOpened(){
		addFormOpened = false;
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
