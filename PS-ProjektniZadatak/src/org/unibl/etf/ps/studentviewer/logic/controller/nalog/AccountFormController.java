package org.unibl.etf.ps.studentviewer.logic.controller.nalog;

import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.BrisanjeDisciplineForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.DodavanjeDisciplineForm;
import org.unibl.etf.ps.studentviewer.gui.view.nalog.AccountForm;
import org.unibl.etf.ps.studentviewer.gui.view.nalog.ChangeAccountNameForm;
import org.unibl.etf.ps.studentviewer.gui.view.nalog.ChangePasswordForm;
import org.unibl.etf.ps.studentviewer.gui.view.nalog.LoginForm;
import org.unibl.etf.ps.studentviewer.gui.view.predmet.BrisanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.gui.view.predmet.DodavanjePredmetaForm;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;

public class AccountFormController {
	
	private AccountForm accountForm;
	private static boolean dodavanjePredmetaFormOpened = false;
	private static boolean brisanjePredmetaFormOpened = false;
	private static boolean changeAccountNameFormOpened = false;
	private static boolean changePasswordFormOpened = false;
	private static boolean dodavanjeDisciplineFormOpened = false;
	private static boolean brisanjeDisciplineFormOpened = false;

	
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
		LoginFormController.resetMainFormOpened();
		LoginFormController.resetAdminFormOpened();
		MainFormController.resetAccountFormOpened();
		accountForm.getMainFormController().getMainForm().dispose();
		accountForm.dispose();
		LoginForm loginForm = new LoginForm();
		loginForm.setVisible(true);
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

	public synchronized boolean createDodavanjeDisciplineForm() {
		if(dodavanjeDisciplineFormOpened)
			return false;
		
		dodavanjeDisciplineFormOpened = true;
		DodavanjeDisciplineForm dodavanjeDisciplineForm = new DodavanjeDisciplineForm(accountForm.getNalogDTO());
		dodavanjeDisciplineForm.setVisible(true);
		return true;
		
	}

	public static void resetDodavanjeDisciplineFormOpened() {
		dodavanjeDisciplineFormOpened = false;
		
	}

	public  synchronized boolean createBrisanjeDisciplineForm() {
		if(brisanjeDisciplineFormOpened)
			return false;
		
		brisanjeDisciplineFormOpened = true;
		BrisanjeDisciplineForm brisanjeeDisciplineForm = new BrisanjeDisciplineForm (accountForm.getNalogDTO(),accountForm.getMainFormController().getMainForm());
		brisanjeeDisciplineForm.setVisible(true);
		return true;
		
	}

	public static void resetBrisanjeDisciplineFormOpened() {
		brisanjeDisciplineFormOpened = false;
		
	}

}
