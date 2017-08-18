package org.unibl.etf.ps.studentviewer.exec;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.gui.controler.MainFormControler;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;
import org.unibl.etf.ps.studentviewer.utility.SortUtil;

public class SortExec extends Exec {
	
	private Date thirdParam;
	ArrayList<String> params = new ArrayList<String>();
	
	
	public SortExec(MainFormControler mainFormControler, ArrayList<Object> params) {
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
