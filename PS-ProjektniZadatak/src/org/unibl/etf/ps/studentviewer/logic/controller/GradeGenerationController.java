package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.gui.GradingTableModel;
import org.unibl.etf.ps.studentviewer.gui.view.GradeGenerationForm;
import org.unibl.etf.ps.studentviewer.model.dao.DAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.PredmetDAO;
import org.unibl.etf.ps.studentviewer.model.dao.StudentDAO;
import org.unibl.etf.ps.studentviewer.model.dto.GradingInfoDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class GradeGenerationController {

	private PredmetDTO predmet;
	private List<StudentNaPredmetuDTO> studentsForGrading;
	private StudentNaPredmetuDTO currentStudent;
	private GradeGenerationForm gradeForm;
	
	public GradeGenerationController(GradeGenerationForm gradeForm, PredmetDTO predmet) {
		this.predmet = predmet;
		this.gradeForm = gradeForm;
	}
	
	public void loadStudentsForGrading() {
		DAOFactory factory = new MySQLDAOFactory();
		PredmetDAO predmetDAO = factory.getPredmetDAO();
		
		studentsForGrading = predmetDAO.getStudentsForGrading(predmet.getPredmetId());
		
	}
	
	public void calculateGrade() {
		double totalPoints = 0.0;
		int grade = 5;
		int[] selectedIndexes = gradeForm.getGradesTable().getSelectedRows();
		
		GradingTableModel model = gradeForm.getGradesTableModel();
		for (int index : selectedIndexes) {
			GradingInfoDTO info = model.getDataAt(index);
			double points = (double)info.getBrojBodova() * (double)info.getTest().getProcenat() / 100.0;
			totalPoints += points;
		}
		if (totalPoints > 90)
			grade = 10;
		else if (totalPoints > 80)
			grade = 9;
		else if (totalPoints > 70)
			grade = 8;
		else if (totalPoints > 60)
			grade = 7;
		else if (totalPoints > 50)
			grade = 6;
		else
			grade = 5;
		gradeForm.setOcjena(grade);
	}
	
	
	
	public void loadStudentInfo() {
		currentStudent = studentsForGrading.remove(0);
		gradeForm.printStudent(currentStudent);
		DAOFactory factory = new MySQLDAOFactory();
		PredmetDAO predmetDAO = factory.getPredmetDAO();
		List<GradingInfoDTO> data = predmetDAO.getGradingInfo(
				currentStudent.getStudentId(), 
				predmet.getPredmetId()
				);
		gradeForm.getGradesTableModel().setData(data);
	}
	
	public void gradeBtnAction(ActionEvent e) {
		DAOFactory factory = new MySQLDAOFactory();
		StudentDAO studentDAO = factory.getStudentDAO();
		studentDAO.gradeStudent(currentStudent.getStudentId(),
				predmet.getPredmetId(), 
				gradeForm.getOcjena());
		
		loadStudentInfo();
	}
}
