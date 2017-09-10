package org.unibl.etf.ps.studentviewer.logic.exec.studentdatamanipulation;

import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.exec.Exec;


public class DeleteExec extends Exec {
	int [] selectedRows;
	public DeleteExec(MainFormController mainFormController, int[] selectedRows) {
		this.selectedRows = selectedRows;
		this.mainFormController = mainFormController;
	}
}
