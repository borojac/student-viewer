package org.unibl.etf.ps.studentviewer.logic.command;

import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;

public class IzmjenaDatumaDodatneNastaveCommand extends IzmjenaDodatneNastaveCommand {
	private DodatnaNastavaDTO dodatnaNastava;
	private String noviDatum;
	private String stariDatum;

	public IzmjenaDatumaDodatneNastaveCommand(DodatnaNastavaDTO dodatnaNastava, String noviDatum) {
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
