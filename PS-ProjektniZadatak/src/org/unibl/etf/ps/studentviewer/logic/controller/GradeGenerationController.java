package org.unibl.etf.ps.studentviewer.logic.controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.gui.GradingTableModel;
import org.unibl.etf.ps.studentviewer.gui.MainTable;
import org.unibl.etf.ps.studentviewer.gui.view.GradeGenerationForm;
import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
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
	private List<? extends StudentNaPredmetuDTO> studentsForGrading;
	private ListIterator<? extends StudentNaPredmetuDTO> studentsForGradingIterator;
	private StudentNaPredmetuDTO currentStudent;
	private GradeGenerationForm gradeForm;

	public GradeGenerationController(GradeGenerationForm gradeForm, PredmetDTO predmet) {
		this.predmet = predmet;
		this.gradeForm = gradeForm;
	}

	public void loadStudentsForGrading(List<? extends StudentNaPredmetuDTO> studentsOnMainTable) {
		studentsForGrading =  studentsOnMainTable;
		studentsForGradingIterator = studentsForGrading.listIterator();
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
		gradeForm.setBodovi(totalPoints);
		gradeForm.setOcjena(grade);
	}


	public void loadStudentInfoNext(JButton btnDalje) {
		if (studentsForGradingIterator.hasNext()) {
			StudentNaPredmetuDTO student = studentsForGradingIterator.next();
			if (student == currentStudent)
				currentStudent = studentsForGradingIterator.next();
			else
				currentStudent = student;
			gradeForm.printStudent(currentStudent);
			new Thread(new Runnable() {

				@Override
				public void run() {
					DAOFactory factory = new MySQLDAOFactory();
					PredmetDAO predmetDAO = factory.getPredmetDAO();
					List<GradingInfoDTO> data = predmetDAO.getGradingInfo(
							currentStudent.getStudentId(), 
							predmet.getPredmetId()
							);
					gradeForm.getGradesTableModel().setData(data);
				}
			}).start();
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					if (!studentsForGradingIterator.hasNext())
						btnDalje.setEnabled(false);
					else if (!btnDalje.isEnabled())
						btnDalje.setEnabled(true);
				}
			});
		}
	}

	public void loadStudentInfoPrev(JButton btnNazad) {
		if (studentsForGradingIterator.hasPrevious()) {
			StudentNaPredmetuDTO student = studentsForGradingIterator.previous();
			if (student == currentStudent)
				currentStudent = studentsForGradingIterator.previous();
			else
				currentStudent = student;
			gradeForm.printStudent(currentStudent);
			new Thread(new Runnable() {

				@Override
				public void run() {
					DAOFactory factory = new MySQLDAOFactory();
					PredmetDAO predmetDAO = factory.getPredmetDAO();
					List<GradingInfoDTO> data = predmetDAO.getGradingInfo(
							currentStudent.getStudentId(), 
							predmet.getPredmetId()
							);
					gradeForm.getGradesTableModel().setData(data);
				}
			}).start();
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {

					if (!studentsForGradingIterator.hasPrevious())
						btnNazad.setEnabled(false);
					else if (!btnNazad.isEnabled())
						btnNazad.setEnabled(true);					
				}
			});
		}
	}

	public void gradeBtnAction(ActionEvent e) {
		DAOFactory factory = new MySQLDAOFactory();
		StudentDAO studentDAO = factory.getStudentDAO();
		int proceed = JOptionPane.YES_OPTION;
		if (studentDAO.hasGrade(currentStudent.getStudentId(),
				predmet.getPredmetId())) {
			String[] options = {"	Da	", "	Ne	"};
			proceed = JOptionPane.showOptionDialog(gradeForm, 
					"Dati student je već ocijenjen. Da li ste sigurni da želite da promjenite ocjenu?", 
					"Potvrdite ocijenjivanje",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);
		}
		if (proceed == JOptionPane.YES_OPTION) {
			final int ocjena = gradeForm.getOcjena();

			if (studentDAO.gradeStudent(currentStudent.getStudentId(),
					predmet.getPredmetId(), 
					ocjena) && gradeForm.getMainTable() != null) {
				System.out.println("Ocjena: " + ocjena);
				StudentsForMainTable.setOcjena(currentStudent.getStudentId(), 
						ocjena, 
						gradeForm.getMainTable());

			}

			loadStudentInfoNext(gradeForm.getBtnDalje());
		}
	}
}
