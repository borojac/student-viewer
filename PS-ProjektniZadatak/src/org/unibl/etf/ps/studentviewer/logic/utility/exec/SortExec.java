package org.unibl.etf.ps.studentviewer.logic.utility.exec;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.utility.SortUtil;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class SortExec extends Exec {
	
	private Date thirdParam;
	ArrayList<String> params = new ArrayList<String>();
	
	
	public SortExec(MainFormController mainFormController, ArrayList<Object> params) {
		this.mainFormController = mainFormController;
		for(Object o : params) {
			this.params.add((String)o);
		}
		mainFormController.getScheduler().add(this);
	}
	
	public void execute() {
		Comparator<StudentMainTableDTO> comparator = SortUtil.getComparator(students, params);
		students = mainFormController.getMainTable().getStudents();
		students.sort(comparator);
		mainFormController.getMainTable().setStudents(students);
		mainFormController.getMainTable().changeView();
		super.execute();
	}
}
