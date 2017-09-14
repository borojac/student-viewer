/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.ChangeForm;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLStudentDAO;
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
				MySQLStudentDAO dao = new MySQLStudentDAO();
				StudentMainTableDTO newStudent = new StudentMainTableDTO(params.get(2), params.get(0), params.get(1));
				dao.azurirajStudentaUListi(newStudent, student.getBrojIndeksa());
				mainFormController.getMainTable().setStudent(number, params.get(2), params.get(0), params.get(1));

				final String message = "Uspjesno azuriranje!";
				JOptionPane.showMessageDialog(null, message,"Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
				form.dispose();
				mainFormController.resetChangeFormOpened();
				}
			else if(valid == 1) {
				final String message = "Pogresan unos za ime studenta!";
				JOptionPane.showMessageDialog(null, message,"Upozorenje!", JOptionPane.WARNING_MESSAGE);
				form.setIme(student.getIme());
				form.setVisible(true);
			}else if(valid == 2) {
				final String message = "Pogresan unos za prezime studenta!";
				JOptionPane.showMessageDialog(null, message,"Upozorenje!", JOptionPane.WARNING_MESSAGE);
				form.setPrezime(student.getPrezime());
				form.setVisible(true);
			}else if(valid == 3) {
				final String message = "Pogresan unos za broj indeksa! "
						+ "Ocekivani format je: broj/godina";
				JOptionPane.showMessageDialog(null, message,"Upozorenje!", JOptionPane.WARNING_MESSAGE);
				form.setBrojIndeksa(student.getBrojIndeksa());
				form.setVisible(true);
			}
		}

}
