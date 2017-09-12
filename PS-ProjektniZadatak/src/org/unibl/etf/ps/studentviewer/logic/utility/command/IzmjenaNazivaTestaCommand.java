package org.unibl.etf.ps.studentviewer.logic.utility.command;

import java.awt.EventQueue;

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
		test.setNaziv(sljedeciNaziv);

	}

	@Override
	public void unExecute() {
		test.setNaziv(prethodniNaziv);
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				nazivTextField.setText(prethodniNaziv);
			}
		});
		
	}
	
	@Override
	public void reExecute() {
		this.test.setNaziv(sljedeciNaziv);
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				nazivTextField.setText(sljedeciNaziv);
			}
		});
	}

}
