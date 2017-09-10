package org.unibl.etf.ps.studentviewer.logic.exec;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.MainTableModel;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class SearchExec extends Exec {
	ArrayList<String> params = new ArrayList<String>();

	public SearchExec(MainFormController mainFormController, ArrayList<Object> params) {
		this.mainFormController = mainFormController;
		for (Object o : params)
			this.params.add((String) o);
		mainFormController.getScheduler().add(this);
	}

	public void execute() { // move to SearchUtil
		MainTableModel model = (MainTableModel) mainFormController.getMainTable().getModel();
		MainTable table = mainFormController.getMainTable();
		ArrayList<StudentMainTableDTO> students = mainFormController.getMainTable().getStudents();
		ArrayList<StudentMainTableDTO> searchedStudents = new ArrayList<StudentMainTableDTO>();

		for (StudentMainTableDTO s : students) {
			for (String param : params) {
				if (s.getIme().toLowerCase().contains(param.toLowerCase()) || s.getBrojIndeksa().contains(param)
						|| s.getPrezime().toLowerCase().contains(param.toLowerCase())) {
					if (!searchedStudents.contains(s))
						searchedStudents.add(s);
				}
			}
		}
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
