package org.unibl.etf.ps.studentviewer.logic.exec;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.utility.SortUtil;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class SortExec extends Exec {
	
	private Date thirdParam;
	ArrayList<String> params = new ArrayList<String>();
	
	
	public SortExec(MainFormController mainFormControler, ArrayList<Object> params) {
		this.mainFormControler = mainFormControler;
		for(Object o : params) {
			this.params.add((String)o);
		}
		students = mainFormControler.getMainTable().getStudents();
		mainFormControler.getScheduler().add(this);
	}
	
	public void execute() {
		Comparator<StudentMainTableDTO> comparator = SortUtil.getComparator(students, params);
		students.sort(comparator);
		mainFormControler.getMainTable().setStudents(students);
		mainFormControler.getMainTable().changeView();
		super.execute();
	}
}
