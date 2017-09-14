package org.unibl.etf.ps.studentviewer.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.unibl.etf.ps.studentviewer.gui.UndoRedoData;
import org.unibl.etf.ps.studentviewer.model.dao.MySQLStudentMainTableDAO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class StudentsForMainTable {
	private static ArrayList<StudentMainTableDTO> allStudents = null;
	private static String[] ispiti = {"22.11.2017.", "02.08.2016."};
	
	/* ADDING INITIAL SHOW IN MAIN TABLE */
	static {
		allStudents = new ArrayList<StudentMainTableDTO>();
		try {
			BufferedReader in = new BufferedReader(new FileReader("data.txt"));
			String line = null;
			int i = 0;
			allStudents = new MySQLStudentMainTableDAO().getAllStudents();
			for(StudentMainTableDTO student : allStudents) {
				HashMap<String, String> testovi = new HashMap<String, String>();
				testovi.put("22.11.2017.", new Integer(100 - i).toString());
				testovi.put("02.08.2016.", new Integer(i).toString());

				student.setTestovi(testovi);
				
//				String komentar = "nekakav komentar " + i;
//				if (i % 3 == 0)
//					komentar = "";
//				student.setKomentar(komentar);
				if ( i % 2 == 0)
					student.setElektrijada();
				else
					student.resetElektrijada();
				
				i++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> state = new ArrayList<String>();
		for (int i = 0; i < allStudents.size(); i ++) 
			state.add(allStudents.get(i).getBrojIndeksa());
		UndoRedoData.initAdd(state);
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
	
	
}
