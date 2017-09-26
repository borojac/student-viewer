/**
 * @author dejan
 */
package org.unibl.etf.ps.studentviewer.logic.controller.student;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;

public class AddChangeStudentsHelpController {
	ArrayList<String> params;
	public AddChangeStudentsHelpController() {
	}
	
	public int checkParams(ArrayList<String> params) {
		
		String param1 = params.get(0);
		if (param1.length() < 2 || param1.length() > 20) return 1;
		char[] ime = param1.toCharArray();
		for (char c : ime) {
			if (!Character.isLetter(c))
				return 1; //pogresan unos imena
		}
		param1 = param1.substring(0, 1).toUpperCase() + param1.substring(1).toLowerCase();
		params.set(0, param1);
		

		String param2 = params.get(1);
		if (param2.length() < 2 || param1.length() > 30) return 2;
		char[] prezime = param2.toCharArray();
		for (char c : prezime) {
			if (!Character.isLetter(c))
				if(c != ' ' || c != '-')
				return 2; //pogresan unos prezimena
		}
		param2 = param2.substring(0, 1).toUpperCase() + param2.substring(1).toLowerCase();
		params.set(1, param2);

		String param3 = params.get(2);
		if(!param3.contains("/"))return 3; //mora biti '/' u broju indeksa
		
		char[] index = param3.toCharArray();
		int numOfSlashes = 0;
		for (char c : index) {
			if (!Character.isDigit(c))
				if (c == '/') {
					numOfSlashes++;
					if (numOfSlashes > 1)
						return 3; //vise od jedan '/' karakter u indeksu
				} else {
					return 3; //pogresan unos indeksu
				}
		}
		
		return 0; //ispravan unos
	}
	
	public int checkParams(StudentMainTableDTO student) {
		params = new ArrayList<>();
		params.add(student.getIme());
		params.add(student.getPrezime());
		params.add(student.getBrojIndeksa());
		//params.add(student.getKomentar());
		int retVal = checkParams(params);
		student.setIme(params.get(0));
		student.setPrezime(params.get(1));
		student.setBrojIndeksa(params.remove(2));
		return retVal;
	}
}
