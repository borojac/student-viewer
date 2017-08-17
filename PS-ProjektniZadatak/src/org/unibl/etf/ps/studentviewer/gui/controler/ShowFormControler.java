package org.unibl.etf.ps.studentviewer.gui.controler;

import java.util.ArrayList;
import java.util.HashMap;

import org.unibl.etf.ps.studentviewer.gui.ShowViewData;

public class ShowFormControler {
	MainFormControler mainFormControler;
	
	public ShowFormControler(MainFormControler mainFormControler) {
		this.mainFormControler = mainFormControler;
	}

	public void updateShowView(ArrayList<Boolean> showViewList) {
		HashMap<String, Boolean> updatedHashMap = new HashMap<String, Boolean>();
		updatedHashMap.put(ShowViewData.D_BROJINDEKSA, showViewList.get(0));
		updatedHashMap.put(ShowViewData.D_IME, showViewList.get(1));
		updatedHashMap.put(ShowViewData.D_PREZIME, showViewList.get(2));
		
		ShowViewData.setNewHashMap(updatedHashMap);
		mainFormControler.getMainTable().changeView();
	}
}
