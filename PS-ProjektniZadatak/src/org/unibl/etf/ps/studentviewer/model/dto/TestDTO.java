package org.unibl.etf.ps.studentviewer.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDTO {

	private int testId;
	private String naziv;
	private Date datum;
	private String napomena;
	private int procenat;
	private int predmetId;
	
	private List<StudentNaTestuDTO> studenti;
	
	public TestDTO() {
		naziv = napomena = "";
		datum = new Date();
		testId = 0;
		predmetId = 0;
		procenat = 50;
		studenti = new ArrayList<>();
	}

	public TestDTO(int testId, String naziv, Date datum, String napomena, int procenat, int predmetId) {
		this.testId = testId;
		this.naziv = naziv;
		this.datum = datum;
		this.napomena = napomena;
		this.procenat = procenat;
		this.predmetId = predmetId;
		studenti = new ArrayList<>();
	}

	public int getProcenat() {
		return procenat;
	}

	public void setProcenat(int procenat) {
		this.procenat = procenat;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}

	public List<StudentNaTestuDTO> getStudenti() {
		return studenti;
	}

	public void setStudenti(List<StudentNaTestuDTO> studenti) {
		this.studenti = studenti;
	}

	
	
	public int getPredmetId() {
		return predmetId;
	}

	public void setPredmetId(int predmetId) {
		this.predmetId = predmetId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + testId;
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
		TestDTO other = (TestDTO) obj;
		if (testId != other.testId)
			return false;
		return true;
	}
	
	
	
}
