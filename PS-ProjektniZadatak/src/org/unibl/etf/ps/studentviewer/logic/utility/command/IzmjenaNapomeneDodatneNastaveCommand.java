package org.unibl.etf.ps.studentviewer.logic.utility.command;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.DodatnaNastavaDTO;

public class IzmjenaNapomeneDodatneNastaveCommand extends IzmjenaDodatneNastaveCommand {
	private DodatnaNastavaDTO dodatnaNastava;
	private String novaNapomena;
	private String staraNapomena;

	public IzmjenaNapomeneDodatneNastaveCommand(DodatnaNastavaDTO dodatnaNastava, String novaNapomena) {
		this.dodatnaNastava = dodatnaNastava;
		staraNapomena = dodatnaNastava.getNapomena();
		this.novaNapomena = novaNapomena;
		dodatnaNastava.setNapomena(novaNapomena);
	}

	@Override
	public void execute() {
		dodatnaNastava.setNapomena(novaNapomena);
	}

	@Override
	public void unExecute() {
		dodatnaNastava.setNapomena(staraNapomena);
	}

	@Override
	public void reExecute() {
		this.execute();
	}
}
