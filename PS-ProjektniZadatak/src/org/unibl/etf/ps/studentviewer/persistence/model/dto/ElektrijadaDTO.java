package org.unibl.etf.ps.studentviewer.persistence.model.dto;

import java.util.Date;

public class ElektrijadaDTO {

	private int id;
	private Date datum;
	private String lokacija;
	
	public ElektrijadaDTO() {
		// TODO Auto-generated constructor stub
	}

	public ElektrijadaDTO(int id, Date datum, String lokacija) {
		super();
		this.id = id;
		this.datum = datum;
		this.lokacija = lokacija;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getLokacija() {
		return lokacija;
	}

	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		ElektrijadaDTO other = (ElektrijadaDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ElektrijadaDTO [id=" + id + ", datum=" + datum + ", lokacija=" + lokacija + "]";
	}
	
	
}

