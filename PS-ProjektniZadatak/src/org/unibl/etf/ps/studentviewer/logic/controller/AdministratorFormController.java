package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.AdministratorDodavanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.gui.view.AdministratorDodavanjeStudijskogProgramaForm;
import org.unibl.etf.ps.studentviewer.gui.view.AdministratorForm;
import org.unibl.etf.ps.studentviewer.gui.view.LoginForm;
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
	private static boolean predmetChooseAddTypeFormOpened = false;
	private static boolean addFormOpened = false;
	private static boolean dodajStudProgOpened = false;
	
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
	
	public void dodajStudProg()
	{
		if(dodajStudProgOpened)
		return;
		
		dodajStudProgOpened = true;
		AdministratorDodavanjeStudijskogProgramaForm ad = new AdministratorDodavanjeStudijskogProgramaForm(this);
		ad.setVisible(true);
	}
	
	public void createAddPredmetForm() {
		if (addFormOpened)
			return;

		addFormOpened = true;

		AdministratorDodavanjePredmetaForm af = new AdministratorDodavanjePredmetaForm(this);
		af.setVisible(true);
	}
	
	public static void resetChooseAddTypeFormOpened() {
		predmetChooseAddTypeFormOpened = false;
	}
	
	public static void resetDodajStudProgOpened() {
		dodajStudProgOpened = false;
	}
	
	public static void resetAddFormOpened() {
		addFormOpened = false;
	}
	
	public void odbijZahtjev() {
		ZahtjevDTO zahtjevDTO = administratorForm.getSelectedZahtjev();
		
		MySQLDAOFactory zahtjevFactory = new MySQLDAOFactory();
		ZahtjevDAO zahtjevDAO = zahtjevFactory.getZahtjevDAO();
		
		if(zahtjevDAO.deleteZahtjev(zahtjevDTO)) {
			JOptionPane.showMessageDialog(administratorForm, "Zahtjev odbijen.");
			administratorForm.removeSelectedRow();
		}
	}
	
	public void odobriZahtjev() {
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
	
	public void odjava() {
		LoginFormController.resetAdminFormOpened();
		LoginFormController.resetMainFormOpened();
		administratorForm.dispose();
		LoginForm loginForm = new LoginForm();
		loginForm.setVisible(true);
	}

}
