package org.unibl.etf.ps.studentviewer.model.dto;

public class PredmetDTO {
	
	private int predmetId;
	private String sifraPredmeta;
	private String nazivPredmeta;
	private short ects;
	private short semestar;
	private char tipPredmeta;
	
	public PredmetDTO() {
		predmetId = 0;
		sifraPredmeta = "";
		nazivPredmeta = "";
		ects = 0;
		semestar = 0;
		tipPredmeta = 'o';
	}
	
	public PredmetDTO(int predmetId, String sifraPredmeta, String nazivPredmeta, short ects, short semestar, char tipPredmeta) {
		this.predmetId = predmetId;
		this.sifraPredmeta = sifraPredmeta;
		this.nazivPredmeta = nazivPredmeta;
		this.ects = ects;
		this.semestar = semestar;
		this.tipPredmeta = tipPredmeta;
	}

	public int getPredmetId() {
		return predmetId;
	}

	public void setPredmetId(int predmetId) {
		this.predmetId = predmetId;
	}

	public String getSifraPredmeta() {
		return sifraPredmeta;
	}

	public void setSifraPredmeta(String sifraPredmeta) {
		this.sifraPredmeta = sifraPredmeta;
	}

	public String getNazivPredmeta() {
		return nazivPredmeta;
	}

	public void setNazivPredmeta(String nazivPredmeta) {
		this.nazivPredmeta = nazivPredmeta;
	}

	public short getEcts() {
		return ects;
	}

	public void setEcts(short ects) {
		this.ects = ects;
	}

	public short getSemestar() {
		return semestar;
	}

	public void setSemestar(short semestar) {
		this.semestar = semestar;
	}

	public char getTipPredmeta() {
		return tipPredmeta;
	}

	public void setTipPredmeta(char tipPredmeta) {
		this.tipPredmeta = tipPredmeta;
	}

}
