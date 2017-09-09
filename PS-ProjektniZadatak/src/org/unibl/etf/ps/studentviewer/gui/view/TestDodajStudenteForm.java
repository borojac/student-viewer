package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.ps.studentviewer.gui.StudentListModel;
import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.gui.control.TestController;
import org.unibl.etf.ps.studentviewer.gui.control.TestDodajStudenteController;
import org.unibl.etf.ps.studentviewer.logic.command.Command;
import org.unibl.etf.ps.studentviewer.logic.command.DodajStudenteTestCommand;
import org.unibl.etf.ps.studentviewer.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.TestDAO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;

import javax.swing.JList;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JScrollPane;

public class TestDodajStudenteForm extends JDialog {

	private static final long serialVersionUID = -2611589723801370402L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnRemove;
	private JButton btnAddAll;
	private JButton btnAdd;
	private JButton btnRemoveAll;
	private JList<StudentNaTestuDTO> allStudentsList;
	private JList<StudentNaTestuDTO> toAddStudentsList;

	private JDialog thisDialog;
	
	private StudentTableModel tableModel;
	
	private TestForm testForm;
	private TestController testController;
	private TestDodajStudenteController dodajStudenteController;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	
	public TestDodajStudenteForm(TestForm testForm, TestController testController) {
		setAlwaysOnTop(true);
		setBounds(100, 100, 700, 350);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		contentPanel.setBackground(new Color(0, 0, 139));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		thisDialog = this;
		this.testForm = testForm;
		this.testController = testController;
		this.dodajStudenteController = new TestDodajStudenteController();
		this.tableModel = (StudentTableModel) testForm.getStudentiTable().getModel();
		
		DAOFactory factory = new MySQLDAOFactory();
		TestDAO testDAO = factory.getTestDAO();
		
		StudentListModel allStudentsListModel = new StudentListModel();

		List<StudentNaTestuDTO> data = testDAO.getStudentsOnPredmet(testController.getTest().getPredmetId());
		allStudentsListModel.setData(data);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 290, 256);
		contentPanel.add(scrollPane);
		
		allStudentsList = new JList<>(allStudentsListModel);
		scrollPane.setViewportView(allStudentsList);
		allStudentsList.setFont(new Font("Century Gothic", Font.BOLD, 12));
		allStudentsList.setForeground(new Color(0, 0, 139));
		allStudentsList.setBackground(new Color(173, 216, 230));
		allStudentsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					dodajStudenteController.addStudents(allStudentsList, toAddStudentsList);
				}
			}
		});
		allStudentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(384, 11, 290, 256);
		contentPanel.add(scrollPane_1);
		
		toAddStudentsList = new JList<>(new StudentListModel());
		scrollPane_1.setViewportView(toAddStudentsList);
		toAddStudentsList.setFont(new Font("Century Gothic", Font.BOLD, 12));
		toAddStudentsList.setForeground(new Color(0, 0, 139));
		toAddStudentsList.setBackground(new Color(173, 216, 230));
		toAddStudentsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					dodajStudenteController.removeStudents(allStudentsList, toAddStudentsList);
				}
			}
		});
		toAddStudentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		btnAdd = new JButton(">");
		btnAdd.setBackground(new Color(0, 0, 139));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dodajStudenteController.addStudents(allStudentsList, toAddStudentsList);
			}
		});
		btnAdd.setBounds(320, 24, 50, 40);
		contentPanel.add(btnAdd);
		
		btnAddAll = new JButton(">>");
		btnAddAll.setBackground(new Color(0, 0, 139));
		btnAddAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dodajStudenteController.addAll(allStudentsList, toAddStudentsList);
			}
		});
		btnAddAll.setBounds(320, 75, 50, 40);
		contentPanel.add(btnAddAll);
		
		btnRemove = new JButton("<");
		btnRemove.setBackground(new Color(0, 0, 139));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dodajStudenteController.removeStudents(allStudentsList, toAddStudentsList);
			}
		});
		btnRemove.setBounds(320, 156, 50, 40);
		contentPanel.add(btnRemove);
		
		btnRemoveAll = new JButton("<<");
		btnRemoveAll.setBackground(new Color(0, 0, 139));
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dodajStudenteController.removeAll(allStudentsList, toAddStudentsList);
			}
		});
		btnRemoveAll.setBounds(320, 207, 50, 40);
		contentPanel.add(btnRemoveAll);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(0, 0, 139));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setBackground(new Color(0, 0, 139));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						Set<StudentNaTestuDTO> studentsSet = new HashSet<>(tableModel.getData());
						studentsSet.addAll(((StudentListModel) toAddStudentsList.getModel()).getData());
						
						Command dodajStudente = new DodajStudenteTestCommand(
								testForm.getModel(),
								(StudentTableModel) testForm.getStudentiTable().getModel(), 
								new ArrayList<>(studentsSet));
						
						testController.executeCommand(dodajStudente);
						testForm.refreshStatistics();
						testForm.refreshStudentiTable();
						thisDialog.dispose();
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setBackground(new Color(0, 0, 139));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						thisDialog.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
