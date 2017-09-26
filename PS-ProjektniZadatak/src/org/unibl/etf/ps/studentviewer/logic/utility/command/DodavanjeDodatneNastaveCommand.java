package org.unibl.etf.ps.studentviewer.logic.utility.command;

import org.unibl.etf.ps.studentviewer.logic.controller.elektrijada.ElektrijadaController;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.DodatnaNastavaDTO;

public class DodavanjeDodatneNastaveCommand implements Command {
	private DodatnaNastavaDTO dodatnaNastava;
	private ElektrijadaController kontroler;
	
	public DodavanjeDodatneNastaveCommand(DodatnaNastavaDTO dodatnaNastava,ElektrijadaController kontroler) {
		this.dodatnaNastava = dodatnaNastava;
		this.kontroler = kontroler;
	}

	@Override
	public void execute() {
		kontroler.getListaDodatnihNastava().add(dodatnaNastava);

	}

	@Override
	public void unExecute() {
		kontroler.getListaDodatnihNastava().remove(dodatnaNastava);

	}

	@Override
	public void reExecute() {
		this.execute();

	}
}
