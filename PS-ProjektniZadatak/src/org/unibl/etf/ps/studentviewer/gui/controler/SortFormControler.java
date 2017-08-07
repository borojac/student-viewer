package org.unibl.etf.ps.studentviewer.gui.controler;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.exec.SortExec;
import org.unibl.etf.ps.studentviewer.gui.view.SortForm;

public class SortFormControler {
	MainFormControler mainFormControler = null;

	public SortFormControler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SortFormControler(MainFormControler mainFormControler, SortForm sortForm) {
		this.mainFormControler = mainFormControler;
		ArrayList<Object> paramsList = new ArrayList<Object>();
		for (String x : sortForm.getSortParams().split(System.lineSeparator()))
			paramsList.add(x);

		SortExec sortExec = new SortExec(mainFormControler, paramsList);
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
