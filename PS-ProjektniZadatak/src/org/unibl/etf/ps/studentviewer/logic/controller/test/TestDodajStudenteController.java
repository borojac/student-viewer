package org.unibl.etf.ps.studentviewer.logic.controller.test;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JList;

import org.unibl.etf.ps.studentviewer.gui.view.test.StudentListModel;
import org.unibl.etf.ps.studentviewer.gui.view.test.StudentTableModel;
import org.unibl.etf.ps.studentviewer.gui.view.test.TestDodajStudenteForm;
import org.unibl.etf.ps.studentviewer.gui.view.test.TestForm;
import org.unibl.etf.ps.studentviewer.logic.utility.command.Command;
import org.unibl.etf.ps.studentviewer.logic.utility.command.DodajStudenteTestCommand;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;

public class TestDodajStudenteController {
	private TestDodajStudenteForm testDodajStudenteForm;
	
	
	
	public TestDodajStudenteController(TestDodajStudenteForm testDodajStudenteForm) {
		super();
		this.testDodajStudenteForm = testDodajStudenteForm;
	}

	public void addStudents(JList<StudentNaTestuDTO> allStudentsList, JList<StudentNaTestuDTO> toAddStudentsList) {
		List<StudentNaTestuDTO> selectedStudents = allStudentsList.getSelectedValuesList();
		StudentListModel toAddModel = (StudentListModel) toAddStudentsList.getModel();
		StudentListModel allStudentsModel = (StudentListModel) allStudentsList.getModel();
		List<StudentNaTestuDTO> toAddList = toAddModel.getData();
		for (StudentNaTestuDTO student : selectedStudents) {
			if (!toAddList.contains(student))
				toAddList.add(student);
		}
		
		toAddModel.setData(toAddList);
		allStudentsModel.getData().removeAll(toAddList);
		toAddModel.fireContentsChanged(this, 0, toAddList.size() - 1);
		allStudentsModel.fireContentsChanged(this, 0, allStudentsModel.getSize() - 1);
		
	}
	
	public void addAll(JList<StudentNaTestuDTO> allStudentsList, JList<StudentNaTestuDTO> toAddStudentsList) {
		StudentListModel toAddModel = (StudentListModel) toAddStudentsList.getModel();
		StudentListModel allStudentsModel = (StudentListModel) allStudentsList.getModel();
		Set<StudentNaTestuDTO> toAdd = new HashSet<>(toAddModel.getData());
		toAdd.addAll(((StudentListModel) allStudentsList.getModel()).getData());
		toAddModel.setData(new ArrayList<StudentNaTestuDTO>(toAdd));
		
		toAddModel.fireContentsChanged(this, 0, toAdd.size() - 1);
		allStudentsModel.getData().clear();
		allStudentsModel.fireContentsChanged(this, 0, allStudentsModel.getSize() - 1);
	}
	
	public void removeStudents(JList<StudentNaTestuDTO> allStudentsList, JList<StudentNaTestuDTO> toAddStudentsList) {
		StudentListModel toAddModel = ((StudentListModel) toAddStudentsList.getModel());
		StudentListModel allStudentsModel = (StudentListModel) allStudentsList.getModel();
		List<StudentNaTestuDTO> toAdd = toAddModel.getData();
		allStudentsModel.getData().addAll(toAddStudentsList.getSelectedValuesList());
		toAdd.removeAll(toAddStudentsList.getSelectedValuesList());
		toAddModel.fireContentsChanged(this, 0, toAdd.size() - 1);
		allStudentsModel.fireContentsChanged(this, 0, allStudentsModel.getSize() - 1);
	}
	
	public void removeAll(JList<StudentNaTestuDTO> allStudentsList, JList<StudentNaTestuDTO> toAddStudentsList) {
		StudentListModel toAddModel = ((StudentListModel) toAddStudentsList.getModel());
		StudentListModel allStudentsModel = (StudentListModel) allStudentsList.getModel();
		allStudentsModel.getData().addAll(toAddModel.getData());
		toAddModel.getData().clear();
		toAddModel.fireContentsChanged(this, 0, 0);
		allStudentsModel.fireContentsChanged(this, 0, allStudentsModel.getSize() - 1);
	}
	
	public void okButtonAction(TestForm testForm, TestController testController, StudentTableModel tableModel, JList<StudentNaTestuDTO> toAddStudentsList) {
		Set<StudentNaTestuDTO> studentsSet = new HashSet<>(testController.getTest().getStudenti());
		studentsSet.addAll(((StudentListModel) toAddStudentsList.getModel()).getData());
		
		Command dodajStudente = new DodajStudenteTestCommand(
				testController.getTest(),
				(StudentTableModel) testForm.getStudentiTable().getModel(), 
				new ArrayList<>(studentsSet));
		
		testController.executeCommand(dodajStudente);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				testForm.refreshStatistics();
				testForm.refreshStudentiTable();				
			}
		}).start();

		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				testDodajStudenteForm.dispose();
			}
		});
	}
}
