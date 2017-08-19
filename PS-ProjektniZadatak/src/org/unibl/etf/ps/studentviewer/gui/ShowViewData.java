package org.unibl.etf.ps.studentviewer.gui;

import java.util.HashMap;

public class ShowViewData {
	static HashMap<String, Boolean> dataMap = new HashMap<String, Boolean>();

	public static void setNewHashMap(HashMap<String, Boolean> newHashMap) {
		dataMap = newHashMap;
		
	}
	
	public static boolean getValue(String s) {
		return dataMap.get(s);
	}
	
	public static final String D_IME = "D_IME";
	public static final String D_PREZIME = "D_PREZIME";
	public static final String D_BROJINDEKSA = "D_BROJINDEKSA";
	public static final String D_ELEKTRIJADA = "D_ELEKTRIJADA";
	public static final String D_KOMENTAR = "D_KOMENTAR";
	
	static {
		dataMap.put(D_IME, true);
		dataMap.put(D_PREZIME, true);
		dataMap.put(D_BROJINDEKSA, true);
		dataMap.put(D_ELEKTRIJADA, false);
		dataMap.put(D_KOMENTAR, false);
	}
}
