package org.unibl.etf.ps.studentviewer.gui.controler;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.exec.ExecScheduler;
import org.unibl.etf.ps.studentviewer.exec.SearchExec;
import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.view.MainForm;
import org.unibl.etf.ps.studentviewer.gui.view.ShowForm;
import org.unibl.etf.ps.studentviewer.gui.view.SortForm;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;

public class MainFormControler {
	private static boolean sortFormOpened = false;
	private static boolean filterFormOpened = false;
	private MainForm mainForm;
	private static ExecScheduler scheduler = new ExecScheduler();
	private ShowForm showForm = new ShowForm(this);
	
	
	
	public ExecScheduler getScheduler() {
		return scheduler;
	}

	public MainForm getMainForm() {
		return mainForm;
	}

	public MainTable getMainTable() {
		return mainForm.getMainTable();
	}
	
	
	
	public MainFormControler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MainFormControler(MainForm mainForm) {
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
	
	public void restoreTable() {
		getMainTable().setStudents(StudentsForMainTable.getAllStudents());
	}
	
	public void search(MainForm mainForm) {
		String params = mainForm.getSearchParams();
		mainForm.setSearchParams("");
		ArrayList<Object> paramsList = new ArrayList<Object>();
		for (String x : params.split(" "))
			paramsList.add(x);
		SearchExec exec = new SearchExec(this, paramsList);

	}

	public void createShowForm() {
		showForm.setVisible(true);
	}
}
