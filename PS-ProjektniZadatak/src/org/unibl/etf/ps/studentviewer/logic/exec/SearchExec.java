package org.unibl.etf.ps.studentviewer.logic.exec;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.MainTableModel;
import org.unibl.etf.ps.studentviewer.gui.control.MainFormController;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class SearchExec extends Exec {
	ArrayList<String> params = new ArrayList<String>();

	public SearchExec(MainFormController mainFormControler, ArrayList<Object> params) {
		this.mainFormControler = mainFormControler;
		for (Object o : params)
			this.params.add((String) o);
		mainFormControler.getScheduler().add(this);
	}

	public void execute() { // move to SearchUtil
		MainTableModel model = (MainTableModel) mainFormControler.getMainTable().getModel();
		MainTable table = mainFormControler.getMainTable();
		ArrayList<StudentMainTableDTO> students = mainFormControler.getMainTable().getStudents();
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
