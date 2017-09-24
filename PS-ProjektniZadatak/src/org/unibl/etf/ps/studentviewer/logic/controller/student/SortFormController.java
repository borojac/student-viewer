package org.unibl.etf.ps.studentviewer.logic.controller.student;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.gui.view.student.SortChooseForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.SortForm;
import org.unibl.etf.ps.studentviewer.logic.controller.MainFormController;
import org.unibl.etf.ps.studentviewer.logic.utility.Sort;
import org.unibl.etf.ps.studentviewer.logic.utility.exec.SortExec;

public class SortFormController {
	MainFormController mainFormController = null;
	SortForm sortForm = null;

	
	public void createSortChooseForm() {
		new SortChooseForm(sortForm, SortFormController.this).setVisible(true);
	}
	
	public void sort() {
		ArrayList<String> paramsList = new ArrayList<String>();
		for (String x : sortForm.getSortParams().split(System.lineSeparator()))
			paramsList.add(x);

		SortExec sortExec = new SortExec(mainFormController, paramsList);
	}
	
	public SortFormController(MainFormController mainFormController, SortForm sortForm) {
		this.mainFormController = mainFormController;
		this.sortForm = sortForm;
	}
	
	public void addTestToSortParams(String param) {
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
	
	public void removeTestFromSortParams(String param) {
		param = Sort.TEST + " " + param;
		String currentParamsString = sortForm.getSortParams();
		if (currentParamsString.contains(param)) {
			currentParamsString = currentParamsString.replace(System.lineSeparator() + param, "");
			currentParamsString = currentParamsString.replace(param + System.lineSeparator(), "");
			currentParamsString = currentParamsString.replace(param, "");
			sortForm.setSortParams(currentParamsString);
		}
	}
	
	public void addToSortParams(String param) {
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
