package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.TestoviTableModel;
import org.unibl.etf.ps.studentviewer.gui.UndoRedoData;
import org.unibl.etf.ps.studentviewer.gui.view.AccountForm;
import org.unibl.etf.ps.studentviewer.gui.view.AddForm;
import org.unibl.etf.ps.studentviewer.gui.view.ChangeForm;
import org.unibl.etf.ps.studentviewer.gui.view.ChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.gui.view.ExportStudentsForm;
import org.unibl.etf.ps.studentviewer.gui.view.FilterForm;
import org.unibl.etf.ps.studentviewer.gui.view.MainForm;
import org.unibl.etf.ps.studentviewer.gui.view.ShowForm;
import org.unibl.etf.ps.studentviewer.gui.view.SortForm;
import org.unibl.etf.ps.studentviewer.gui.view.TestForm;
import org.unibl.etf.ps.studentviewer.logic.utility.exec.ExecScheduler;
import org.unibl.etf.ps.studentviewer.logic.utility.exec.SearchExec;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.TestDAO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class MainFormController {
	private static boolean sortFormOpened = false;
	private static boolean filterFormOpened = false;
	private static boolean accountFormOpened = false;
	private MainForm mainForm;
	private static ExecScheduler scheduler = new ExecScheduler();
	private ShowForm showForm = new ShowForm(this);
	/* Stankovic */
	private boolean addFormOpened = false;
	private boolean chooseAddTypeFormOpened = false;
	private boolean changeFormOpened = false;
	private boolean deleting = false;
	private boolean exporting = false;

	public void resetAddFormOpened() {
		addFormOpened = false;
	}

	public void resetChooseAddTypeFormOpened() {
		chooseAddTypeFormOpened = false;
	}

	public void resetChangeFormOpened() {
		changeFormOpened = false;
	}
	
	public void resetDeleting() {
		deleting = false;
	}

	public void resetExporting() {
		exporting = false;
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
		AccountForm accountForm = new AccountForm(this, mainForm.getNalogDTO());
		accountForm.setVisible(true);
		
		return true;
	}
	
	public void restoreTable() {
		ArrayList<StudentMainTableDTO> tempList = new ArrayList<>();
		for (StudentMainTableDTO s : StudentsForMainTable.getAllStudents())
			tempList.add(s);
		getMainTable().setStudents(tempList);
		UndoRedoData.addState(tempList);
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
		// TODO
		List<TestDTO> data = testDAO.getAllTests(1);

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

		public void createChangeForm(int[] selectedRow) {
			if (changeFormOpened)
				return;
			if (selectedRow != null && selectedRow.length == 1) {
				changeFormOpened = true;

				ChangeForm cf = new ChangeForm(this, getMainTable().getStudent(selectedRow[0]), selectedRow[0]);
				cf.setVisible(true);
			}else {
				final String message = "Odaberite samo jednog studenta za izmjenu!";
				JOptionPane.showMessageDialog(null, message);
			}
		}	
		public void deleteStudentsControler(int[] selectedRows) {
			if (deleting)
				return;
			deleting = true;
			new DeleteStudentsController(this, selectedRows);
		}
		
		public void choseExportType() {
			if (exporting)
				return;
			exporting = true;
			ExportStudentsForm esf = new ExportStudentsForm(this);
			esf.setVisible(true);
		}
		
		// Stankovic end//
	/*
	 * TODO - ko vec radi sa predmetima, za testove mi treba predmet u kontroleru ili formi
	 */
//	public PredmetDTO getPredmet() {
//		return predmet;
//	}
	public void resetFilterFormOpened() {
		filterFormOpened = false;
	}
}
