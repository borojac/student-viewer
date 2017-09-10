package org.unibl.etf.ps.studentviewer.model.dto;

import java.util.ArrayList;

public class NalogDTO {
	
	private int nalogId;
	private String ime;
	private String prezime;
	private String korisnickoIme;
	private String lozinka;
	private char tipNaloga;
	private ArrayList<PredmetDTO> predmeti;
	
	public NalogDTO() {
		nalogId = 0;
		ime = "";
		prezime = "";
		korisnickoIme = "";
		lozinka = "";
		tipNaloga = 'k';
		predmeti = new ArrayList<>();
	}
	
	public NalogDTO(int nalogId, String ime, String prezime, String korisnickoIme, String lozinka, char tipNaloga) {
		this.nalogId = nalogId;
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.tipNaloga = tipNaloga;
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

	public char getTipNaloga() {
		return tipNaloga;
	}

	public void setTipNaloga(char tipNaloga) {
		this.tipNaloga = tipNaloga;
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
