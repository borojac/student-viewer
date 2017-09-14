/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.model.dao.MySQLStudentDAO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class DeleteStudentsController {
	MainFormController mainFormController = null;
	int[] selectedRows = null;
	public DeleteStudentsController(MainFormController mainFormController, int[] selectedRows) {
		this.mainFormController = mainFormController;
		this.selectedRows = selectedRows;
		if(selectedRows != null && selectedRows.length > 0)
			delete();
		else {
			final String message = "Odaberite studente za brisanje!";
			JOptionPane.showMessageDialog(null, message,"Obavjestenje!", JOptionPane.INFORMATION_MESSAGE);
		}
		mainFormController.resetDeleting();
	}
	
	private void delete() {
			JOptionPane jop = new JOptionPane();
			jop.setName("Potvrda brisanja");
			StringBuilder sb = new StringBuilder();
			sb.append("Sigurno zelite da obrisete ");
			if(selectedRows.length == 1)
				sb.append("oznacenog studenta?");
			else
				sb.append("oznacene studente?");
			if(jop.showConfirmDialog(null, sb.toString()) == JOptionPane.YES_OPTION) {
				MySQLStudentDAO dao = new MySQLStudentDAO();
				for(int i = 0; i < selectedRows.length; i++) {
					String indeks = mainFormController.getMainTable().getStudent(selectedRows[i]).getBrojIndeksa();
					PredmetDTO predmet = mainFormController.getMainForm().getSelectedPredmet();
					dao.obrisiStudentaSaPredmeta(dao.getStudentBy(indeks).getStudentId(), predmet);
				}
				mainFormController.getMainTable().deleteStudents(selectedRows);
				final String message = "Uspjesno brisanje!";
				JOptionPane.showMessageDialog(null, message,"Obavjestenje!", JOptionPane.INFORMATION_MESSAGE);
			}
	}
}
