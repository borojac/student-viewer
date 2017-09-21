package org.unibl.etf.ps.studentviewer.logic.controller;

import org.unibl.etf.ps.studentviewer.gui.view.AdminPredmetiForm;
import org.unibl.etf.ps.studentviewer.gui.view.AdministratorDodavanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.gui.view.PredmetChooseAddTypeForm;

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

		AdministratorDodavanjePredmetaForm af = new AdministratorDodavanjePredmetaForm();
		af.setVisible(true);
	}
	
	public static void resetChooseAddTypeFormOpened() {
		predmetChooseAddTypeFormOpened = false;
	}
	
	public static void resetAddFormOpened() {
		addFormOpened = false;
	}

}
