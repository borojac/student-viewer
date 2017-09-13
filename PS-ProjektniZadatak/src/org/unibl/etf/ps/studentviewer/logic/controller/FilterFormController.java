package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.gui.view.FilterChooseForm;
import org.unibl.etf.ps.studentviewer.gui.view.FilterForm;
import org.unibl.etf.ps.studentviewer.logic.utility.Filter;
import org.unibl.etf.ps.studentviewer.logic.utility.exec.FilterExec;

public class FilterFormController {
	String secondParam;
	Date thirdParam;
	MainFormController mainFormControler;
	FilterForm filterForm = null;
	
	public void setParameters() {
		String elektrijada = filterForm.getElektrijada();
		if (!"".equals(elektrijada))
			if (elektrijada.contains("koji idu na")) {
				filterForm.addToControlParams(Filter.IDE_NA_ELEKTRIJADU);
			}else 
				filterForm.addToControlParams(Filter.NE_IDE_NA_ELEKTRIJADU);
		
		String komentar = filterForm.getKomentar();
		if (!"".equals(komentar))
			if (komentar.contains("imaju")) {
				filterForm.addToControlParams(Filter.IMA_KOMENTAR);
			}else
				filterForm.addToControlParams(Filter.NEMA_KOMENTAR);
	}
	
	public FilterFormController(MainFormController mainFormControler, FilterForm filterForm) {
		super();
		this.mainFormControler = mainFormControler;
		this.filterForm = filterForm;
	}

	public void filter() {
		new FilterExec(mainFormControler, filterForm.getTestoviMap(), filterForm.getControlParams());
	}
	
	public void createChooseForm() {
		new FilterChooseForm(filterForm, this).setVisible(true);
	}
}
