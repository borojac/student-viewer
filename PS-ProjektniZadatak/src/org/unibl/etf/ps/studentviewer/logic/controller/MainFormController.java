package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.unibl.etf.ps.studentviewer.gui.view.MainForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.DodavanjeStudentaZaElektrijaduForm;
import org.unibl.etf.ps.studentviewer.gui.view.elektrijada.ElektrijadaForm;
import org.unibl.etf.ps.studentviewer.gui.view.nalog.AccountForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.AddForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.AdminStudentForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.ChangeForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.ChooseAddTypeForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.ExportStudentsForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.FilterForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.MainTable;
import org.unibl.etf.ps.studentviewer.gui.view.student.ShowForm;
import org.unibl.etf.ps.studentviewer.gui.view.student.SortForm;
import org.unibl.etf.ps.studentviewer.gui.view.test.TestForm;
import org.unibl.etf.ps.studentviewer.gui.view.test.TestoviTableModel;
import org.unibl.etf.ps.studentviewer.logic.controller.student.DeleteStudentsController;
import org.unibl.etf.ps.studentviewer.logic.utility.exec.ExecScheduler;
import org.unibl.etf.ps.studentviewer.logic.utility.exec.SearchExec;
import org.unibl.etf.ps.studentviewer.logic.utility.maintableshow.UndoRedoData;
import org.unibl.etf.ps.studentviewer.persistence.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.ElektrijadaDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.StudentDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dao.TestDAO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.TestDTO;

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

	public void resetSortFormOpened() {
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
		if (accountFormOpened)
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
		int selectedRow = testoviTable.getSelectedRow();
		mainForm.refreshTestoviTable();
		if (selectedRow >= testoviTable.getRowCount() || selectedRow < 0) 
			return;
		final TestDTO selected = ((TestoviTableModel) testoviTable.getModel()).getRowAt(selectedRow);
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				TestForm tf = new TestForm(selected, mainForm);
				tf.setVisible(true);
			}
		});
	}

	public void initTestoviTable() {
		PredmetDTO activePredmet = mainForm.getSelectedPredmet();
		if (activePredmet != null) {
			DAOFactory testFactory = new MySQLDAOFactory();
			TestDAO testDAO = testFactory.getTestDAO();
			List<TestDTO> data = testDAO.getAllTests(activePredmet.getPredmetId());

			mainForm.getTestoviTable().setModel(new TestoviTableModel(data));
		} else {
			mainForm.getTestoviTable().setModel(new TestoviTableModel());
		}
	}

	public void deleteTestAction() {
		JTable testoviTable = mainForm.getTestoviTable();
		DAOFactory factory = new MySQLDAOFactory();
		TestDAO testDAO = factory.getTestDAO();
		TestDTO test = ((TestoviTableModel) testoviTable.getModel()).getRowAt(testoviTable.getSelectedRow());
		try {
			if (testDAO.deleteTest(test)) {
				mainForm.refreshTestoviTable();
				StudentsForMainTable.setTest(test, mainForm.getMainTable(), true);
			}
		} catch (SQLException e) {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					JOptionPane.showMessageDialog(null,
							e.getMessage(),
							"Greška", JOptionPane.INFORMATION_MESSAGE);					
				}
			});
		}
	}

	public void initMouseHoverAction(MouseEvent event, JTable testoviTable) {
		if (testoviTable.contains(event.getPoint()) 
				&& testoviTable.getModel().getRowCount() >= testoviTable.rowAtPoint(event.getPoint())
				&& testoviTable.columnAtPoint(event.getPoint()) == 2) {
			
			int row = testoviTable.rowAtPoint(event.getPoint());
			TestoviTableModel model = (TestoviTableModel) testoviTable.getModel();
			testoviTable.setToolTipText(model.getRowAt(row).getNapomena());
			
		} else {
			testoviTable.setToolTipText(null);
		}
	}
	// Stankovic begin//

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
			ChangeForm cf = new ChangeForm(this, null, getMainTable().getStudent(selectedRow[0]), selectedRow[0]);
			cf.setVisible(true);

		} else {
			final String message = "Odaberite samo jednog studenta za izmjenu!";
			JOptionPane.showMessageDialog(null, message, "Upozorenje", JOptionPane.WARNING_MESSAGE);
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

	public void resetFilterFormOpened() {
		filterFormOpened = false;
	}

	public void postaviMainForm(PredmetDTO activePredmet, PredmetDTO lastPredmet) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				initTestoviTable();
			}
		}).start();
		if (activePredmet != null) {
			UndoRedoData.saveState(mainForm.getNalogDTO(), lastPredmet);

			StudentsForMainTable.initShowInMainTable(activePredmet, mainForm.getNalogDTO());

			((DefaultTableModel) mainForm.getMainTable().getModel())
			.setColumnIdentifiers(new Object[] { "Indeks", "Ime", "Prezime" });
			ArrayList<StudentMainTableDTO> temp = StudentsForMainTable.initShowInMainTable(activePredmet,
					mainForm.getNalogDTO());

			mainForm.getMainTable().setStudents(temp);
			mainForm.getMainTable().changeView();
			showForm = new ShowForm(this);
		}else {
			getMainTable().setStudents(new ArrayList<StudentMainTableDTO>());
			getMainTable().changeView();
		}
	}

	public void prikaziDisciplinu(NalogDTO nalogDTO, JComboBox<String> disciplineCB) {
		
		if (disciplineCB.getItemCount() == 0) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					JOptionPane.showMessageDialog(mainForm, "Nemate pristup disciplini.");
				}
			});
		} else {

			String nazivDiscipline = (String) disciplineCB.getSelectedItem();
			DAOFactory dao = new MySQLDAOFactory();
			ElektrijadaDAO deDAO = dao.getElektrijadaDAO();
			ElektrijadaDTO elektrijadaDTO = deDAO.getElektrijadaZaNalogDTO(nalogDTO.getNalogId(), nazivDiscipline);
			DisciplinaDTO disciplinaDTO = new DisciplinaDTO(nazivDiscipline, elektrijadaDTO.getId());
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {

					mainForm.setVisible(false);
					ElektrijadaForm frame = new ElektrijadaForm(elektrijadaDTO, nalogDTO, disciplinaDTO, mainForm);
					frame.setVisible(true);
				}
			});
		}
	}

}
