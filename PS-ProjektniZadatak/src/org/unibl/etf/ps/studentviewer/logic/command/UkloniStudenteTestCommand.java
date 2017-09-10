package org.unibl.etf.ps.studentviewer.logic.command;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.Even;
import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class UkloniStudenteTestCommand extends TestCommand {
	
	private List<StudentNaTestuDTO> prethodnaLista, sljedecaLista;
	private StudentTableModel model;

	public UkloniStudenteTestCommand(TestDTO test, StudentTableModel model, List<StudentNaTestuDTO> forRemoving) {
		super(test);
		this.model = model;
		prethodnaLista = test.getStudenti();
		sljedecaLista = new ArrayList<>(prethodnaLista);
		sljedecaLista.removeAll(forRemoving);
	}
	@Override
	public void execute() {
		test.setStudenti(sljedecaLista);
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				model.setData(sljedecaLista);
			}
		});
	}

	@Override
	public void unExecute() {
		test.setStudenti(prethodnaLista);
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				model.setData(prethodnaLista);
			}
		});
	}

	@Override
	public void reExecute() {
		execute();
	}

}
