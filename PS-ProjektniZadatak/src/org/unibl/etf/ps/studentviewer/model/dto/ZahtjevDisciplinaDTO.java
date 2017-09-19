package org.unibl.etf.ps.studentviewer.model.dto;

import java.util.Date;

public class ZahtjevDisciplinaDTO {
	
	private int nalogId;
	private int elektrijadaId;
	private String naziv;
	private Date datumZahtjeva;
	private Date datumOdobrenja;
	private int adminId;
	
	public ZahtjevDisciplinaDTO() {
		nalogId = elektrijadaId = adminId = 0;
		naziv = "";
		datumOdobrenja = datumZahtjeva = null;
	}
	
	public ZahtjevDisciplinaDTO(int nalogId, int elektrijadaId, String naziv, Date datumZahtjeva, Date datumOdobrenja,
			int adminId) {
		super();
		this.nalogId = nalogId;
		this.elektrijadaId = elektrijadaId;
		this.naziv = naziv;
		this.datumZahtjeva = datumZahtjeva;
		this.datumOdobrenja = datumOdobrenja;
		this.adminId = adminId;
	}

	public ZahtjevDisciplinaDTO(int nalogId, int elektrijadaId, String naziv){
		this.nalogId = nalogId;
		this.elektrijadaId = elektrijadaId;
		this.naziv = naziv;
		this.datumZahtjeva = new Date();
		this.datumOdobrenja = new Date();
		this.adminId = 0;
	}
	
	public int getNalogId() {
		return nalogId;
	}

	public void setNalogId(int nalogId) {
		this.nalogId = nalogId;
	}

	public int getElektrijadaId() {
		return elektrijadaId;
	}

	public void setElektrijadaId(int elektrijadaId) {
		this.elektrijadaId = elektrijadaId;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Date getDatumZahtjeva() {
		return datumZahtjeva;
	}

	public void setDatumZahtjeva(Date datumZahtjeva) {
		this.datumZahtjeva = datumZahtjeva;
	}

	public Date getDatumOdobrenja() {
		return datumOdobrenja;
	}

	public void setDatumOdobrenja(Date datumOdobrenja) {
		this.datumOdobrenja = datumOdobrenja;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + elektrijadaId;
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
		ZahtjevDisciplinaDTO other = (ZahtjevDisciplinaDTO) obj;
		if (elektrijadaId != other.elektrijadaId)
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ZahtjevDisciplinaDTO [nalogId=" + nalogId + ", elektrijadaId=" + elektrijadaId + ", naziv=" + naziv
				+ ", datumZahtjeva=" + datumZahtjeva + ", datumOdobrenja=" + datumOdobrenja + ", adminId=" + adminId
				+ "]";
	}
	
	
}
