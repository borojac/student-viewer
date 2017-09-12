package org.unibl.etf.ps.studentviewer.logic.utility.command;

import org.unibl.etf.ps.studentviewer.logic.controller.ElektrijadaController;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;

public class BrisanjeDodatneNastaveCommand implements Command {

	private DodatnaNastavaDTO dodatnaNastava;

	public BrisanjeDodatneNastaveCommand(DodatnaNastavaDTO dodatnaNastava) {
		this.dodatnaNastava = dodatnaNastava;
	}

	@Override
	public void execute() {
		ElektrijadaController.listaDodatnihNastava.remove(dodatnaNastava);
	}

	@Override
	public void unExecute() {
		ElektrijadaController.listaDodatnihNastava.add(dodatnaNastava);
	}

	@Override
	public void reExecute() {
		this.execute();

	}

}
