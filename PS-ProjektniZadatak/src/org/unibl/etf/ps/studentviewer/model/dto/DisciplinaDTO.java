package org.unibl.etf.ps.studentviewer.model.dto;

public class DisciplinaDTO {
	private String naziv;
	private int elektrijadaId;
	
	public DisciplinaDTO() {
		// TODO Auto-generated constructor stub
	}

	public DisciplinaDTO(String naziv, int elektrijadaId) {
		super();
		this.naziv = naziv;
		this.elektrijadaId = elektrijadaId;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getElektrijadaId() {
		return elektrijadaId;
	}

	public void setElektrijadaId(int elektrijadaId) {
		this.elektrijadaId = elektrijadaId;
	}
	
	
}
