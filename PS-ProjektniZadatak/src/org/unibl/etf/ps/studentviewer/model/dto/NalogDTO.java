package org.unibl.etf.ps.studentviewer.model.dto;

import java.util.ArrayList;

public class NalogDTO {
	
	private int nalogId;
	private String ime;
	private String prezime;
	private String korisnickoIme;
	private String lozinka;
	private ArrayList<PredmetDTO> predmeti;
	
	public NalogDTO() {
		nalogId = 0;
		ime = "";
		prezime = "";
		korisnickoIme = "";
		lozinka = "";
		predmeti = new ArrayList<>();
	}
	
	public NalogDTO(int nalogId, String ime, String prezime, String korisnickoIme, String lozinka) {
		this.nalogId = nalogId;
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		predmeti = new ArrayList<>();
	}

	public int getNalogId() {
		return nalogId;
	}

	public void setNalogId(int nalogId) {
		this.nalogId = nalogId;
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

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public ArrayList<PredmetDTO> getPredmeti() {
		return predmeti;
	}

	public void setPredmeti(ArrayList<PredmetDTO> predmeti) {
		this.predmeti = predmeti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nalogId;
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
		NalogDTO other = (NalogDTO) obj;
		if (nalogId != other.nalogId)
			return false;
		return true;
	}

}
