package org.unibl.etf.ps.studentviewer.model;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.gui.UndoRedoData;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class StudentsForMainTable {
	private static ArrayList<StudentMainTableDTO> allStudents = null;

	static {
		allStudents = new ArrayList<StudentMainTableDTO>();
		allStudents.add(new StudentMainTableDTO("0001/14", "Sinisa", "Poletanovic", "0000"));
		allStudents.add(new StudentMainTableDTO("0004/14", "Ljubisa", "Peric", "0001"));
		allStudents.add(new StudentMainTableDTO("0003/13", "Petar", "Markanovic", "0002"));
		allStudents.add(new StudentMainTableDTO("0013/13", "Dejan", "Danilovic", "0003"));
		allStudents.add(new StudentMainTableDTO("0021/15", "Stojan", "Lekic", "0004"));

		ArrayList<String> state = new ArrayList<String>();
		for (int i = 0; i < allStudents.size(); i ++) 
			state.add(allStudents.get(i).getJmbg());
		UndoRedoData.initAdd(state);
	}
	
	public static StudentMainTableDTO getByJmbg(String jmbg) {
		for (StudentMainTableDTO s : allStudents)
			if (s.getJmbg().equals(jmbg))
				return s;
		return null;
	}

	public static ArrayList<StudentMainTableDTO> getAllStudents() {
		return allStudents;
	}
	
	
}
