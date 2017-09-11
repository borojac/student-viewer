package org.unibl.etf.ps.studentviewer.logic.controller;


import java.io.IOException;
import java.security.MessageDigest;
import java.util.Base64;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.KreirajNalogForm;
import org.unibl.etf.ps.studentviewer.gui.view.MainForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;


public class KreirajNalogFormController {
	
	
	
	public KreirajNalogFormController(KreirajNalogForm kreirajNalogForm)
	{
		this.kreirajNalogForm = kreirajNalogForm;
	}
	
	MySQLDAOFactory nalogFactory = new MySQLDAOFactory();
	NalogDAO nalogDAO = nalogFactory.getNalogDAO();
	private KreirajNalogForm kreirajNalogForm;
	JOptionPane btn1;
	private String sLozinka,sLozinkaPot,sKorIme,sIme,sPrezime,sLozinkaHash;
	
	public synchronized boolean createKreirajNalog() {
		
		sLozinka = kreirajNalogForm.getLozinkaTf();
		sLozinkaPot = kreirajNalogForm.getLozinkaPotTf();
		sKorIme = kreirajNalogForm.getKorImeTf();
		sIme = kreirajNalogForm.getImeTf();
		sPrezime = kreirajNalogForm.getPrezimeTf();
		
		if (sIme.equals(""))
		{
			JOptionPane.showMessageDialog(btn1, "Popunite ime!");
		}
		else if (sPrezime.equals(""))
		{
			JOptionPane.showMessageDialog(btn1, "Popunite prezime!");
		}
		else if (sKorIme.equals(""))
		{
			JOptionPane.showMessageDialog(btn1, "Popunite korisnicko ime!");
		}
		else if (sKorIme.length() < 4)
		{
			JOptionPane.showMessageDialog(btn1, "Korisnicko ime sadrzi manje od 4 karaktera!");
		}
		else if (nalogDAO.checkNalog(sKorIme))
		{
			JOptionPane.showMessageDialog(btn1, "Korisnicko ime vec postoji!");
		}
		else if (sLozinka.equals(""))
		{
			JOptionPane.showMessageDialog(btn1, "Popunite lozinku!");
		}
		else if (sLozinka.length() < 8)
		{
			JOptionPane.showMessageDialog(btn1, "Lozinka sadrzi manje od 8 karaktera!");
		}
		else if (sLozinkaPot.equals(""))
		{
			JOptionPane.showMessageDialog(btn1, "Popunite lozinku za potvrdu!");
		}
		else if (!sLozinkaPot.equals(sLozinka))
		{
			JOptionPane.showMessageDialog(btn1, "Lozinke se ne podudaraju!");
		}
		else
		{
			sLozinkaHash = sha256(sLozinka);
			NalogDTO nalogDTO = new NalogDTO(sIme, sPrezime, sKorIme, sLozinkaHash, 'K');
			if(nalogDAO.addNalog(nalogDTO)) {
				JOptionPane.showMessageDialog(btn1, "Nalog ispravno kreiran!");
				try {
					
					MainForm mainForm = new MainForm(nalogDAO.getNalog(nalogDTO.getKorisnickoIme(), nalogDTO.getLozinka()));
					mainForm.setVisible(true);
					kreirajNalogForm.setVisible(false);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
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

