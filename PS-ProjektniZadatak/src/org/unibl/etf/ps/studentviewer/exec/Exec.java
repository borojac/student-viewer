package org.unibl.etf.ps.studentviewer.exec;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.gui.UndoRedoData;
import org.unibl.etf.ps.studentviewer.gui.controler.MainFormControler;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class Exec extends Thread {
	protected MainFormControler mainFormControler;
	

	ArrayList<StudentMainTableDTO> students = null; 
	protected String secondParam = null;
	public void execute() {
		UndoRedoData.addState(students);
	}
	
}
