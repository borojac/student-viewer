package org.unibl.etf.ps.studentviewer.gui.controler;

import org.unibl.etf.ps.studentviewer.gui.view.AccountForm;
import org.unibl.etf.ps.studentviewer.gui.view.DodavanjePredmetaForm;

public class AccountFormController {
	
	private AccountForm accountForm;
	private static boolean dodavanjePredmetaFormOpened = false;
	private static boolean brisanjePredmetaFormOpened = false;
	
	public AccountFormController() {
		super();
	}
	
	public AccountFormController(AccountForm accountForm) {
		this.accountForm = accountForm;
	}
	
	public synchronized boolean createPromjenaLozinkeForm() {
		return true;
	}
	
	public synchronized boolean createPromjenaKorImenaForm() {
		return true;
	}
	
	public synchronized boolean createDodavanjePredmetaForm() {
		if(dodavanjePredmetaFormOpened)
			return false;
		
		dodavanjePredmetaFormOpened = true;
		DodavanjePredmetaForm dodavanjePredmetaForm = new DodavanjePredmetaForm();
		dodavanjePredmetaForm.setVisible(true);
		return true;
	}
	
	public synchronized boolean createBrisanjePredmetaForm() {
		if(brisanjePredmetaFormOpened)
			return false;
		
		brisanjePredmetaFormOpened = true;
		BrisanjePredmetaForm brisanjePredmetaForm = new BrisanjePredmetaForm();
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

}
