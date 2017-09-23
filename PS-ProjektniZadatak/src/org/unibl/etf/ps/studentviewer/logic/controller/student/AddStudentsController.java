/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.controller.student;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.student.AddForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.UndoRedoData;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.controller.nalog.AdministratorFormController;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLStudentDAO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class AddStudentsController {
	ArrayList<String> params = new ArrayList<String>();
	ArrayList<ArrayList<String>> params2 = new ArrayList<>();
	MainFormController mainFormController = null;
	AdministratorFormController adminFormController = null;
	AddForm form = null;

	public AddStudentsController(AdministratorFormController adminFormController, ArrayList<String> params,
			AddForm form) {
		this.adminFormController = adminFormController;
		this.form = form;
		for (String ob : params) {
			this.params.add(ob.trim());
		}
		
		addOneStudentAdmin();
	}

	public AddStudentsController(MainFormController mainFormController,ArrayList<ArrayList<String>> params) {
		this.mainFormController = mainFormController;
		this.params2 = params;
		addOneStudentUser();
	}

	private void addOneStudentAdmin() {
		AddChangeStudentsHelpController help = new AddChangeStudentsHelpController();
		int valid = help.checkParams(params);
		if (valid == 0) {
			StudentMainTableDTO student = new StudentMainTableDTO(params.get(2), params.get(0), params.get(1));
			// student.setKomentar(params.get(3)); //dodatno setovanje komentara;
			MySQLStudentDAO st = new MySQLStudentDAO();
			if (st.dodajStudentaUListu(student)) {
				// int studetnID = st.getStudentBy(student.getBrojIndeksa()).getStudentId();
				// student.setStudentId(studetnID);
				// st.dodajStudentaNaPredmet(student,
				// mainFormController.getMainForm().getSelectedPredmet());
				// mainFormController.getMainTable().addStudent(student);
				// mainFormController.getMainTable().tableChanged();
				final String message = "Uspjesno cuvanje!";
				JOptionPane.showMessageDialog(null, message, "Obavjestenje!", JOptionPane.INFORMATION_MESSAGE);
			} else {
				final String message = "Vec postoji studen sa istim brojem indeksa!";
				JOptionPane.showMessageDialog(null, message, "Upozorenje!", JOptionPane.WARNING_MESSAGE);
			}
			form.dispose();
			adminFormController.resetAddStudentFormOpened();
			adminFormController.resetChooseAddStudentsTypeFormOpened();
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

	private void addOneStudentUser() {
		try {
			for (ArrayList<String> list : params2) {
				StudentMainTableDTO student = new StudentMainTableDTO(list.get(2), list.get(0), list.get(1));
				MySQLStudentDAO st = new MySQLStudentDAO();
				int studetnID = st.getStudentBy(student.getBrojIndeksa()).getStudentId();
				student.setStudentId(studetnID);
				st.dodajStudentaNaPredmet(student, mainFormController.getMainForm().getSelectedPredmet());
				mainFormController.getMainTable().addStudent(student);
			}
			final String message = "Uspjesno cuvanje!";
			JOptionPane.showMessageDialog(null, message, "Obavjestenje!", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			final String message = "Greska!";
			JOptionPane.showMessageDialog(null, message, "Upozorenje!", JOptionPane.WARNING_MESSAGE);

		}
	}

}
