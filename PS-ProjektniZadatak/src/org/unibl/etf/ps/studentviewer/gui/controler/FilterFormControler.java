package org.unibl.etf.ps.studentviewer.gui.controler;

import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.exec.FilterExec;
import org.unibl.etf.ps.studentviewer.gui.view.FilterForm;

public class FilterFormControler {
	// ArrayList<Student> firstParam;
	String secondParam;
	Date thirdParam;
	MainFormControler mainFormControler;

	public FilterFormControler(MainFormControler mainFormControler) {
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
