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
		AdministratorFormController administratorFormController = null;
		ChangeForm form = null;
		int number;
		boolean isAdmin;
		StudentMainTableDTO student = null;
		public ChangeStudentsController(MainFormController mainFormController, AdministratorFormController administratorFormController, ArrayList<String> params, StudentMainTableDTO student, int number, ChangeForm form, boolean isAdmin) {
			this.mainFormController = mainFormController;
			this.administratorFormController = administratorFormController;
			this.form = form;
			this.number = number;
			this.student = student;
			this.isAdmin = isAdmin;
			for (String ob : params) {
				this.params.add(ob.trim());
			}
			changeStudent(isAdmin);
			
	}
		private void changeStudent(boolean isAdmin) {
				MySQLStudentDAO dao = new MySQLStudentDAO();
				StudentMainTableDTO newStudent = new StudentMainTableDTO(params.get(2), params.get(0), params.get(1));
				if(!isAdmin)
					newStudent.setKomentar(params.get(3));
				newStudent.setId(student.getId());
				if(!isAdmin) {
					dao.azurirajStudentaNaPredmetu(newStudent, mainFormController.getMainForm().getSelectedPredmet());
				//mainFormController.getMainTable().setStudent(number, params.get(2), params.get(0), params.get(1), params.get(3)); //4 parametar je komentar
				}else
					dao.azurirajStudentaUListi(newStudent, student.getBrojIndeksa());
				final String message = "Uspjesno azuriranje!";
				JOptionPane.showMessageDialog(null, message,"Obavjestenje", JOptionPane.INFORMATION_MESSAGE);
				form.dispose();
				if(!isAdmin)
					mainFormController.resetChangeFormOpened();
				else
					administratorFormController.resetChanging();
				}
}
