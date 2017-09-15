package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.unibl.etf.ps.studentviewer.gui.ShowViewData;
import org.unibl.etf.ps.studentviewer.gui.view.ShowChooseForm;
import org.unibl.etf.ps.studentviewer.gui.view.ShowForm;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class ShowFormController {
	MainFormController mainFormController;
	
	public ShowFormController(MainFormController mainFormController) {
		this.mainFormController = mainFormController;
	}

	public void updateShowView(ArrayList<Boolean> showViewList) {
		HashMap<String, Boolean> updatedHashMap = new HashMap<String, Boolean>();
		updatedHashMap.put(ShowViewData.D_BROJINDEKSA, showViewList.get(0));
		updatedHashMap.put(ShowViewData.D_IME, showViewList.get(1));
		updatedHashMap.put(ShowViewData.D_PREZIME, showViewList.get(2));
		updatedHashMap.put(ShowViewData.D_ELEKTRIJADA, showViewList.get(3));
		updatedHashMap.put(ShowViewData.D_KOMENTAR, showViewList.get(4));
		updatedHashMap.put(ShowViewData.D_TEST, showViewList.get(5));
		ShowViewData.setNewHashMap(updatedHashMap);
//		ArrayList<StudentMainTableDTO> tempList = new ArrayList<StudentMainTableDTO>();
//		for (StudentMainTableDTO s : StudentsForMainTable.getAllStudents())
//			tempList.add(s);
//		mainFormController.getMainTable().setStudents(tempList);
		mainFormController.getMainTable().changeView();
	}
	
	public void createShowChooseForm(ShowForm sf) {
		new  ShowChooseForm(sf, mainFormController).setVisible(true);
	}
	
}
