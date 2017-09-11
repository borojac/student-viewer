package org.unibl.etf.ps.studentviewer.logic.command;

import org.unibl.etf.ps.studentviewer.logic.controller.ElektrijadaController;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;

public class DodavanjeDodatneNastaveCommand implements Command {
	private DodatnaNastavaDTO dodatnaNastava;

	public DodavanjeDodatneNastaveCommand(DodatnaNastavaDTO dodatnaNastava) {
		this.dodatnaNastava = dodatnaNastava;
	}

	@Override
	public void execute() {
		ElektrijadaController.listaDodatnihNastava.add(dodatnaNastava);

	}

	@Override
	public void unExecute() {
		ElektrijadaController.listaDodatnihNastava.remove(dodatnaNastava);

	}

	@Override
	public void reExecute() {
		this.execute();

	}
}
