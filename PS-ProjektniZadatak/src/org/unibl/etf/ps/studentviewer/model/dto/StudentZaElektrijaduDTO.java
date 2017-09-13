package org.unibl.etf.ps.studentviewer.model.dto;

public class StudentZaElektrijaduDTO {
	private int id;
	private String indeks;
	private String ime;
	private String prezime;
	private String komentar;

	public StudentZaElektrijaduDTO() {

	}

	public StudentZaElektrijaduDTO(int id, String indeks, String ime, String prezime, String komentar) {
		super();
		this.id = id;
		this.indeks = indeks;
		this.ime = ime;
		this.prezime = prezime;
		this.komentar = komentar;
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
		return komentar;
	}

	public void setNapomena(String komentar) {
		this.komentar = komentar;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		StudentZaElektrijaduDTO other = (StudentZaElektrijaduDTO) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

}
