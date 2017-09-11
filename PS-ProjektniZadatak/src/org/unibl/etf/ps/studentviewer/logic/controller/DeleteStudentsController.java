package org.unibl.etf.ps.studentviewer.logic.controller;

import javax.swing.JOptionPane;

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
			JOptionPane.showMessageDialog(null, message);
		}
		mainFormController.resetDeleting();
	}
	
	private void delete() {
			JOptionPane jop = new JOptionPane();
			StringBuilder sb = new StringBuilder();
			sb.append("Sigurno zelite da obrisete ");
			if(selectedRows.length == 1)
				sb.append("oznacenog studenta?");
			else
				sb.append("oznacene studente?");
			if(jop.showConfirmDialog(null, sb.toString()) == JOptionPane.YES_OPTION) {
				mainFormController.getMainTable().deleteStudents(selectedRows);
				final String message = "Uspjesno brisanje!";
				JOptionPane.showMessageDialog(null, message);
			}
	}
}
