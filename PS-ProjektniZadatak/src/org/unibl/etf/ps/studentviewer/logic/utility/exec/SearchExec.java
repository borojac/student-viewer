package org.unibl.etf.ps.studentviewer.logic.utility.exec;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.student.MainTable;
import org.unibl.etf.ps.studentviewer.gui.view.student.MainTableModel;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.utility.SearchUtil;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;

public class SearchExec extends Exec {

	public SearchExec(MainFormController mainFormController, ArrayList<Object> params) {
		this.mainFormController = mainFormController;
		for (Object o : params)
			this.params.add((String) o);
		mainFormController.getScheduler().add(this);
	}

	public void execute() { 
		ArrayList<StudentMainTableDTO> searchedStudents = SearchUtil.searchForStudents(mainFormController, params);
		MainTable table = mainFormController.getMainTable();
		if (searchedStudents.size() > 0) {
			table.setStudents(searchedStudents);
			table.repaint();
		} else {
			JOptionPane.showMessageDialog(null, "Nije pronadjen niti jedan student!");
			return;
		}
		this.students = searchedStudents;
		super.execute();
	}
}
