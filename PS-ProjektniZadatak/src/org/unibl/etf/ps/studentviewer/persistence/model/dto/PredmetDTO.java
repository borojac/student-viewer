package org.unibl.etf.ps.studentviewer.persistence.model.dto;

import java.util.ArrayList;

public class PredmetDTO {
	
	private int predmetId;
	private String sifraPredmeta;
	private String nazivPredmeta;
	private short ects;
	private short semestar;
	private char tipPredmeta;
	private String nazivSP;
	private String skolskaGodina;
	private short ciklus;
	
	private ArrayList<StudentNaPredmetuDTO> studenti;
	private ArrayList<TestDTO> testovi;
	
	public PredmetDTO() {
		predmetId = 0;
		sifraPredmeta = "";
		nazivPredmeta = "";
		ects = 0;
		semestar = 0;
		tipPredmeta = 'o';
		nazivSP = "";
		skolskaGodina = "";
		ciklus = 0;
		studenti = new ArrayList<>();
		testovi = new ArrayList<>();
	}
	
	public PredmetDTO(int predmetId, String sifraPredmeta, String nazivPredmeta, short ects, short semestar, char tipPredmeta, String nazivSP, String skolskaGodina, short ciklus) {
		this.predmetId = predmetId;
		this.sifraPredmeta = sifraPredmeta;
		this.nazivPredmeta = nazivPredmeta;
		this.ects = ects;
		this.semestar = semestar;
		this.tipPredmeta = tipPredmeta;
		this.nazivSP = nazivSP;
		this.skolskaGodina = skolskaGodina;
		this.ciklus = ciklus;
		studenti = new ArrayList<>();
		testovi = new ArrayList<>();
	}
	
	public PredmetDTO(String sifraPredmeta, String nazivPredmeta, short ects, short semestar, char tipPredmeta, String nazivSP, String skolskaGodina, short ciklus) {
		predmetId = 0;
		this.sifraPredmeta = sifraPredmeta;
		this.nazivPredmeta = nazivPredmeta;
		this.ects = ects;
		this.semestar = semestar;
		this.tipPredmeta = tipPredmeta;
		this.nazivSP = nazivSP;
		this.skolskaGodina = skolskaGodina;
		this.ciklus = ciklus;
		studenti = new ArrayList<>();
		testovi = new ArrayList<>();
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

	public String getNazivSP() {
		return nazivSP;
	}

	public void setNazivSP(String nazivSP) {
		this.nazivSP = nazivSP;
	}

	public String getSkolskaGodina() {
		return skolskaGodina;
	}

	public void setSkolskaGodina(String skolskaGodina) {
		this.skolskaGodina = skolskaGodina;
	}

	public short getCiklus() {
		return ciklus;
	}

	public void setCiklus(short ciklus) {
		this.ciklus = ciklus;
	}

	public ArrayList<StudentNaPredmetuDTO> getStudenti() {
		return studenti;
	}

	public void setStudenti(ArrayList<StudentNaPredmetuDTO> studenti) {
		this.studenti = studenti;
	}

	public ArrayList<TestDTO> getTestovi() {
		return testovi;
	}

	public void setTestovi(ArrayList<TestDTO> testovi) {
		this.testovi = testovi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + predmetId;
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
		PredmetDTO other = (PredmetDTO) obj;
		if (predmetId != other.predmetId)
			return false;
		return true;
	}

}
