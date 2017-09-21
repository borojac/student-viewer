package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.Date;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.gui.view.FilterChooseForm;
import org.unibl.etf.ps.studentviewer.gui.view.FilterForm;
import org.unibl.etf.ps.studentviewer.logic.utility.Filter;
import org.unibl.etf.ps.studentviewer.logic.utility.exec.FilterExec;

public class FilterFormController {
	MainFormController mainFormControler;
	FilterForm filterForm = null;
	
	public boolean setParameters() {
		if (filterForm.isPolozen() && filterForm.isNepolozen()) {
			JOptionPane.showMessageDialog(null, "Neispravno selektovani \"polozen predmet\" i \"nepolozen predmet\"!");
			return false;
		}
		
		
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
		
		
		if (filterForm.isPolozen())
			filterForm.addToControlParams(Filter.PREDMET_POLOZEN);
		
		if (filterForm.isNepolozen())
			filterForm.addToControlParams(Filter.PREDMET_NEPOLOZEN);
		
		return true;
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
