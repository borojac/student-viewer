package org.unibl.etf.ps.studentviewer.logic.command;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class UkloniStudenteTestCommand extends TestCommand {
	
	private List<StudentNaTestuDTO> prethodnaLista, sljedecaLista;
	private StudentTableModel model;

	public UkloniStudenteTestCommand(TestDTO test, StudentTableModel model, List<StudentNaTestuDTO> data) {
		super(test);
		this.model = model;
		sljedecaLista = data;
		prethodnaLista = test.getStudenti();
	}
	@Override
	public void execute() {
		model.setData(sljedecaLista);
		test.setStudenti(sljedecaLista);
		model.fireTableDataChanged();
	}

	@Override
	public void unExecute() {
		model.setData(prethodnaLista);
		test.setStudenti(prethodnaLista);
		model.fireTableDataChanged();
	}

	@Override
	public void reExecute() {
		execute();
	}

}
