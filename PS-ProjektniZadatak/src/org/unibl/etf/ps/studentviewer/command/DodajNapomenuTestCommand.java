package org.unibl.etf.ps.studentviewer.command;

import javax.swing.JTextArea;

import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class DodajNapomenuTestCommand extends TestCommand {
	
	private String prethodnaVrijednost, sljedecaVrijednost;
	private JTextArea napomenaArea;
	
	public DodajNapomenuTestCommand(TestDTO test, JTextArea napomenaArea) {
		super(test);
		this.napomenaArea = napomenaArea;
		this.prethodnaVrijednost = test.getNapomena();
		this.sljedecaVrijednost = napomenaArea.getText();
	}

	@Override
	public void execute() {

		this.test.setNapomena(sljedecaVrijednost);
	}

	@Override
	public void unExecute() {

		this.test.setNapomena(prethodnaVrijednost);
		napomenaArea.setText(prethodnaVrijednost);

	}

	@Override
	public void reExecute() {

		this.test.setNapomena(sljedecaVrijednost);
		napomenaArea.setText(sljedecaVrijednost);
		
	}

}
