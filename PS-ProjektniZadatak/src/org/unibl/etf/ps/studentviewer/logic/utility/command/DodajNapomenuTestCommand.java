package org.unibl.etf.ps.studentviewer.logic.utility.command;

import java.awt.EventQueue;

import javax.swing.JTextArea;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.TestDTO;

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
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				napomenaArea.setText(prethodnaVrijednost);
			}
		});

	}

	@Override
	public void reExecute() {

		this.test.setNapomena(sljedecaVrijednost);
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				napomenaArea.setText(sljedecaVrijednost);
			}
		});
		
	}

}
