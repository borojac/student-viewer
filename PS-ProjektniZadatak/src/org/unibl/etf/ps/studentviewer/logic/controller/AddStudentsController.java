/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.AddForm;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class AddStudentsController {
	ArrayList<String> params = new ArrayList<String>();
	MainFormController mainFormController = null;
	AddForm form = null;
	public AddStudentsController(MainFormController mainFormController, ArrayList<String> params, AddForm form ) {
		this.mainFormController = mainFormController;
		this.form = form;
		for (String ob : params) {
			this.params.add(ob.trim());
		}
		addOneStudent();
	}
	
	private void addOneStudent() {
		AddChangeStudentsHelpController help = new AddChangeStudentsHelpController();
		int valid = help.checkParams(params);
		if(valid == 0) {
			StudentMainTableDTO student = new StudentMainTableDTO(params.get(2), params.get(0), params.get(1));
			//TODO poziv metode koja cuva novog studenta u bazi
			mainFormController.getMainTable().addStudent(student);
			//TODO poziv metode koja azurira tabelu
			final String message = "Uspjesno cuvanje!";
			JOptionPane.showMessageDialog(null, message,"Obavjestenje!", JOptionPane.INFORMATION_MESSAGE);
			form.dispose();
			mainFormController.resetAddFormOpened();
			mainFormController.resetChooseAddTypeFormOpened();
			}
		else if(valid == 1) {
			final String message = "Pogresan unos za ime studenta!";
			JOptionPane.showMessageDialog(null, message,"Upozorenje!", JOptionPane.WARNING_MESSAGE);
			form.setIme("");
			form.setFocusIme();
			form.setVisible(true);
		}else if(valid == 2) {
			final String message = "Pogresan unos za prezime studenta!";
			JOptionPane.showMessageDialog(null, message,"Upozorenje!", JOptionPane.WARNING_MESSAGE);
			form.setIme(params.get(0));
			form.setPrezime("");
			form.setFocusPrezime();
			form.setVisible(true);
		}else if(valid == 3) {
			final String message = "Pogresan unos za broj indeksa! "
					+ "Ocekivani format je: broj/godina";
			JOptionPane.showMessageDialog(null, message,"Upozorenje!", JOptionPane.WARNING_MESSAGE);
			form.setIme(params.get(0));
			form.setPrezime(params.get(1));
			form.setBrojIndeksa("");
			form.setFocusBrIndeksa();
			form.setVisible(true);
		}
	}

}
