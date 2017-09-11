package org.unibl.etf.ps.studentviewer.model.dto;

import java.io.Serializable;

public class DodatnaNastavaDTO implements Serializable {
	private String naziv;
	private String datum;
	private String napomena;

	public DodatnaNastavaDTO() {
		// TODO Auto-generated constructor stub
		this.naziv = this.napomena = "#";
		this.datum = null;
	}

	public DodatnaNastavaDTO(String naziv, String datum, String napomena) {
		super();
		this.naziv = naziv;
		this.datum = datum;
		this.napomena = napomena;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getNapomena() {
		return napomena;
	}

	public void setNapomena(String napomena) {
		this.napomena = napomena;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
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
		DodatnaNastavaDTO other = (DodatnaNastavaDTO) obj;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}
}
