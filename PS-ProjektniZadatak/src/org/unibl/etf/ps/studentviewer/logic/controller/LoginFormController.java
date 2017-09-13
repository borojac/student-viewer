package org.unibl.etf.ps.studentviewer.logic.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Base64;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.AdministratorForm;
import org.unibl.etf.ps.studentviewer.gui.view.KreirajNalogForm;
import org.unibl.etf.ps.studentviewer.gui.view.LoginForm;
import org.unibl.etf.ps.studentviewer.gui.view.MainForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;

public class LoginFormController {
	
	private LoginForm loginForm;
	private static boolean mainFormOpened = false;
	private static boolean adminFormOpened = false;
	private static boolean kreirajNalogFormOpened = false;
	
	public LoginFormController(LoginForm loginForm) {
		this.loginForm = loginForm;
	}
	
	public void createKreirajNalogForm() {
		if(kreirajNalogFormOpened) {
			return;
		}
		
		kreirajNalogFormOpened = true;
		
		KreirajNalogForm kreirajNalogForm = new KreirajNalogForm();
		kreirajNalogForm.setVisible(true);
		loginForm.setVisible(false);
	}
	
	public void prijava() throws IOException {
		if(mainFormOpened || adminFormOpened) {
			return;
		}
		
		mainFormOpened = true;
		adminFormOpened = true;
		
		String korisnickoIme = loginForm.getKorisnickoIme();
		String lozinkaHash = sha256(loginForm.getLozinka());
		
		if("".equals(korisnickoIme) || "".equals(loginForm.getLozinka())) {
			JOptionPane.showMessageDialog(loginForm, "Niste unijeli korisnicko ime ili lozinku.");
			resetAdminFormOpened();
			resetMainFormOpened();
		} else {
			MySQLDAOFactory nalogFactory = new MySQLDAOFactory();
			NalogDAO nalogDAO = nalogFactory.getNalogDAO();
			NalogDTO nalogDTO = nalogDAO.getNalog(korisnickoIme, lozinkaHash);
			if(nalogDTO == null) {
				JOptionPane.showMessageDialog(loginForm, "Korisnicko ime ili lozinka nisu korektno uneseni.");
				resetAdminFormOpened();
				resetMainFormOpened();
			} else {
				if(nalogDTO.getTipNaloga() == 'K') {
					MainForm mainForm = new MainForm(nalogDTO);
					mainForm.setVisible(true);
					loginForm.dispose();
				} else {
					AdministratorForm adminForm = new AdministratorForm();
					adminForm.setNalogDTO(nalogDTO);
					adminForm.setVisible(true);
					loginForm.dispose();
				}
				
			}
		}
	}
	
	private String sha256(String lozinka) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(lozinka.getBytes("UTF-8"));
			Base64.Encoder encoder = Base64.getEncoder();
			return encoder.encodeToString(hash);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void resetMainFormOpened() {
		mainFormOpened = false;
	}
	
	public static void resetAdminFormOpened() {
		adminFormOpened = false;
	}

}
