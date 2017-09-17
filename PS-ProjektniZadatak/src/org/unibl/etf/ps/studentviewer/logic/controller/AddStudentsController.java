/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.UndoRedoData;
import org.unibl.etf.ps.studentviewer.gui.view.AddForm;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLStudentDAO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class AddStudentsController {
	ArrayList<String> params = new ArrayList<String>();
	MainFormController mainFormController = null;
	AddForm form = null;

	public AddStudentsController(MainFormController mainFormController, ArrayList<String> params, AddForm form) {
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
		if (valid == 0) {
			StudentMainTableDTO student = new StudentMainTableDTO(params.get(2), params.get(0), params.get(1));
			student.setKomentar(params.get(3)); //dodatno setovanje komentara;
			MySQLStudentDAO st = new MySQLStudentDAO();
			if (st.dodajStudentaUListu(student)) {
				int studetnID = st.getStudentBy(student.getBrojIndeksa()).getStudentId();
				student.setStudentId(studetnID);
				st.dodajStudentaNaPredmet(student, mainFormController.getMainForm().getSelectedPredmet());
				mainFormController.getMainTable().addStudent(student);
//				mainFormController.getMainTable().tableChanged();
				final String message = "Uspjesno cuvanje!";
				JOptionPane.showMessageDialog(null, message, "Obavjestenje!", JOptionPane.INFORMATION_MESSAGE);
			}else {
				final String message = "Vec postoji studen sa istim brojem indeksa!";
				JOptionPane.showMessageDialog(null, message, "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			}
			form.dispose();
			mainFormController.resetAddFormOpened();
			mainFormController.resetChooseAddTypeFormOpened();
		} else if (valid == 1) {
			final String message = "Pogresan unos za ime studenta!";
			JOptionPane.showMessageDialog(null, message, "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			form.setIme("");
			form.setFocusIme();
			form.setVisible(true);
		} else if (valid == 2) {
			final String message = "Pogresan unos za prezime studenta!";
			JOptionPane.showMessageDialog(null, message, "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			form.setIme(params.get(0));
			form.setPrezime("");
			form.setFocusPrezime();
			form.setVisible(true);
		} else if (valid == 3) {
			final String message = "Pogresan unos za broj indeksa! " + "Ocekivani format je: broj/godina";
			JOptionPane.showMessageDialog(null, message, "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			form.setIme(params.get(0));
			form.setPrezime(params.get(1));
			form.setBrojIndeksa("");
			form.setFocusBrIndeksa();
			form.setVisible(true);
		}
	}

}
