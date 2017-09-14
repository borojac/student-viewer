package org.unibl.etf.ps.studentviewer.logic.controller;

import java.util.ArrayList;

public class AddChangePredmetHelpController {
	
	public AddChangePredmetHelpController() {
		
	}
	
	public int checkParams(ArrayList<String> params) {
		
		String param1 = params.get(0);
		if(param1.length() < 4 || param1.length() > 5) {
			return 1;
		}
		char[] sifra = param1.toCharArray();
		for (char c : sifra) {
			if (!Character.isLetter(c) && !Character.isDigit(c)) {
				return 1; //pogresan unos sifre
			}
			if(Character.isLetter(c)) {
				Character.toUpperCase(c);
			}
		}
		param1 = String.valueOf(sifra);
		params.set(0, param1);
		
		String param2 = params.get(1);
		if(param2.length() < 2) {
			return 2;
		}
		char[] naziv = param2.toCharArray();
		for(char c : naziv) {
			if(!Character.isLetter(c) && !Character.isDigit(c)) {
				if(c != ' ' && c != '-') {
					return 2;
				}
			}
		}
		param2 = param2.substring(0, 1).toUpperCase() + param2.substring(1).toLowerCase();
		params.set(1, param2);
		
		String param3 = params.get(2);
		if(param3.length() < 1 || param3.length() > 2) {
			return 3;
		}
		char[] ects = param3.toCharArray();
		for(char c : ects) {
			if(!Character.isDigit(c)) {
				return 3;
			}
		}
		
		String param4 = params.get(3);
		if(param4.length() != 1) {
			return 4;
		}
		char[] semestar = param4.toCharArray();
		for(char c : semestar) {
			if(!Character.isDigit(c)) {
				return 4;
			}
		}
		
		String param5 = params.get(4);
		if(param5.length() != 1) {
			return 5;
		}
		if(!Character.isLetter(param5.charAt(0))) {
			return 5;
		}
		char ch = Character.toUpperCase(param5.charAt(0));
		if(ch != 'O' && ch != 'I') {
			return 5;
		}
		params.set(4, String.valueOf(ch));
		
		String param6 = params.get(5);
		if(param2.length() < 2) {
			return 6;
		}
		char[] nazivSP = param6.toCharArray();
		for(char c : nazivSP) {
			if(!Character.isLetter(c)) {
				if(c != ' ' && c != '-') {
					return 6;
				}
			}
		}
		param6 = param6.substring(0, 1).toUpperCase() + param6.substring(1).toLowerCase();
		params.set(5, param6);
		
		String param7 = params.get(6);
		if(param7.length() != 7) {
			return 7;
		}
		char[] skolskaGodina = param7.toCharArray();
		for(char c : skolskaGodina) {
			if(!Character.isDigit(c) && c != '/') {
				return 7;
			}
		}
		params.set(6, param7);
		
		String param8 = params.get(7);
		if(param8.length() != 1) {
			return 8;
		}
		char[] ciklus = param8.toCharArray();
		for(char c : ciklus) {
			if(!Character.isDigit(c)) {
				return 8;
			}
		}
		
		return 0;
	}

}
