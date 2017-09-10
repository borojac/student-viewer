package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.TestoviTableModel;
import org.unibl.etf.ps.studentviewer.gui.UndoRedoData;
import org.unibl.etf.ps.studentviewer.gui.addstudentview.AddForm;
import org.unibl.etf.ps.studentviewer.gui.addstudentview.ChangeForm;
import org.unibl.etf.ps.studentviewer.gui.addstudentview.ChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.gui.view.AccountForm;
import org.unibl.etf.ps.studentviewer.gui.view.FilterForm;
import org.unibl.etf.ps.studentviewer.gui.view.MainForm;
import org.unibl.etf.ps.studentviewer.gui.view.ShowForm;
import org.unibl.etf.ps.studentviewer.gui.view.SortForm;
import org.unibl.etf.ps.studentviewer.gui.view.TestForm;
import org.unibl.etf.ps.studentviewer.logic.exec.ExecScheduler;
import org.unibl.etf.ps.studentviewer.logic.exec.SearchExec;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.TestDAO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class MainFormController {
	private static boolean sortFormOpened = false;
	private static boolean filterFormOpened = false;
	private static boolean accountFormOpened = false;
	private MainForm mainForm;
	private static ExecScheduler scheduler = new ExecScheduler();
	private ShowForm showForm = new ShowForm(this);
	/* Stankovic */
	private static boolean addFormOpened = false;
	private static boolean chooseAddTypeFormOpened = false;
	private static boolean changeFormOpened = false;

	public static void resetAddFormOpened() {
		addFormOpened = false;
	}

	public static void resetChooseAddTypeFormOpened() {
		chooseAddTypeFormOpened = false;
	}

	public static void resetChangeFormOpened() {
		changeFormOpened = false;
	}

	/* Stankovic end */
	
	
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
	
	public void addTestAction() {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				TestForm tf = new TestForm(null, mainForm);
				tf.setVisible(true);
			}
		});
	}
	
	public void editTestAction(JTable testoviTable) {
		final TestDTO selected = ((TestoviTableModel) testoviTable.getModel()).getData().get(testoviTable.getSelectedRow());
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				TestForm tf = new TestForm(selected, mainForm);
				tf.setVisible(true);
			}
		});
	}
	
	public void initTestoviTable(JTable testoviTable) {

		DAOFactory testFactory = new MySQLDAOFactory();
		TestDAO testDAO = testFactory.getTestDAO();
		List<TestDTO> data = testDAO.getAllTests();

		TestoviTableModel model = new TestoviTableModel(data);
		testoviTable.setModel(model);

	}
	
	public void deleteTestAction(JTable testoviTable) {

		DAOFactory factory = new MySQLDAOFactory();
		TestDAO testDAO = factory.getTestDAO();
		TestDTO test = ((TestoviTableModel) testoviTable.getModel()).getRowAt(testoviTable.getSelectedRow());
		if (!testDAO.deleteTest(test))
			JOptionPane.showMessageDialog(null, "Test ne može biti obrisan. Lista studenata na testu mora biti prazna da bi se test mogao brisati.", "Greška", JOptionPane.INFORMATION_MESSAGE);
		else {
			TestoviTableModel model = (TestoviTableModel) testoviTable.getModel();
			List<TestDTO> data = model.getData();
			data.remove(testoviTable.getSelectedRow());
			model.fireTableDataChanged();
		}
			
	}
	
	public void initMouseHoverAction(MouseEvent event, JTable testoviTable) {
		if (testoviTable.contains(event.getPoint())) {
			int row = testoviTable.rowAtPoint(event.getPoint());
			TestoviTableModel model = (TestoviTableModel) testoviTable.getModel();
			testoviTable.setToolTipText(model.getRowAt(row).getNapomena());
		}
	}
	// Stankovic begin//
	
		public void createChooseAddTypeForm() {
			if (chooseAddTypeFormOpened)
				return;

			chooseAddTypeFormOpened = true;

			ChooseAddTypeForm catf = new ChooseAddTypeForm(this);
			catf.setVisible(true);
		}

		public void createAddForm() {
			if (addFormOpened)
				return;

			addFormOpened = true;

			AddForm af = new AddForm(this);
			af.setVisible(true);
		}

		public void createChangeForm(int selectedRow) {
			if (changeFormOpened)
				return;
			if (selectedRow != -1) {
				changeFormOpened = true;

				ChangeForm cf = new ChangeForm(this, StudentsForMainTable.getAllStudents().get(selectedRow), selectedRow);
				cf.setVisible(true);
			}
		}

		public void deleteStudents(int[] selectedRows) {
			if (selectedRows != null && selectedRows.length > 0) {
				StringBuilder message = new StringBuilder();
				message.append("Da li ste sigurni da zelite da obrisete ");
				if (selectedRows.length == 1)
					message.append("odabranog studenta?");
				else
					message.append("odabrane studente?");
				if (JOptionPane.showConfirmDialog(null, message.toString()) == JOptionPane.YES_OPTION) {
					for (int rb : selectedRows)
						StudentsForMainTable.getAllStudents().remove(rb);
					final String message2 = "Uspjesno brisanje!";
					JOptionPane.showMessageDialog(null, message2);
				}
			}
		}
		// Stankovic end//
	/*
	 * TODO - ko vec radi sa predmetima, za testove mi treba predmet u kontroleru
	 */
//	public PredmetDTO getPredmet() {
//		return predmet;
//	}
	public void resetFilterFormOpened() {
		filterFormOpened = false;
	}
}
