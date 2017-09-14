package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.ChangeAccountNameForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;

public class ChangeAccountNameFormController {
	
	private ChangeAccountNameForm changeAccountNameForm;
	
	public ChangeAccountNameFormController(ChangeAccountNameForm changeAccountNameForm) {
		this.changeAccountNameForm = changeAccountNameForm;
	}
	
	public void promjenaKorisnickogImena() {
		String staroKorIme = changeAccountNameForm.getStaroKorIme();
		String novoKorIme = changeAccountNameForm.getNovoKorIme();
		
		NalogDTO nalogDTO = changeAccountNameForm.getNalogDTO();
		
		if("".equals(staroKorIme) || "".equals(novoKorIme)) {
			JOptionPane.showMessageDialog(changeAccountNameForm, "Oba polja moraju biti popunjena.");
		} else if(!staroKorIme.equals(nalogDTO.getKorisnickoIme())) {
			JOptionPane.showMessageDialog(changeAccountNameForm, "Nekorektan unos starog korisnickog imena");
		} else if(staroKorIme.equals(novoKorIme)) {
			JOptionPane.showMessageDialog(changeAccountNameForm, "Novo korisnicko ime se mora razlikovati od starog.");
		} else {
			MySQLDAOFactory nalogFactory = new MySQLDAOFactory();
			NalogDAO nalogDAO = nalogFactory.getNalogDAO();
			nalogDTO.setKorisnickoIme(novoKorIme);
			
			if(nalogDAO.updateNalog(nalogDTO)) {
				JOptionPane.showMessageDialog(changeAccountNameForm, "Korisnicko ime uspjesno promjenjeno.");
				changeAccountNameForm.dispose();
				AccountFormController.resetChangeAccountNameFormOpened();
			} else {
				JOptionPane.showMessageDialog(changeAccountNameForm, "Korisnicko ime nije promjenjeno");
			}
		}
	}

}
