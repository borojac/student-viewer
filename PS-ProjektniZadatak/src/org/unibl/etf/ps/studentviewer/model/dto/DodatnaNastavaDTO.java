package org.unibl.etf.ps.studentviewer.model.dto;

public class DodatnaNastavaDTO {
	private int elektrijadaID;
	private int nalogID;
	private String naziv;
	private String datum;
	private String napomena;

	public DodatnaNastavaDTO() {
		// TODO Auto-generated constructor stub
	}

	

	public int getElektrijadaID() {
		return elektrijadaID;
	}



	public void setElektrijadaID(int elektrijadaID) {
		this.elektrijadaID = elektrijadaID;
	}



	public int getNalogID() {
		return nalogID;
	}



	public void setNalogID(int nalogID) {
		this.nalogID = nalogID;
	}



	public DodatnaNastavaDTO(int elektrijadaID, int nalogID, String naziv, String datum, String napomena) {
		super();
		this.elektrijadaID = elektrijadaID;
		this.nalogID = nalogID;
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
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result + elektrijadaID;
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
		if (elektrijadaID != other.elektrijadaID)
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}

	
}
