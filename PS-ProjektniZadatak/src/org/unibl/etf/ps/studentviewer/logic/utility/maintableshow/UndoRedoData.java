package org.unibl.etf.ps.studentviewer.logic.utility.maintableshow;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLStudentMainTableDAO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class UndoRedoData {
	static int position = 0;
	static ArrayList<ArrayList<String>> stateList = new ArrayList<ArrayList<String>>();

	public static void initAddStudents(ArrayList<StudentMainTableDTO> students) {
		ArrayList<String> state = new ArrayList<String>();
		for (int ii = 0; ii < students.size(); ii++)
			state.add(students.get(ii).getBrojIndeksa());
		initAdd(state);
	}

	private static void initAdd(ArrayList<String> state) {
		stateList = new ArrayList<ArrayList<String>>();
		stateList.add(state);
	}

	public synchronized static void addState(ArrayList<StudentMainTableDTO> students) {
		if (position != stateList.size() - 1) {
			for (int i = position + 1; i < stateList.size(); i++)
				stateList.remove(i);
		}

		ArrayList<String> state = new ArrayList<String>();

		for (StudentMainTableDTO student : students) {
			state.add(student.getBrojIndeksa());
		}

		stateList.add(state);
		position++;
		if (position == 20) {
			position--;
			stateList.remove(0);
		}
	}

	public static ArrayList<StudentMainTableDTO> undo() {
		if (position == 0)
			return null;

		position--;
		ArrayList<StudentMainTableDTO> students = new ArrayList<StudentMainTableDTO>();
		ArrayList<String> state = stateList.get(position);

		for (String s : state) { // instead of i -> jmbg
			StudentMainTableDTO student = StudentsForMainTable.getByBrojIndeksa(s);
			if (student!=null)
			students.add(student);
		}

		return students;

	}

	public static ArrayList<StudentMainTableDTO> redo() {

		if (position == stateList.size() - 1)
			return null;

		position++;
		ArrayList<StudentMainTableDTO> students = new ArrayList<StudentMainTableDTO>();
		ArrayList<String> state = stateList.get(position);

		for (String s : state) { // instead of i -> jmbg
			StudentMainTableDTO student = StudentsForMainTable.getByBrojIndeksa(s);
			if (student!=null)
			students.add(student);
		}

		return students;

	}

	public static void saveState(NalogDTO nalogDTO, PredmetDTO selectedPredmet) {
		if (stateList.size() > 0) {
			StringBuilder sb = new StringBuilder("");
			for (ArrayList<String> helpList : stateList) {
				for (String s : helpList) {
					sb.append(s + "#");
				}
				sb.append("#");
			}
			new MySQLStudentMainTableDAO().setStateOfMainTable(selectedPredmet, nalogDTO, sb.toString());
		}
	}

	public static ArrayList<StudentMainTableDTO> loadFromBase(String stanjeTabele) {
		stateList = new ArrayList<ArrayList<String>>();
		String[] lists = stanjeTabele.split("##");
		for (String list : lists) {
			String elements[] = list.split("#");
			ArrayList<String> tempList = new ArrayList<String>();
			for (String s : elements)
				if (!"".equals(s))
					tempList.add(s);
			stateList.add(tempList);
		}
		
		position = lists.length - 1;
		
		ArrayList<String> temp = stateList.get(stateList.size() - 1);
		ArrayList<StudentMainTableDTO> temp2 = new ArrayList<StudentMainTableDTO>();
		for (String s : temp)
			for (StudentMainTableDTO st : StudentsForMainTable.getAllStudents())
				if (s.equals(st.getBrojIndeksa()))
					temp2.add(st);
		
		return temp2;
	}
}
