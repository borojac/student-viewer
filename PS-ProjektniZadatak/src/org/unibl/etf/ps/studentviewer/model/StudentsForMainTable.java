package org.unibl.etf.ps.studentviewer.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.unibl.etf.ps.studentviewer.gui.view.student.MainTable;
import org.unibl.etf.ps.studentviewer.logic.utility.maintableshow.ShowViewData;
import org.unibl.etf.ps.studentviewer.logic.utility.maintableshow.UndoRedoData;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLDAOFactory;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLStudentMainTableDAO;
import org.unibl.etf.ps.studentviewer.model.dao.StudentMainTableDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class StudentsForMainTable {
	private static ArrayList<StudentMainTableDTO> allStudents = null;
	private static String[] ispiti = { "22.11.2017.", "02.08.2016." };
	private static MainTable mainTable = null;
	
	public static void setMainTable(MainTable mainTable) {
		StudentsForMainTable.mainTable = mainTable;
	}

	private static void addTestToStudent(int id, String termin, int brojBodova) {
		for (StudentMainTableDTO s : allStudents) {
			if (s.getStudentId() == id) {
				s.addIspit(termin, new Integer(brojBodova).toString());
			}
		}
	}

	private static String getDateFormat(TestDTO t) {
		return new SimpleDateFormat("dd.MM.yyyy.").format(t.getDatum());
	}

	/* ADDING INITIAL SHOW IN MAIN TABLE */
	public static ArrayList<StudentMainTableDTO> initShowInMainTable(PredmetDTO predmetDTO, NalogDTO nalog) {
		// Preuzimanje svih studenata koji slusaju trenutni ispit
		StudentMainTableDAO dao = (MySQLStudentMainTableDAO) new MySQLDAOFactory().getStudentMainTableDAO();
		allStudents = dao.getAllStudentsOnPredmet(predmetDTO);
		// Preuzimanje svih testova sa trenutnog predmeta
		List<TestDTO> list = new MySQLDAOFactory().getTestDAO().getAllTests(predmetDTO.getPredmetId());

		for (TestDTO t : list) {
			List<StudentNaTestuDTO> listOfStudents = t.getStudenti();
			for (StudentNaTestuDTO student : listOfStudents) {
				int id = student.getStudentId();
				int brojBodova = student.getBrojBodova();
				addTestToStudent(id, getDateFormat(t), brojBodova);
			}
		}

		List<String> datumiIspita = new ArrayList<String>();
		for (TestDTO t : list) {
			String s = getDateFormat(t);
			datumiIspita.add(s);
		}

		ispiti = datumiIspita.toArray(new String[datumiIspita.size()]);

		// Kreiranje HashMap-e za ShowViewData
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		HashMap<String, Boolean> map2 = new HashMap<String, Boolean>();

		for (String s : ispiti)
			map2.put(s, false);

		map.put(ShowViewData.D_IME, true);
		map.put(ShowViewData.D_PREZIME, true);
		map.put(ShowViewData.D_BROJINDEKSA, true);
		map.put(ShowViewData.D_ELEKTRIJADA, false);
		map.put(ShowViewData.D_KOMENTAR, false);
		map.put(ShowViewData.D_OCJENA, false);

		ShowViewData.setNewHashMap(map);
		ShowViewData.setNewTestHashMap(map2);

		MainTable.setNewColumnIdentifiers(ispiti);

		String stanjeTabele = dao.getStateOfMainTable(predmetDTO, nalog);

		if (stanjeTabele == null) {
			ArrayList<StudentMainTableDTO> tempList = new ArrayList<>();
			for (StudentMainTableDTO s : StudentsForMainTable.getAllStudents())
				tempList.add(s);
			UndoRedoData.initAddStudents(tempList);
			return tempList;
		} else {
			return UndoRedoData.loadFromBase(stanjeTabele);
		}
	}

	public static StudentMainTableDTO getByBrojIndeksa(String brojIndeksa) {
		for (StudentMainTableDTO s : allStudents)
			if (s.getBrojIndeksa().equals(brojIndeksa))
				return s;
		return null;
	}

	public static String[] getAllIspiti() {
		return ispiti;
	}

	public static ArrayList<StudentMainTableDTO> getAllStudents() {
		return allStudents;
	}

	public static void setTest(TestDTO test, MainTable mainTable, boolean forDelete) {

		List<String> examList = new ArrayList<String>();
		for (String s : ispiti)
			examList.add(s);

		if (forDelete) {
			examList.remove(getDateFormat(test));
			ispiti = examList.toArray(new String[examList.size()]);
			MainTable.setNewColumnIdentifiers(ispiti);
			ShowViewData.removeExam(getDateFormat(test));
			for (StudentMainTableDTO student : getAllStudents()) {
				student.resetExam(getDateFormat(test));
			}
			
		} else {
			if (!examList.contains(getDateFormat(test))) {
				examList.add(getDateFormat(test));
				ispiti = examList.toArray(new String[examList.size()]);
				ShowViewData.resetExam(getDateFormat(test));
				MainTable.setNewColumnIdentifiers(ispiti);
			}

			List<StudentNaTestuDTO> listOfStudents = test.getStudenti();
			for (StudentNaTestuDTO student : listOfStudents) {
				int id = student.getStudentId();
				int brojBodova = student.getBrojBodova();
				addTestToStudent(id, getDateFormat(test), brojBodova);
			}

			for (StudentMainTableDTO student : getAllStudents()) {
				boolean control = false;
				if (!"/".equals(student.getTest(getDateFormat(test)))) {
					for (StudentNaTestuDTO studentNaTestu : listOfStudents) {
						if (studentNaTestu.getStudentId() == student.getStudentId()) {
							control = true;
							break;
						}
					}
				}
				if (!control) {
					student.resetExam(getDateFormat(test));
				}
			}
			
			

		}

		mainTable.setStudents(mainTable.getStudents());
		mainTable.changeView();
	}
	
	public static void setOcjena(int id, int ocjena) {
		for (StudentMainTableDTO student : getAllStudents())
			if (student.getStudentId() == id) {
				student.setOcjena(ocjena);
				break;
			}
		mainTable.setStudents(mainTable.getStudents());
		mainTable.changeView();
	}

}
