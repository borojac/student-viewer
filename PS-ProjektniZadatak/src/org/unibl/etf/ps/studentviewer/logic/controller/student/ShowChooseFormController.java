package org.unibl.etf.ps.studentviewer.logic.controller.student;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.unibl.etf.ps.studentviewer.gui.view.student.ShowChooseForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.ShowForm;
import org.unibl.etf.ps.studentviewer.logic.utility.maintableshow.ShowViewData;

public class ShowChooseFormController {
	ShowChooseForm showChooseForm;
	ShowForm sf;

	public ShowChooseFormController(ShowForm sf, ShowChooseForm showChooseForm) {
		this.showChooseForm = showChooseForm;
		this.sf = sf;
	}

	public void rightFlow() {
		// TODO Auto-generated method stub
		String[] selectedElements = showChooseForm.getLeftSelected();
		JList leftList = showChooseForm.getChooseList();
		JList rightList = showChooseForm.getChoosenList();

		DefaultListModel<String> leftListModel = (DefaultListModel<String>) leftList.getModel();
		DefaultListModel<String> rightListModel = (DefaultListModel<String>) rightList.getModel();

		for (String s : selectedElements) {
			leftListModel.removeElement(s);
			rightListModel.addElement(s);
		}
	}

	public void leftFlow() {
		// TODO Auto-generated method stub
		String[] selectedElements = showChooseForm.getRightSelected();
		JList leftList = showChooseForm.getChooseList();
		JList rightList = showChooseForm.getChoosenList();

		DefaultListModel leftListModel = (DefaultListModel) leftList.getModel();
		DefaultListModel rightListModel = (DefaultListModel) rightList.getModel();

		for (String s : selectedElements) {
			rightListModel.removeElement(s);
			leftListModel.addElement(s);
		}
	}

	public void save() {
		DefaultListModel<String> trueModel = (DefaultListModel<String>) showChooseForm.getChoosenList().getModel();
		DefaultListModel<String> falseModel = (DefaultListModel<String>) showChooseForm.getChooseList().getModel();
		
		String[] trueExams = new String[trueModel.size()];
		String[] falseExams = new String[falseModel.size()];

		for (int i = 0; i < trueModel.size(); i ++)
			trueExams[i] = new String(trueModel.getElementAt(i));
		

		for (int i = 0; i < falseModel.size(); i ++)
			falseExams[i] = new String(falseModel.getElementAt(i));
		
		for (String s : trueExams) 
			ShowViewData.setExam(s);
		
		for (String s : falseExams) 
			ShowViewData.resetExam(s);
		showChooseForm.setVisible(true);
		
	}

}
