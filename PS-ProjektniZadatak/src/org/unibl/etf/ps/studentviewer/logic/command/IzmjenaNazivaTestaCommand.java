package org.unibl.etf.ps.studentviewer.logic.command;

import javax.swing.JTextField;

import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class IzmjenaNazivaTestaCommand extends TestCommand {
	
	private String prethodniNaziv, sljedeciNaziv;
	
	private JTextField nazivTextField;
	
	public IzmjenaNazivaTestaCommand(TestDTO test, JTextField nazivTextField) {
		super(test);
		this.nazivTextField = nazivTextField;
		this.sljedeciNaziv = nazivTextField.getText();
		this.prethodniNaziv = test.getNaziv();
	}

	@Override
	public void execute() {
		this.test.setNaziv(sljedeciNaziv);

	}

	@Override
	public void unExecute() {
		this.nazivTextField.setText(prethodniNaziv);
		this.test.setNaziv(prethodniNaziv);
	}
	
	@Override
	public void reExecute() {
		this.nazivTextField.setText(sljedeciNaziv);
		this.test.setNaziv(sljedeciNaziv);
	}

}
