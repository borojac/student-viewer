package org.unibl.etf.ps.studentviewer.logic.utility;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.gui.view.student.MainTable;
import org.unibl.etf.ps.studentviewer.gui.view.student.MainTableModel;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;

public class SearchUtil {
	public static ArrayList<StudentMainTableDTO> searchForStudents(MainFormController mainFormController, ArrayList<String> params){
		MainTable table = mainFormController.getMainTable();
		ArrayList<StudentMainTableDTO> students = table.getStudents();
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
		return searchedStudents;
	}
}
