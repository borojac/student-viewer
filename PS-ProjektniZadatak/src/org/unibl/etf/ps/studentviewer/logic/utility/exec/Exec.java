package org.unibl.etf.ps.studentviewer.logic.utility.exec;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.utility.maintableshow.UndoRedoData;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class Exec {
	protected MainFormController mainFormController;
	protected ArrayList<StudentMainTableDTO> students = null; 
	protected ArrayList<String> params = new ArrayList<String>();
	
	protected void execute() {
		UndoRedoData.addState(students);
	}
	
}
