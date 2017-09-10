package org.unibl.etf.ps.studentviewer.logic.exec;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.gui.UndoRedoData;
import org.unibl.etf.ps.studentviewer.gui.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class Exec extends Thread {
	protected MainFormController mainFormControler;
	

	ArrayList<StudentMainTableDTO> students = null; 
	protected String secondParam = null;
	public void execute() {
		UndoRedoData.addState(students);
	}
	
}
