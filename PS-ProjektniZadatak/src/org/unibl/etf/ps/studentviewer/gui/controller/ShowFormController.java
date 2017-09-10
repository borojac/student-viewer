package org.unibl.etf.ps.studentviewer.gui.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.unibl.etf.ps.studentviewer.gui.ShowViewData;

public class ShowFormController {
	MainFormController mainFormControler;
	
	public ShowFormController(MainFormController mainFormControler) {
		this.mainFormControler = mainFormControler;
	}

	public void updateShowView(ArrayList<Boolean> showViewList) {
		HashMap<String, Boolean> updatedHashMap = new HashMap<String, Boolean>();
		updatedHashMap.put(ShowViewData.D_BROJINDEKSA, showViewList.get(0));
		updatedHashMap.put(ShowViewData.D_IME, showViewList.get(1));
		updatedHashMap.put(ShowViewData.D_PREZIME, showViewList.get(2));
		updatedHashMap.put(ShowViewData.D_ELEKTRIJADA, showViewList.get(3));
		updatedHashMap.put(ShowViewData.D_KOMENTAR, showViewList.get(4));
		
		ShowViewData.setNewHashMap(updatedHashMap);
		mainFormControler.getMainTable().changeView();
	}
}
