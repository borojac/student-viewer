package org.unibl.etf.ps.studentviewer.gui.addstudentcontroller;

import java.io.IOException;
import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.gui.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.exec.addstudentexec.ImporterExcel;

public class ImportController {
	public ImportController(MainFormController mainFormController) throws IOException {
		// TODO Auto-generated constructor stub
		ImporterExcel importerExcel = new ImporterExcel();
		ArrayList<String[]> studenti = importerExcel.getData(3);
	}
}
