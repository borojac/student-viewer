package org.unibl.etf.ps.studentviewer.logic.controller;

import org.unibl.etf.ps.studentviewer.gui.view.KreirajNalogForm;
import org.unibl.etf.ps.studentviewer.gui.view.LoginForm;

public class LoginFormController {
	
	private LoginForm loginForm;
	
	public LoginFormController(LoginForm loginForm) {
		this.loginForm = loginForm;
	}
	
	public void createKreirajNalogForm() {
		KreirajNalogForm kreirajNalogForm = new KreirajNalogForm();
		kreirajNalogForm.setVisible(true);
		loginForm.setVisible(false);
	}

}
