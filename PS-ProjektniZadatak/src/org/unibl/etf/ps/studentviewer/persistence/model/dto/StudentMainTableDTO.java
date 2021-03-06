package org.unibl.etf.ps.studentviewer.persistence.model.dto;

import java.util.HashMap;

import org.unibl.etf.ps.studentviewer.logic.utility.Sort;
import org.unibl.etf.ps.studentviewer.logic.utility.maintableshow.ShowViewData;

public class StudentMainTableDTO extends StudentNaPredmetuDTO{
	

	private int ocjena = 0;
	private String komentar = ""; // Mozda lista komentara
	private String elektrijada = "NE";
	private HashMap<String, String> testovi = new HashMap<String, String>();
	
	public void addIspit(String a, String b) {
		if (testovi.get(a) == null)
			testovi.put(a, b);
		else {
			testovi.remove(a);
			testovi.put(a, b);
		}
		
	}
	
	public int getOcjena() {
		return ocjena;
	}

	public void setOcjena(int ocjena) {
		this.ocjena = ocjena;
	}
	
	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public HashMap<String, String> getTestovi(){
		return testovi;
	}
	
	public void setTestovi(HashMap<String, String> testovi) {
		this.testovi = testovi;
	}
	

	public StudentMainTableDTO(String brojIndeksa, String ime, String prezime) {
		super();
		this.brojIndeksa = brojIndeksa;
		this.ime = ime;
		this.prezime = prezime;
	}
	
	public String getProperty(String type) {
		if (ShowViewData.D_BROJINDEKSA.equals(type) || Sort.BROJ_INDEKSA.equals(type))
			return brojIndeksa;
		
		if (ShowViewData.D_IME.equals(type) || Sort.IME.equals(type))
			return ime;
		
		if (ShowViewData.D_PREZIME.equals(type) || Sort.PREZIME.equals(type))
			return prezime;
		
		if (ShowViewData.D_ELEKTRIJADA.equals(type) || Sort.ELEKTRIJADA.equals(type))
			return elektrijada;
		
		if (ShowViewData.D_KOMENTAR.equals(type) || Sort.KOMENTAR.equals(type))
			return komentar;
		
		if (type.startsWith(ShowViewData.D_TEST) || type.startsWith(Sort.TEST)) 
			return (testovi.get(type.substring(7)) != null) ? testovi.get(type.substring(7)) : "/";
		
		if (ShowViewData.D_OCJENA.equals(type) || type.startsWith(Sort.OCJENA))
			return (ocjena != 0)?new Integer(ocjena).toString():"/";
			
		return null;
	}

	public String getTestForShowView(String test) {
		if (testovi.get(test) == null)
			return "/";
		return testovi.get(test);
	}
	
	public String getTest(String test) {
		return testovi.get(test);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + studentId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentMainTableDTO other = (StudentMainTableDTO) obj;
		if (brojIndeksa == null) {
			if (other.brojIndeksa != null)
				return false;
		} else if (!brojIndeksa.equals(other.brojIndeksa))
			return false;
		return true;
	}

	public String getElektrijada() {
		return elektrijada;
	}

	public void setElektrijada() {
		elektrijada = "DA";
		
	}
	
	public void resetElektrijada() {
		elektrijada = "NE";
	}

	public void resetExam(String exam) {
		testovi.remove(exam);
	}

	
	
	
	
}
