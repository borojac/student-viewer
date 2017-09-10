package org.unibl.etf.ps.studentviewer.model.dto;

public class StudentNaPredmetuDTO {
	
	private int studentId;
	private String brojIndeksa;
	private String ime;
	private String prezime;
	
	public StudentNaPredmetuDTO() {
		studentId = 0;
		brojIndeksa = "";
		ime = "";
		prezime = "";
	}
	
	public StudentNaPredmetuDTO(int studentId, String brojIndeksa, String ime, String prezime) {
		this.studentId = studentId;
		this.brojIndeksa = brojIndeksa;
		this.ime = ime;
		this.prezime = prezime;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
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
		StudentNaPredmetuDTO other = (StudentNaPredmetuDTO) obj;
		if (studentId != other.studentId)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return brojIndeksa + " " + ime + " " + prezime;
	}

}
