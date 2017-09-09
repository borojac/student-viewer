package org.unibl.etf.ps.studentviewer.model.dto;

import java.util.ArrayList;
import java.util.Date;

public class NalogDTO {
	
	private int nalogId;
	private String ime;
	private String prezime;
	private String korisnickoIme;
	private String lozinka;
	private Date datumRodjenja;
	private ArrayList<PredmetDTO> predmeti;
	
	public NalogDTO() {
		nalogId = 0;
		ime = "";
		prezime = "";
		korisnickoIme = "";
		lozinka = "";
		datumRodjenja = new Date();
		predmeti = new ArrayList<>();
	}
	
	public NalogDTO(int nalogId, String ime, String prezime, String korisnickoIme, String lozinka, Date datumRodjenja) {
		this.nalogId = nalogId;
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.datumRodjenja = datumRodjenja;
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

	public Date getDatumRodjenja() {
		return datumRodjenja;
	}

	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public ArrayList<PredmetDTO> getPredmeti() {
		return predmeti;
	}

	public void setPredmeti(ArrayList<PredmetDTO> predmeti) {
		this.predmeti = predmeti;
	}
	
	public void dodajPredmet(PredmetDTO predmet) {
		predmeti.add(predmet);
	}
	
	public void ukloniPredmet(PredmetDTO predmet) {
		predmeti.remove(predmet);
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
