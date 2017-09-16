package org.unibl.etf.ps.studentviewer.model.dto;

public class GradingInfoDTO {
	private TestDTO test;
	private int brojBodova;
	private String komentar;
	public GradingInfoDTO(TestDTO test, int brojBodova, String komentar) {
		super();
		this.test = test;
		this.brojBodova = brojBodova;
		this.komentar = komentar;
	}
	public TestDTO getTest() {
		return test;
	}
	public void setTest(TestDTO test) {
		this.test = test;
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
