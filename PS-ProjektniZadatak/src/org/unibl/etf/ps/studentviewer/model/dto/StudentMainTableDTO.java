package org.unibl.etf.ps.studentviewer.model.dto;

import org.unibl.etf.ps.studentviewer.gui.ShowViewData;

public class StudentMainTableDTO {
	private String brojIndeksa;
	private String ime;
	private String prezime;
	
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
		if (ShowViewData.D_BROJINDEKSA.equals(type))
			return brojIndeksa;
		
		if (ShowViewData.D_IME.equals(type))
			return ime;
		
		if (ShowViewData.D_PREZIME.equals(type))
			return prezime;
		
		return null;
	}
	
	
	
	
}
