package org.unibl.etf.ps.studentviewer.logic.controller.nalog;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.nalog.ChangeAccountNameForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.NalogDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;

public class ChangeAccountNameFormController {
	
	private ChangeAccountNameForm changeAccountNameForm;
	
	public ChangeAccountNameFormController(ChangeAccountNameForm changeAccountNameForm) {
		this.changeAccountNameForm = changeAccountNameForm;
	}
	
	public void promjenaKorisnickogImena() {
		String korisnickoIme = changeAccountNameForm.getKorisnickoIme();
		
		NalogDTO nalogDTO = changeAccountNameForm.getNalogDTO();
		
		if("".equals(korisnickoIme)) {
			JOptionPane.showMessageDialog(changeAccountNameForm, "Morate unijeti novo korisnicko ime.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		} else if(korisnickoIme.length() < 4) {
			JOptionPane.showMessageDialog(changeAccountNameForm, "Korisnicko ime mora sadrzati bar 4 karaktera.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
		} else if(korisnickoIme.equals(nalogDTO.getKorisnickoIme())) {
			JOptionPane.showMessageDialog(changeAccountNameForm, "Korisnicko ime nije promjenjeno.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
			changeAccountNameForm.dispose();
			AccountFormController.resetChangeAccountNameFormOpened();
		} else {
			MySQLDAOFactory nalogFactory = new MySQLDAOFactory();
			NalogDAO nalogDAO = nalogFactory.getNalogDAO();
			nalogDTO.setKorisnickoIme(korisnickoIme);
			
			if(nalogDAO.updateNalog(nalogDTO)) {
				JOptionPane.showMessageDialog(changeAccountNameForm, "Korisnicko ime uspjesno promjenjeno.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
				changeAccountNameForm.dispose();
				AccountFormController.resetChangeAccountNameFormOpened();
			} else {
				JOptionPane.showMessageDialog(changeAccountNameForm, "Korisnicko ime nije promjenjeno.", "Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

}
