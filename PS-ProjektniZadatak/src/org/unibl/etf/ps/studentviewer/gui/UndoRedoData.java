package org.unibl.etf.ps.studentviewer.gui;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class UndoRedoData {
	static int position = 0;
	static ArrayList<ArrayList<String>> stateList = new ArrayList<ArrayList<String>>();

	public static void initAdd(ArrayList<String> state) {
		stateList.add(state);
	}
	
	public synchronized static void addState(ArrayList<StudentMainTableDTO> students) {
		if (position != stateList.size() -1){ 
			for (int i = position + 1; i < stateList.size(); i ++)
				stateList.remove(i);
		}
		
		ArrayList<String> state = new ArrayList<String>();
		
		for (StudentMainTableDTO student : students) {
			state.add(student.getJmbg());
		}
	
		stateList.add(state);
		position ++;
		if (position == 20) {
			position --;
			stateList.remove(0);
		}
	}
	
	public static ArrayList<StudentMainTableDTO> undo(){
		if (position == 0)
			return null;
		
		position --;
		ArrayList<StudentMainTableDTO> students = new ArrayList<StudentMainTableDTO>();
		ArrayList<String> state = stateList.get(position);
		
		for (String s : state) { // instead of i -> jmbg
			students.add(StudentsForMainTable.getByJmbg(s));
		}
		
		return students;
		
	}
	
	public static ArrayList<StudentMainTableDTO> redo(){
		
		if (position == stateList.size() - 1)
			return null;
		
		position ++;
		ArrayList<StudentMainTableDTO> students = new ArrayList<StudentMainTableDTO>();
		ArrayList<String> state = stateList.get(position);
		
		for (String s : state) { // instead of i -> jmbg
			students.add(StudentsForMainTable.getByJmbg(s));
		}
		
		return students;
		
	}
}
