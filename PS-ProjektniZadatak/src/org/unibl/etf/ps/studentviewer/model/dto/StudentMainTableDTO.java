package org.unibl.etf.ps.studentviewer.model.dto;

import java.util.Date;
import java.util.HashMap;

import org.unibl.etf.ps.studentviewer.gui.ShowViewData;
import org.unibl.etf.ps.studentviewer.logic.utility.Sort;

public class StudentMainTableDTO {
	private String brojIndeksa;
	private String ime;
	private String prezime;
	private String komentar = "none"; // Mozda lista komentara
	private String elektrijada = "NE";
	private HashMap<String, String> testovi = new HashMap<String, String>();
	
	
	
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
	
	public String getBrojIndeksa() {
		return brojIndeksa;
	}

	public void setBrojIndeksa(String brojIndeksa) {
		this.brojIndeksa = brojIndeksa;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
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
			return (testovi.get(type.substring(7)) != null) ? testovi.get(type.substring(7)) : "none";
		
		return null;
	}

	public String getTest(String ispit) {
		return testovi.get(ispit);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brojIndeksa == null) ? 0 : brojIndeksa.hashCode());
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
	
	
	
	
}
