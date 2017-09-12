package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.gui.view.SortChooseForm;
import org.unibl.etf.ps.studentviewer.gui.view.SortForm;
import org.unibl.etf.ps.studentviewer.logic.exec.SortExec;
import org.unibl.etf.ps.studentviewer.logic.utility.Sort;

public class SortFormController {
	MainFormController mainFormController = null;
	SortForm sortForm = null;

	public SortFormController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void createSortChooseForm(SortForm sf) {
		new SortChooseForm(sf, mainFormController, SortFormController.this).setVisible(true);
	}
	
	public void sort() {
		ArrayList<Object> paramsList = new ArrayList<Object>();
		for (String x : sortForm.getSortParams().split(System.lineSeparator()))
			paramsList.add(x);

		SortExec sortExec = new SortExec(mainFormController, paramsList);
	}
	
	public SortFormController(MainFormController mainFormController, SortForm sortForm) {
		this.mainFormController = mainFormController;
		this.sortForm = sortForm;
	}
	
	public void addTestToSortParams(String param, SortForm sortForm) {
		param = Sort.TEST + " " + param;
		String currentParamsString = sortForm.getSortParams();
		if (!currentParamsString.contains(param)) {
			StringBuilder currentParams = new StringBuilder(currentParamsString);
			if (!"".equals(currentParams.toString()))
				currentParams.append(System.lineSeparator());
			currentParams.append(param);
			sortForm.setSortParams(currentParams.toString());
		}
	}
	
	public void removeTestFromSortParams(String param, SortForm sortForm) {
		param = Sort.TEST + " " + param;
		String currentParamsString = sortForm.getSortParams();
		if (currentParamsString.contains(param)) {
			currentParamsString = currentParamsString.replace(System.lineSeparator() + param, "");
			currentParamsString = currentParamsString.replace(param + System.lineSeparator(), "");
			currentParamsString = currentParamsString.replace(param, "");
			sortForm.setSortParams(currentParamsString);
		}
	}
	
	public void addToSortParams(String param, SortForm sortForm) {
		String currentParamsString = sortForm.getSortParams();
		if (currentParamsString.contains(param)) {
			if (sortForm.getStateOfCheckBox(param) == false) {
				currentParamsString = currentParamsString.replace(System.lineSeparator() + param, "");
				currentParamsString = currentParamsString.replace(param + System.lineSeparator(), "");
				currentParamsString = currentParamsString.replace(param, "");
				sortForm.setSortParams(currentParamsString);
			}
		} else {
			if (sortForm.getStateOfCheckBox(param) == true) {
				StringBuilder currentParams = new StringBuilder(sortForm.getSortParams());
				if (!"".equals(currentParams.toString()))
					currentParams.append(System.lineSeparator());

				currentParams.append(param);
				sortForm.setSortParams(currentParams.toString());
			}
		}
	}

}
