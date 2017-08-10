package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.ps.studentviewer.gui.StudentListModel;
import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
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

public class TestDodajStudenteDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnRemove;
	private JButton btnAddAll;
	private JButton btnAdd;
	private JButton btnRemoveAll;
	private JList<StudentNaTestuDTO> allStudentsList;
	private JList<StudentNaTestuDTO> toAddStudentsList;

	private JDialog thisDialog;
	
	private StudentTableModel tableModel;
	
	public TestDodajStudenteDialog(StudentTableModel tableModel) {
		setAlwaysOnTop(true);
		setBounds(100, 100, 700, 350);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		thisDialog = this;
		this.tableModel = tableModel;
		
		DAOFactory factory = new MySQLDAOFactory();
//		 TODO - cekam StudentDAO da povadim studente na testu
//		StudentDAO studentDAO = factory.getStudentDAO();
		
		StudentListModel allStudentsListModel = new StudentListModel();
		
		
		List<StudentNaTestuDTO> data = new ArrayList<>();
		
		data.add(new StudentNaTestuDTO(1, "1145/14", "Nemanja", "Stokuca", 0, null));
		data.add(new StudentNaTestuDTO(2, "1141/13", "Dragana", "Volas", 0, null));
		
		allStudentsListModel.setData(data);
		
		allStudentsList = new JList<>(allStudentsListModel);
		allStudentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		allStudentsList.setBounds(10, 11, 290, 256);
		contentPanel.add(allStudentsList);
		
		toAddStudentsList = new JList<>(new StudentListModel());
		toAddStudentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		toAddStudentsList.setBounds(384, 11, 290, 256);
		contentPanel.add(toAddStudentsList);
		
		btnAdd = new JButton(">");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(toAddStudentsList.getModel().getClass().getName());
				List<StudentNaTestuDTO> selectedStudents = allStudentsList.getSelectedValuesList();
				StudentListModel model = (StudentListModel) toAddStudentsList.getModel();
				List<StudentNaTestuDTO> toAddList = model.getData();
				for (StudentNaTestuDTO student : selectedStudents) {
					if (!toAddList.contains(student))
						toAddList.add(student);
				}
				
				model.setData(toAddList);
				model.fireContentsChanged(this, 0, toAddList.size() - 1);
				
				// TODO - da se lista svih studenata mijenja kad se dodaju
				
			}
		});
		btnAdd.setBounds(320, 24, 50, 40);
		contentPanel.add(btnAdd);
		
		btnAddAll = new JButton(">>");
		btnAddAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StudentListModel model = (StudentListModel) toAddStudentsList.getModel();
				Set<StudentNaTestuDTO> toAdd = new HashSet<>(model.getData());
				toAdd.addAll(((StudentListModel) allStudentsList.getModel()).getData());
				model.setData(new ArrayList<StudentNaTestuDTO>(toAdd));
				
				model.fireContentsChanged(this, 0, toAdd.size() - 1);
			}
		});
		btnAddAll.setBounds(320, 75, 50, 40);
		contentPanel.add(btnAddAll);
		
		btnRemove = new JButton("<");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StudentListModel model = ((StudentListModel) toAddStudentsList.getModel());
				List<StudentNaTestuDTO> toAdd = model.getData();
				toAdd.removeAll(toAddStudentsList.getSelectedValuesList());
				model.fireContentsChanged(this, 0, toAdd.size() - 1);
			}
		});
		btnRemove.setBounds(320, 156, 50, 40);
		contentPanel.add(btnRemove);
		
		btnRemoveAll = new JButton("<<");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StudentListModel model = ((StudentListModel) toAddStudentsList.getModel());
				model.getData().clear();
				model.fireContentsChanged(this, 0, 0);
			}
		});
		btnRemoveAll.setBounds(320, 207, 50, 40);
		contentPanel.add(btnRemoveAll);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// TODO - napraviti cuvanje izmjena kao komandu
						
						Set<StudentNaTestuDTO> studentsSet = new HashSet<>(tableModel.getData());
						studentsSet.addAll(((StudentListModel) toAddStudentsList.getModel()).getData());
						
						tableModel.setData(new ArrayList<>(studentsSet));
						tableModel.fireTableDataChanged();
						
						thisDialog.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
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
