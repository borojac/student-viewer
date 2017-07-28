package org.unibl.etf.ps.studentviewer.model.dto;

import java.util.Date;
import java.util.List;

public class TestDTO {

	private int testId;
	private String naziv;
	private Date datum;
	private String napomena;
	
	private List<StudentDTO> studenti;
	
	public TestDTO() {}

	public TestDTO(int testId, String naziv, Date datum, String napomena) {
		super();
		this.testId = testId;
		this.naziv = naziv;
		this.datum = datum;
		this.napomena = napomena;
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

	public List<StudentDTO> getStudenti() {
		return studenti;
	}

	public void setStudenti(List<StudentDTO> studenti) {
		this.studenti = studenti;
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
