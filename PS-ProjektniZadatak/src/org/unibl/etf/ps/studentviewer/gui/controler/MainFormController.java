package org.unibl.etf.ps.studentviewer.gui.controler;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.exec.ExecScheduler;
import org.unibl.etf.ps.studentviewer.exec.SearchExec;
import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.UndoRedoData;
import org.unibl.etf.ps.studentviewer.gui.addstudentview.AddForm;
import org.unibl.etf.ps.studentviewer.gui.addstudentview.ChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.gui.view.AccountForm;
import org.unibl.etf.ps.studentviewer.gui.view.FilterForm;
import org.unibl.etf.ps.studentviewer.gui.view.MainForm;
import org.unibl.etf.ps.studentviewer.gui.view.ShowForm;
import org.unibl.etf.ps.studentviewer.gui.view.SortForm;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;

public class MainFormController {
	private static boolean sortFormOpened = false;
	private static boolean filterFormOpened = false;
	private static boolean accountFormOpened = false;
	private MainForm mainForm;
	private static ExecScheduler scheduler = new ExecScheduler();
	private ShowForm showForm = new ShowForm(this);
	/*Stankovic*/
	private static boolean addFormOpened = false;
	private static boolean chooseAddTypeFormOpened = false;

	
	public static void resetAddFormOpened() {
		addFormOpened = false;
	}
	public static void resetChooseAddTypeFormOpened() {
		chooseAddTypeFormOpened = false;
	}

	/*Stankovic end*/
	
	
	public static void resetSortFormOpened(){
		sortFormOpened = false;
	}
	
	public static void resetAccountFormOpened() {
		accountFormOpened = false;
	}
	
	public ExecScheduler getScheduler() {
		return scheduler;
	}

	public MainForm getMainForm() {
		return mainForm;
	}

	public MainTable getMainTable() {
		return mainForm.getMainTable();
	}
	
	
	
	public MainFormController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MainFormController(MainForm mainForm) {
		this.mainForm = mainForm;
	}

	public synchronized boolean createSortForm() {
		if (sortFormOpened)
			return false;

		sortFormOpened = true;

		SortForm sortForm = new SortForm(this);
		sortForm.setVisible(true);
		return true;
	}
	
	public synchronized boolean createAccountForm() {
		if(accountFormOpened)
			return false;
		
		accountFormOpened = true;
		AccountForm accountForm = new AccountForm(this);
		accountForm.setVisible(true);
		
		return true;
	}
	
	public void restoreTable() {
		getMainTable().setStudents(StudentsForMainTable.getAllStudents());
		UndoRedoData.addState(StudentsForMainTable.getAllStudents());
	}
	
	public void search(MainForm mainForm) {
		String params = mainForm.getSearchParams();
		mainForm.setSearchParams("");
		if ("".equals(params))
			return;
		ArrayList<Object> paramsList = new ArrayList<Object>();
		for (String x : params.split(" "))
			paramsList.add(x);
		SearchExec exec = new SearchExec(this, paramsList);

	}

	public void createShowForm() {
		showForm.setVisible(true);
	}

	public void createFilterForm() {
		if (filterFormOpened)
			return;
		
		filterFormOpened = true;
		
		FilterForm f = new FilterForm(this);
		f.setVisible(true);
	}
	/*Stankovic begin*/
	public synchronized boolean createAddForm() {
		if (addFormOpened)
			return false;
		addFormOpened = true;
		AddForm addForm = new AddForm(this);
		addForm.setVisible(true);
		return true;
	}
	
	public synchronized boolean createChooseAddTypeForm() {
		if (chooseAddTypeFormOpened)
			return false;
		chooseAddTypeFormOpened = true;
		ChooseAddTypeForm chooseAddTypeForm = new ChooseAddTypeForm(this);
		chooseAddTypeForm.setVisible(true);
		return true;
	}
	
	/*Stankovic end*/
}
