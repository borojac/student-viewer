package org.unibl.etf.ps.studentviewer.logic.controller;

import org.unibl.etf.ps.studentviewer.gui.view.AccountForm;
import org.unibl.etf.ps.studentviewer.gui.view.BrisanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.gui.view.ChangeAccountNameForm;
import org.unibl.etf.ps.studentviewer.gui.view.ChangePasswordForm;
import org.unibl.etf.ps.studentviewer.gui.view.DodavanjePredmetaForm;

public class AccountFormController {
	
	private AccountForm accountForm;
	private static boolean dodavanjePredmetaFormOpened = false;
	private static boolean brisanjePredmetaFormOpened = false;
	private static boolean changeAccountNameFormOpened = false;
	private static boolean changePasswordFormOpened = false;
	
	public AccountFormController() {
		super();
	}
	
	public AccountFormController(AccountForm accountForm) {
		this.accountForm = accountForm;
	}
	
	public synchronized boolean createChangePasswordForm() {
		if(changePasswordFormOpened)
			return false;
		changePasswordFormOpened = true;
		ChangePasswordForm changePasswordForm = new ChangePasswordForm(accountForm.getNalogDTO());
		changePasswordForm.setVisible(true);
		return true;
	}
	
	public synchronized boolean createChangeAccountNameForm() {
		if(changeAccountNameFormOpened)
			return false;
		changeAccountNameFormOpened = true;
		ChangeAccountNameForm changeAccountNameForm = new ChangeAccountNameForm(accountForm.getNalogDTO());
		changeAccountNameForm.setVisible(true);
		return true;
	}
	
	public synchronized boolean createDodavanjePredmetaForm() {
		if(dodavanjePredmetaFormOpened)
			return false;
		
		dodavanjePredmetaFormOpened = true;
		DodavanjePredmetaForm dodavanjePredmetaForm = new DodavanjePredmetaForm(accountForm.getNalogDTO());
		dodavanjePredmetaForm.setVisible(true);
		return true;
	}
	
	public synchronized boolean createBrisanjePredmetaForm() {
		if(brisanjePredmetaFormOpened)
			return false;
		
		brisanjePredmetaFormOpened = true;
		BrisanjePredmetaForm brisanjePredmetaForm = new BrisanjePredmetaForm(accountForm.getNalogDTO(), accountForm.getMainFormController().getMainForm());
		brisanjePredmetaForm.setVisible(true);
		return true;
	}
	
	public synchronized boolean odjava() {
		return true;
	}
	
	public static void resetDodavanjePredmetaFormOpened(){
		dodavanjePredmetaFormOpened = false;
	}
	
	public static void resetBrisanjePredmetaFormOpened(){
		brisanjePredmetaFormOpened = false;
	}
	
	public static void resetChangeAccountNameFormOpened(){
		changeAccountNameFormOpened = false;
	}
	
	public static void resetChangePasswordFormOpened(){
		changePasswordFormOpened = false;
	}

}
