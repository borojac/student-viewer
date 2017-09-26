package org.unibl.etf.ps.studentviewer.logic.utility.exec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.utility.FilterUtil;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;

public class FilterExec extends Exec {
	HashMap<String, HashMap<String, String>> testovi;
	
	public FilterExec(MainFormController mainFormControler, HashMap<String, HashMap<String, String>> hashMap, ArrayList<String> controlParams) {
		this.mainFormController = mainFormControler;
		this.testovi = hashMap;
		this.params = controlParams;
		mainFormController.getScheduler().add(this);
	}

	public void execute() {
		ArrayList<StudentMainTableDTO> filteredStudents = FilterUtil.filter(mainFormController, testovi, params);
		mainFormController.getMainTable().setStudents(filteredStudents);
		mainFormController.getMainTable().changeView();
		students = filteredStudents;
		super.execute();
	}
}
