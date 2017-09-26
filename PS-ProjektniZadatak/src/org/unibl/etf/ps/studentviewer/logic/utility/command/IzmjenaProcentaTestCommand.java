package org.unibl.etf.ps.studentviewer.logic.utility.command;

import java.awt.EventQueue;

import javax.swing.JComboBox;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.TestDTO;

public class IzmjenaProcentaTestCommand extends TestCommand {
	private int prethodniProcenat, sljedeciProcenat;
	private JComboBox procenatComboBox;
	public IzmjenaProcentaTestCommand(TestDTO test, JComboBox procenatComboBox) {
		super(test);
		this.procenatComboBox = procenatComboBox;
		this.prethodniProcenat = test.getProcenat();
		this.sljedeciProcenat = Integer.parseInt((String) procenatComboBox.getSelectedItem());
	}
	@Override
	public void execute() {
		test.setProcenat(sljedeciProcenat);
	}

	@Override
	public void unExecute() {
		test.setProcenat(prethodniProcenat);
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				procenatComboBox.setSelectedItem("" + prethodniProcenat);
			}
		});
	}

	@Override
	public void reExecute() {
		test.setProcenat(sljedeciProcenat);
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				procenatComboBox.setSelectedItem("" + sljedeciProcenat);
			}
		});
	}

}
