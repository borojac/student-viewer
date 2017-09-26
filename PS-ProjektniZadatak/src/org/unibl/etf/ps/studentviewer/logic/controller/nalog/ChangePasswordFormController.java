package org.unibl.etf.ps.studentviewer.logic.controller.nalog;

import java.security.MessageDigest;
import java.util.Base64;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.nalog.ChangePasswordForm;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;

public class ChangePasswordFormController {
	
	private ChangePasswordForm changePasswordForm;
	
	public ChangePasswordFormController(ChangePasswordForm changePasswordForm) {
		this.changePasswordForm = changePasswordForm;
	}
	
	public void promjenaLozinke() {
		String staraLozinka = changePasswordForm.getStaraLozinka();
		String novaLozinka = changePasswordForm.getNovaLozinka();
		String novaLozinkaPotvrda = changePasswordForm.getNovaLozinkaPotvrda();
		String staraLozinkaHash = sha256(staraLozinka);
		String novaLozinkaHash = sha256(novaLozinka);
		
		NalogDTO nalogDTO = changePasswordForm.getNalogDTO();
		
		if("".equals(staraLozinka) || "".equals(novaLozinka) || "".equals(novaLozinkaPotvrda)) {
			JOptionPane.showMessageDialog(changePasswordForm, "Morate popuniti sva polja.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		} else if(!staraLozinkaHash.equals(nalogDTO.getLozinka())) {
			JOptionPane.showMessageDialog(changePasswordForm, "Nekorektan unos trenutne lozinke.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			changePasswordForm.setEmptyStaraLozinka();
			changePasswordForm.setEmptyNovaLozinka();
			changePasswordForm.setEmptyNovaLozinkaPotvrda();
		} else if(staraLozinka.equals(novaLozinka)) {
			JOptionPane.showMessageDialog(changePasswordForm, "Nova lozinka mora da se razlikuje od stare.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			changePasswordForm.setEmptyNovaLozinka();
			changePasswordForm.setEmptyNovaLozinkaPotvrda();
		} else if(novaLozinka.length() < 8) {
			JOptionPane.showMessageDialog(changePasswordForm, "Nova lozinka mora imati bar 8 karaktera.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			changePasswordForm.setEmptyNovaLozinka();
			changePasswordForm.setEmptyNovaLozinkaPotvrda();
		} else if(!novaLozinka.equals(novaLozinkaPotvrda)) {
			JOptionPane.showMessageDialog(changePasswordForm, "Unosi nove lozinke se ne podudaraju.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			changePasswordForm.setEmptyNovaLozinka();
			changePasswordForm.setEmptyNovaLozinkaPotvrda();
		} else {
			MySQLDAOFactory nalogFactory = new MySQLDAOFactory();
			NalogDAO nalogDAO = nalogFactory.getNalogDAO();
			nalogDTO.setLozinka(novaLozinkaHash);
			
			if(nalogDAO.updateNalog(nalogDTO)) {
				JOptionPane.showMessageDialog(changePasswordForm, "Lozinka uspjesno promjenjena.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
				changePasswordForm.dispose();;
				AccountFormController.resetChangePasswordFormOpened();
			} else {
				JOptionPane.showMessageDialog(changePasswordForm, "Lozinka nije promjenjena.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
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
