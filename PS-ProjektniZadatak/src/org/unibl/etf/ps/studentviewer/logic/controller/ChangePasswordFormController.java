package org.unibl.etf.ps.studentviewer.logic.controller;

import java.security.MessageDigest;
import java.util.Base64;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.ChangePasswordForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;

public class ChangePasswordFormController {
	
	private ChangePasswordForm changePasswordForm;
	
	public ChangePasswordFormController(ChangePasswordForm changePasswordForm) {
		this.changePasswordForm = changePasswordForm;
	}
	
	public void promjena() {
		String staraLozinka = changePasswordForm.getStaraLozinka();
		String novaLozinka = changePasswordForm.getNovaLozinka();
		String staraLozinkaHash = sha256(staraLozinka);
		String novaLozinkaHash = sha256(novaLozinka);
		
		NalogDTO nalogDTO = changePasswordForm.getNalogDTO();
		
		if("".equals(staraLozinka) || "".equals(novaLozinka)) {
			JOptionPane.showMessageDialog(changePasswordForm, "Morate popuniti oba polja.");
		} else if(!staraLozinkaHash.equals(nalogDTO.getLozinka())) {
			JOptionPane.showMessageDialog(changePasswordForm, "Nekorektan unos trenutne lozinke.");
		} else if(staraLozinka.equals(novaLozinka)) {
			JOptionPane.showMessageDialog(changePasswordForm, "Nova lozinka mora da se razlikuje od stare.");
		} else if(novaLozinka.length() < 8) {
			JOptionPane.showMessageDialog(changePasswordForm, "Nova lozinka mora imati bar 8 karaktera.");
		} else {
			MySQLDAOFactory nalogFactory = new MySQLDAOFactory();
			NalogDAO nalogDAO = nalogFactory.getNalogDAO();
			nalogDTO.setLozinka(novaLozinkaHash);
			
			if(nalogDAO.updateNalog(nalogDTO)) {
				JOptionPane.showMessageDialog(changePasswordForm, "Lozinka uspjesno promjenjena.");
				changePasswordForm.setVisible(false);
				AccountFormController.resetChangePasswordFormOpened();
			} else {
				JOptionPane.showMessageDialog(changePasswordForm, "Lozinka nije promjenjena.");
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

}
