package org.unibl.etf.ps.studentviewer.model.dto;

import java.io.Serializable;

public class StudentZaElektrijaduDTO implements Serializable {
	private String indeks;
	private String ime;
	private String prezime;
	private String napomena;

	public StudentZaElektrijaduDTO() {
		indeks = ime = prezime = napomena = "#";
	}

	public StudentZaElektrijaduDTO(String indeks, String ime, String prezime, String napomena) {
		super();
		this.indeks = indeks;
		this.ime = ime;
		this.prezime = prezime;
		this.napomena = napomena;
	}

	public String getIndeks() {
		return indeks;
	}

	public void setIndeks(String indeks) {
		this.indeks = indeks;
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
		result = prime * result + ((indeks == null) ? 0 : indeks.hashCode());
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
		StudentZaElektrijaduDTO other = (StudentZaElektrijaduDTO) obj;
		if (indeks == null) {
			if (other.indeks != null)
				return false;
		} else if (!indeks.equals(other.indeks))
			return false;
		return true;
	}

}
