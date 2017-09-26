package org.unibl.etf.ps.studentviewer.logic.utility.exec;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.utility.SortUtil;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;

public class SortExec extends Exec {
	
	public SortExec(MainFormController mainFormController, ArrayList<String> params) {
		this.mainFormController = mainFormController;
		for(Object o : params) {
			this.params.add((String)o);
		}
		mainFormController.getScheduler().add(this);
	}
	
	public void execute() {
		Comparator<StudentMainTableDTO> comparator = SortUtil.getComparator(params);
		students = mainFormController.getMainTable().getStudents();
		students.sort(comparator);
		mainFormController.getMainTable().setStudents(students);
		mainFormController.getMainTable().changeView();
		super.execute();
	}
}
