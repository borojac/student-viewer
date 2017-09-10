package org.unibl.etf.ps.studentviewer.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.gui.UndoRedoData;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class StudentsForMainTable {
	private static ArrayList<StudentMainTableDTO> allStudents = null;

	static {
		allStudents = new ArrayList<StudentMainTableDTO>();
		try {
			BufferedReader in = new BufferedReader(new FileReader("data.txt"));
			String line = null;
			int i = 0;
			while((line = in.readLine()) != null) {
				i++;
				String ime = line.split(" ")[0];
				String prezime = line.split(" ")[1];
				allStudents.add(new StudentMainTableDTO(i + "/14", ime, prezime, i+""));
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
