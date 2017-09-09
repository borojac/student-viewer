package org.unibl.etf.ps.studentviewer.model.dto;

import java.util.Date;
import java.util.HashMap;

import org.unibl.etf.ps.studentviewer.gui.ShowViewData;
import org.unibl.etf.ps.studentviewer.logic.utility.Sort;

public class StudentMainTableDTO {
	private String brojIndeksa;
	private String ime;
	private String prezime;
	private String jmbg;
	private String komentar = ""; // Mozda lista komentara
	private String elektrijada = "NE";
	private HashMap<Date, String> ispiti  = new HashMap<Date, String>();
	private HashMap<Date, String> kolokvijumi = new HashMap<Date, String>();
	private HashMap<Date, String> usmeniIspiti = new HashMap<Date, String>();
	
	
	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
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

	public StudentMainTableDTO(String brojIndeksa, String ime, String prezime, String jmbg) {
		super();
		this.brojIndeksa = brojIndeksa;
		this.ime = ime;
		this.prezime = prezime;
		this.jmbg = jmbg;
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
		
		return null;
	}
	
	
	
	
}
