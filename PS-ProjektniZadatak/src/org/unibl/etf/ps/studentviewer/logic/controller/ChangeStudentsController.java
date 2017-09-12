package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.ChangeForm;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class ChangeStudentsController {
		ArrayList<String> params = new ArrayList<String>();
		MainFormController mainFormController = null;
		ChangeForm form = null;
		int number;
		StudentMainTableDTO student = null;
		public ChangeStudentsController(MainFormController mainFormController, ArrayList<String> params, StudentMainTableDTO student, int number, ChangeForm form) {
			this.mainFormController = mainFormController;
			this.form = form;
			this.number = number;
			this.student = student;
			for (String ob : params) {
				this.params.add(ob.trim());
			}
			changeStudent();
			
	}
		private void changeStudent() {
			AddChangeStudentsHelpController help = new AddChangeStudentsHelpController(); //pomocna klasa da se izbjegne dupliciranje koda
			int valid = help.checkParams(params);
			if(valid == 0) {
				//TODO poziv metode koja cuva novog studenta u bazi
				mainFormController.getMainTable().setStudent(number, params.get(2), params.get(0), params.get(1));
				//TODO poziv metode koja azurira tabelu
				final String message = "Uspjesno azuriranje!";
				JOptionPane.showMessageDialog(null, message);
				form.dispose();
				mainFormController.resetChangeFormOpened();
				}
			else if(valid == 1) {
				final String message = "Pogresan unos za ime studenta!";
				JOptionPane.showMessageDialog(null, message);
				form.setIme(student.getIme());
				form.setVisible(true);
			}else if(valid == 2) {
				final String message = "Pogresan unos za prezime studenta!";
				JOptionPane.showMessageDialog(null, message);
				form.setPrezime(student.getPrezime());
				form.setVisible(true);
			}else if(valid == 3) {
				final String message = "Pogresan unos za broj indeksa! "
						+ "Ocekivani format je: broj/godina";
				JOptionPane.showMessageDialog(null, message);
				form.setBrojIndeksa(student.getBrojIndeksa());
				form.setVisible(true);
			}
		}

}
