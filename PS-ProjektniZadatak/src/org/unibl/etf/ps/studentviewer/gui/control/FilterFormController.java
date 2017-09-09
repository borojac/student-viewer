package org.unibl.etf.ps.studentviewer.gui.control;

import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.gui.view.FilterForm;
import org.unibl.etf.ps.studentviewer.logic.exec.FilterExec;

public class FilterFormController {
	// ArrayList<Student> firstParam;
	String secondParam;
	Date thirdParam;
	MainFormController mainFormControler;

	public FilterFormController(MainFormController mainFormControler) {
		super();
		this.mainFormControler = mainFormControler;
	}

	public void sort() {
		
	}

	public void filter(FilterForm filterForm) {
		ArrayList<Object> params = filterForm.getFilterParams();
		new FilterExec(mainFormControler, params);
	}
}
