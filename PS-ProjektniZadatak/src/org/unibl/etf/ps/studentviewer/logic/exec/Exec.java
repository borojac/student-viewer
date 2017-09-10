package org.unibl.etf.ps.studentviewer.logic.exec;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.gui.UndoRedoData;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class Exec extends Thread {
	protected MainFormController mainFormController;
	

	ArrayList<StudentMainTableDTO> students = null; 
	protected String secondParam = null;
	public void execute() {
		UndoRedoData.addState(students);
	}
	
}
