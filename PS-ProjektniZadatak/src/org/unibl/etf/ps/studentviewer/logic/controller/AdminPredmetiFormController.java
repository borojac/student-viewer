package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.AdminPredmetiForm;
import org.unibl.etf.ps.studentviewer.gui.view.AdministratorDodavanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.gui.view.AdministratorIzmjenaPredmetaForm;
import org.unibl.etf.ps.studentviewer.gui.view.PredmetChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dao.ZahtjevDAO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class AdminPredmetiFormController {
	
	private AdminPredmetiForm adminPredmetiForm;
	private static boolean predmetChooseAddTypeFormOpened = false;
	private static boolean addFormOpened = false;
	
	public AdminPredmetiFormController(AdminPredmetiForm adminPredmetiForm) {
		this.adminPredmetiForm = adminPredmetiForm;
	}
	
	public void createPredmetChooseAddTypeForm() {
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

		AdministratorDodavanjePredmetaForm af = new AdministratorDodavanjePredmetaForm(adminPredmetiForm);
		af.setVisible(true);
	}
	
	public static void resetChooseAddTypeFormOpened() {
		predmetChooseAddTypeFormOpened = false;
	}
	
	public static void resetAddFormOpened() {
		addFormOpened = false;
	}
	
	public AdminPredmetiForm getAdminPredmetiForm() {
		return adminPredmetiForm;
	}
	
	public void izmjeniPredmet() {
		AdministratorIzmjenaPredmetaForm administratorIzmjenaPredmetaForm = new AdministratorIzmjenaPredmetaForm(adminPredmetiForm);
		administratorIzmjenaPredmetaForm.setVisible(true);
	}
	
	public void obrisiPredmet() {
		int answer = JOptionPane.showOptionDialog(adminPredmetiForm, "Da li ste sigurni da zelite da obrisete odabrani predmet?", "Brisanje predmeta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Da", "Ne" }, "default");
		if(answer == JOptionPane.YES_OPTION) {
			PredmetDTO predmetDTO = adminPredmetiForm.getSelectedPredmet();
		
			MySQLDAOFactory factory = new MySQLDAOFactory();
			PredmetDAO predmetDAO = factory.getPredmetDAO();
			NalogDAO nalogDAO = factory.getNalogDAO();
			ZahtjevDAO zahtjevDAO = factory.getZahtjevDAO();
			
			if(nalogDAO.removePredmete(predmetDTO) && zahtjevDAO.deleteZahtjeve(predmetDTO) && predmetDAO.deletePredmet(predmetDTO)) {
				JOptionPane.showMessageDialog(adminPredmetiForm, "Predmet uspjesno obrisan.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
				adminPredmetiForm.removeSelectedRow();
			}
			
			adminPredmetiForm.getPredmetiTbl().getSelectionModel().setSelectionInterval(-1, -1);
			adminPredmetiForm.getIzmjeniBtn().setEnabled(false);
			adminPredmetiForm.getObrisiBtn().setEnabled(false);
		}
	}

}
