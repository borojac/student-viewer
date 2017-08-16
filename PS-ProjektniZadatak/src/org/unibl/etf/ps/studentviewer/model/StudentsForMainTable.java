package org.unibl.etf.ps.studentviewer.model;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class StudentsForMainTable {
	private static ArrayList<StudentMainTableDTO> allStudents = null;

	static {
		allStudents = new ArrayList<StudentMainTableDTO>();
		allStudents.add(new StudentMainTableDTO("0001/14", "Sinisa", "Poletanovic"));
		allStudents.add(new StudentMainTableDTO("0004/14", "Ljubisa", "Peric"));
		allStudents.add(new StudentMainTableDTO("0003/13", "Petar", "Markanovic"));
		allStudents.add(new StudentMainTableDTO("0013/13", "Dejan", "Danilovic"));
		allStudents.add(new StudentMainTableDTO("0021/15", "Stojan", "Lekic"));
	}

	public static ArrayList<StudentMainTableDTO> getAllStudents() {
		return allStudents;
	}
	
	
}
