package org.unibl.etf.ps.studentviewer.model.dto;

public class StudentNaTestuDTO extends StudentNaPredmetuDTO {
	private int brojBodova;
	private String komentar;
	
	public StudentNaTestuDTO() {
		super();
		brojBodova = 0;
		komentar = "";
	}

	public StudentNaTestuDTO(int studentId, String brojIndeksa, String ime, String prezime, 
			int brojBodova, String komentar) {
		super(studentId, brojIndeksa, ime ,prezime);
		this.brojBodova = brojBodova;
		this.komentar = komentar;
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
	
}
