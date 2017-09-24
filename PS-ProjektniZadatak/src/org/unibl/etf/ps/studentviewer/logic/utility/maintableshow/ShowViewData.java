package org.unibl.etf.ps.studentviewer.logic.utility.maintableshow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.unibl.etf.ps.studentviewer.model.StudentsForMainTable;

public class ShowViewData {
	static HashMap<String, Boolean> dataMap = new HashMap<String, Boolean>();
	private static HashMap<String, Boolean> DD_TEST = new HashMap<String, Boolean>();
	
	public static void setNewTestHashMap(HashMap<String, Boolean> newHashMap) {
		DD_TEST = newHashMap;
	}
	
	public static void setNewHashMap(HashMap<String, Boolean> newHashMap) {
		dataMap = newHashMap;
	}
	
	public static void setExam(String exam) {
		DD_TEST.remove(exam);
		DD_TEST.put(exam, true);
	}
	
	public static void resetExam(String exam) {
		DD_TEST.remove(exam);
		DD_TEST.put(exam, false);
	}
	
	
	public static void removeExam(String exam) {
		DD_TEST.remove(exam);
	}
	
	private static boolean checkTest() {
		Collection<Boolean> bools = DD_TEST.values();
		for (Boolean b : bools)
			if (b)
				return true;
		return false;
	}
	
	public static boolean getValue(String s) {
		if (s == null)
			return false;
		if (s.contains(".")) {
			return DD_TEST.get(s);}
		else if (D_TEST.equals(s))
			return checkTest();
		return dataMap.get(s);
	}
	
	public static String[] getSelectedExams() {
		Set<String> exams = DD_TEST.keySet();
		ArrayList<String> selectedExams = new ArrayList<String>();
		for (String s : exams)
			if (DD_TEST.get(s) == true)
				selectedExams.add(s);
		return selectedExams.toArray(new String[selectedExams.size()]);
	}
	
	public static String[] getUnselectedExams() {
		Set<String> exams = DD_TEST.keySet();
		ArrayList<String> unselectedExams = new ArrayList<String>();
		for (String s : exams)
			if (DD_TEST.get(s) == false)
				unselectedExams.add(s);
		
		return unselectedExams.toArray(new String[unselectedExams.size()]);
	}
	
	public static final String D_IME = "D_IME";
	public static final String D_PREZIME = "D_PREZIME";
	public static final String D_BROJINDEKSA = "D_BROJINDEKSA";
	public static final String D_ELEKTRIJADA = "D_ELEKTRIJADA";
	public static final String D_KOMENTAR = "D_KOMENTAR";
	public static final String D_TEST = "D_TEST";
	public static final String D_OCJENA = "D_OCJENA";
	
	
	
	static {
		for (String s : StudentsForMainTable.getAllIspiti())
			DD_TEST.put(s, false);
		
		dataMap.put(D_IME, true);
		dataMap.put(D_PREZIME, true);
		dataMap.put(D_BROJINDEKSA, true);
		dataMap.put(D_ELEKTRIJADA, false);
		dataMap.put(D_KOMENTAR, false);
		dataMap.put(D_OCJENA, false);
	}
}
