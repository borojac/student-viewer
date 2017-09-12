package org.unibl.etf.ps.studentviewer.model.dto;

import java.util.Date;

public class DodatnaNastavaDTO {
	private int nastavaId;
	private int elektrijadaId;
	private int nalogId;
	private String naziv;
	private Date datum;
	private String napomena;
	private String nazivTeme;

	public DodatnaNastavaDTO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "DodatnaNastavaDTO [naziv=" + naziv + ", datum=" + datum + ", napomena=" + napomena + ", nazivTeme="
				+ nazivTeme + "]";
	}

	public int getNastavaId() {
		return nastavaId;
	}

	public void setNastavaId(int nastavaId) {
		this.nastavaId = nastavaId;
	}

	public int getElektrijadaId() {
		return elektrijadaId;
	}

	public void setElektrijadaId(int elektrijadaId) {
		this.elektrijadaId = elektrijadaId;
	}

	public int getNalogId() {
		return nalogId;
	}

	public void setNalogId(int nalogId) {
		this.nalogId = nalogId;
	}

	public DodatnaNastavaDTO(int nastavaId, Date datum, String napomena, String nazivTeme, int nalogId, String naziv,
			int elektrijadaId) {
		super();
		this.nastavaId = nastavaId;
		this.elektrijadaId = elektrijadaId;
		this.nalogId = nalogId;
		this.naziv = naziv;
		this.datum = datum;
		this.napomena = napomena;
		this.nazivTeme = nazivTeme;
	}

	public String getNazivTeme() {
		return nazivTeme;
	}

	public void setNazivTeme(String nazivTeme) {
		this.nazivTeme = nazivTeme;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
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
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}

}
