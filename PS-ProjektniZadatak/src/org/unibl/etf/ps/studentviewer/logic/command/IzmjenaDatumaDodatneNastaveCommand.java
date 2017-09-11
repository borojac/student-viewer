package org.unibl.etf.ps.studentviewer.logic.command;

import java.util.Date;

import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;

public class IzmjenaDatumaDodatneNastaveCommand extends IzmjenaDodatneNastaveCommand {
	private DodatnaNastavaDTO dodatnaNastava;
	private Date noviDatum;
	private Date stariDatum;

	public IzmjenaDatumaDodatneNastaveCommand(DodatnaNastavaDTO dodatnaNastava, Date noviDatum) {
		this.dodatnaNastava = dodatnaNastava;
		stariDatum = dodatnaNastava.getDatum();
		this.noviDatum = noviDatum;
		dodatnaNastava.setDatum(noviDatum);
	}

	@Override
	public void execute() {
		dodatnaNastava.setDatum(noviDatum);
	}

	@Override
	public void unExecute() {
		dodatnaNastava.setDatum(stariDatum);
	}

	@Override
	public void reExecute() {
		this.execute();

	}
}
