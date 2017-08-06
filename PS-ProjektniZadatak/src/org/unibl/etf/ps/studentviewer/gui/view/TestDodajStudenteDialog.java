package org.unibl.etf.ps.studentviewer.gui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.unibl.etf.ps.studentviewer.gui.StudentListModel;
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
	private StudentListModel toAddModel = new StudentListModel();

	
	public TestDodajStudenteDialog(StudentListModel model) {
		setAlwaysOnTop(true);
		setBounds(100, 100, 700, 350);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		allStudentsList = new JList<>(model);
		allStudentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		allStudentsList.setBounds(10, 11, 290, 256);
		contentPanel.add(allStudentsList);
		
		toAddStudentsList = new JList<>(toAddModel);
		toAddStudentsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		toAddStudentsList.setBounds(384, 11, 290, 256);
		contentPanel.add(toAddStudentsList);
		
		btnAdd = new JButton(">");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Set<StudentNaTestuDTO> toAdd = new HashSet<>(((StudentListModel) toAddStudentsList.getModel()).getData());
				toAdd.addAll(allStudentsList.getSelectedValuesList());
				((StudentListModel) toAddStudentsList.getModel()).setData(new ArrayList<StudentNaTestuDTO>(toAdd));
			}
		});
		btnAdd.setBounds(320, 24, 50, 40);
		contentPanel.add(btnAdd);
		
		btnAddAll = new JButton(">>");
		btnAddAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Set<StudentNaTestuDTO> toAdd = new HashSet<>(((StudentListModel) toAddStudentsList.getModel()).getData());
				toAdd.addAll(((StudentListModel) allStudentsList.getModel()).getData());
				((StudentListModel) toAddStudentsList.getModel()).setData(new ArrayList<StudentNaTestuDTO>(toAdd));
			}
		});
		btnAddAll.setBounds(320, 75, 50, 40);
		contentPanel.add(btnAddAll);
		
		btnRemove = new JButton("<");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<StudentNaTestuDTO> toAdd = ((StudentListModel) toAddStudentsList.getModel()).getData();
				toAdd.removeAll(toAddStudentsList.getSelectedValuesList());
			}
		});
		btnRemove.setBounds(320, 156, 50, 40);
		contentPanel.add(btnRemove);
		
		btnRemoveAll = new JButton("<<");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((StudentListModel) toAddStudentsList.getModel()).getData().clear();
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
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
