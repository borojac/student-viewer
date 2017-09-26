package org.unibl.etf.ps.studentviewer.logic.controller.nalog;


import java.io.IOException;
import java.security.MessageDigest;
import java.util.Base64;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.MainForm;
import org.unibl.etf.ps.studentviewer.gui.view.nalog.KreirajNalogForm;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;


public class KreirajNalogFormController {
	
	private NalogDAO nalogDAO;
	private KreirajNalogForm kreirajNalogForm;
	
	public KreirajNalogFormController(KreirajNalogForm kreirajNalogForm) {
		this.kreirajNalogForm = kreirajNalogForm;
		MySQLDAOFactory nalogFactory = new MySQLDAOFactory();
		nalogDAO = nalogFactory.getNalogDAO();
	}
	
	public void createKreirajNalog() {		
		String sLozinka = kreirajNalogForm.getLozinkaTf();
		String sLozinkaPot = kreirajNalogForm.getLozinkaPotTf();
		String sKorIme = kreirajNalogForm.getKorImeTf();
		String sIme = kreirajNalogForm.getImeTf();
		String sPrezime = kreirajNalogForm.getPrezimeTf();
		
		if (sIme.equals("")) {
			JOptionPane.showMessageDialog(kreirajNalogForm, "Popunite ime!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		}
		else if (sPrezime.equals("")) {
			JOptionPane.showMessageDialog(kreirajNalogForm, "Popunite prezime!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		}
		else if (sKorIme.equals("")) {
			JOptionPane.showMessageDialog(kreirajNalogForm, "Popunite korisnicko ime!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		}
		else if (sKorIme.length() < 4) {
			JOptionPane.showMessageDialog(kreirajNalogForm, "Korisnicko ime sadrzi manje od 4 karaktera!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		}
		else if (nalogDAO.checkNalog(sKorIme)) {
			JOptionPane.showMessageDialog(kreirajNalogForm, "Korisnicko ime vec postoji!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		}
		else if (sLozinka.equals("")) {
			JOptionPane.showMessageDialog(kreirajNalogForm, "Popunite lozinku!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		}
		else if (sLozinka.length() < 8) {
			JOptionPane.showMessageDialog(kreirajNalogForm, "Lozinka sadrzi manje od 8 karaktera!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		}
		else if (sLozinkaPot.equals("")) {
			JOptionPane.showMessageDialog(kreirajNalogForm, "Popunite lozinku za potvrdu!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		}
		else if (!sLozinkaPot.equals(sLozinka)) {
			JOptionPane.showMessageDialog(kreirajNalogForm, "Lozinke se ne podudaraju!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		}
		else {
			String sLozinkaHash = sha256(sLozinka);
			NalogDTO nalogDTO = new NalogDTO(sIme, sPrezime, sKorIme, sLozinkaHash, 'K');
			if(nalogDAO.addNalog(nalogDTO)) {
				JOptionPane.showMessageDialog(kreirajNalogForm, "Nalog ispravno kreiran!", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
				try {
					
					MainForm mainForm = new MainForm(nalogDAO.getNalog(nalogDTO.getKorisnickoIme(), nalogDTO.getLozinka()));
					mainForm.setVisible(true);
					kreirajNalogForm.setVisible(false);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private String sha256(String lozinka){
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(lozinka.getBytes("UTF-8"));
			Base64.Encoder encoder = Base64.getEncoder();
			return encoder.encodeToString(hash);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

