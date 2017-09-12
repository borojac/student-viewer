package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.unibl.etf.ps.studentviewer.gui.view.SortChooseForm;
import org.unibl.etf.ps.studentviewer.gui.view.SortForm;

public class SortChooseFormController {
	SortChooseForm sortChooseForm;
	SortForm sf;

	public SortChooseFormController(SortForm sf, SortChooseForm showChooseForm) {
		this.sortChooseForm = showChooseForm;
		this.sf = sf;
	}

	public void rightFlow() {
		// TODO Auto-generated method stub
		String[] selectedElements = sortChooseForm.getLeftSelected();
		JList leftList = sortChooseForm.getChooseList();
		JList rightList = sortChooseForm.getChoosenList();

		DefaultListModel<String> leftListModel = (DefaultListModel<String>) leftList.getModel();
		DefaultListModel<String> rightListModel = (DefaultListModel<String>) rightList.getModel();

		for (String s : selectedElements) {
			leftListModel.removeElement(s);
			rightListModel.addElement(s);
		}
	}

	public void leftFlow() {
		// TODO Auto-generated method stub
		String[] selectedElements = sortChooseForm.getRightSelected();
		JList leftList = sortChooseForm.getChooseList();
		JList rightList = sortChooseForm.getChoosenList();

		DefaultListModel leftListModel = (DefaultListModel) leftList.getModel();
		DefaultListModel rightListModel = (DefaultListModel) rightList.getModel();

		for (String s : selectedElements) {
			rightListModel.removeElement(s);
			leftListModel.addElement(s);
		}
	}

	public void save(SortFormController sfc) {
		JList<String> rightList = sortChooseForm.getChoosenList();
		JList<String> leftList = sortChooseForm.getChooseList();

		DefaultListModel<String> rightListModel = (DefaultListModel<String>) rightList.getModel();
		DefaultListModel<String> leftListModel = (DefaultListModel<String>) leftList.getModel();

		ArrayList<String> choosenExams = new ArrayList<String>();

		for (int i = 0; i < rightListModel.size(); i++) {
			sfc.addTestToSortParams(rightListModel.getElementAt(i), sf);
			choosenExams.add(rightListModel.get(i));
		}
		
		sf.setExamsToSort(choosenExams);
		
		for (int i = 0; i < leftListModel.size(); i++)
			sfc.removeTestFromSortParams(leftListModel.getElementAt(i), sf);

	}
}
