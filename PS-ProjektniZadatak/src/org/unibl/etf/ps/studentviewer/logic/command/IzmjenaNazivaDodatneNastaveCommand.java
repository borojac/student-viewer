package org.unibl.etf.ps.studentviewer.logic.command;

import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;

public class IzmjenaNazivaDodatneNastaveCommand extends IzmjenaDodatneNastaveCommand {
	private DodatnaNastavaDTO dodatnaNastava;
	private String noviNaziv;
	private String stariNaziv;
	
	public IzmjenaNazivaDodatneNastaveCommand(DodatnaNastavaDTO dodatnaNastava, String noviNaziv) {		
		this.dodatnaNastava = dodatnaNastava;	
		stariNaziv = dodatnaNastava.getNaziv();
		this.noviNaziv = noviNaziv;
		dodatnaNastava.setNaziv(noviNaziv);
	}

	@Override
	public void execute() {
		dodatnaNastava.setNaziv(noviNaziv);
	}

	@Override
	public void unExecute() {
		dodatnaNastava.setNaziv(stariNaziv);
	}
	
	@Override
	public void reExecute(){
		this.execute();
	}
}
