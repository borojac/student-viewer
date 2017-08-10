package org.unibl.etf.ps.studentviewer.model.dto;

public class StudentNaTestuDTO {
	private int studentId;
	private String brojIndeksa, ime, prezime;
	private int brojBodova;
	private String komentar;
	
	public StudentNaTestuDTO() {}

	public StudentNaTestuDTO(int studentId, String brojIndeksa, String ime, String prezime, int brojBodova,
			String komentar) {
		super();
		this.studentId = studentId;
		this.brojIndeksa = brojIndeksa;
		this.ime = ime;
		this.prezime = prezime;
		this.brojBodova = brojBodova;
		this.komentar = komentar;
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

	public int getBrojBodova() {
		return brojBodova;
	}

	public void setBrojBodova(int brojBodova) {
		this.brojBodova = brojBodova;
	}

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
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
		StudentNaTestuDTO other = (StudentNaTestuDTO) obj;
		if (studentId != other.studentId)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return brojIndeksa + " " + ime + " " + prezime;
	}
	
}
