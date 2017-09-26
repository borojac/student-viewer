package org.unibl.etf.ps.studentviewer.logic.utility.command;

import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;

public class IzmjenaNazivaDodatneNastaveCommand extends IzmjenaDodatneNastaveCommand {
	private DodatnaNastavaDTO dodatnaNastava;
	private String noviNaziv;
	private String stariNaziv;

	public IzmjenaNazivaDodatneNastaveCommand(DodatnaNastavaDTO dodatnaNastava, String noviNaziv) {
		this.dodatnaNastava = dodatnaNastava;
		stariNaziv = dodatnaNastava.getNazivTeme();
		this.noviNaziv = noviNaziv;
		dodatnaNastava.setNazivTeme(noviNaziv);
	}

	@Override
	public void execute() {
		dodatnaNastava.setNazivTeme(noviNaziv);
	}

	@Override
	public void unExecute() {
		dodatnaNastava.setNazivTeme(stariNaziv);
	}

	@Override
	public void reExecute() {
		this.execute();
	}
}
