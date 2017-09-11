package org.unibl.etf.ps.studentviewer.model.dto;

import java.util.Date;

public class ZahtjevDTO {
	
	private int predmetId;
	private int nalogId;
	private int adminId;
	private Date datumZahtjeva;
	private Date datumOdobrenja;
	
	public ZahtjevDTO() {
		predmetId = 0;
		nalogId = 0;
		adminId = 0;
		datumZahtjeva = new Date();
		datumOdobrenja = new Date();
	}
	
	public ZahtjevDTO(int predmetId, int nalogId) {
		this.predmetId = predmetId;
		this.nalogId = nalogId;
		adminId = 0;
		datumZahtjeva = new Date();
		datumOdobrenja = new Date();
	}
	
	public ZahtjevDTO(int predmetId, int nalogId, int adminId, Date datumZahtjeva, Date datumOdobrenja) {
		this.predmetId = predmetId;
		this.nalogId = nalogId;
		this.adminId = adminId;
		this.datumZahtjeva = datumZahtjeva;
		this.datumOdobrenja = datumOdobrenja;
	}

	public int getPredmetId() {
		return predmetId;
	}

	public void setPredmetId(int predmetId) {
		this.predmetId = predmetId;
	}

	public int getNalogId() {
		return nalogId;
	}

	public void setNalogId(int nalogId) {
		this.nalogId = nalogId;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
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

}
