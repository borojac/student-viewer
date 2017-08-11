package org.unibl.etf.ps.studentviewer.command;

import java.util.List;

import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class DodajStudenteTestCommand extends TestCommand {

	private List<StudentNaTestuDTO> prethodnaLista, sljedecaLista;
	private StudentTableModel model;
	public DodajStudenteTestCommand(TestDTO test, StudentTableModel model, List<StudentNaTestuDTO> data) {
		super(test);
		this.model = model;
		prethodnaLista = test.getStudenti();
		sljedecaLista = data;
	}
	@Override
	public void execute() {

		test.setStudenti(sljedecaLista);
		model.setData(sljedecaLista);
		model.fireTableDataChanged();

	}

	@Override
	public void unExecute() {
		test.setStudenti(prethodnaLista);
		model.setData(prethodnaLista);
		model.fireTableDataChanged();
	}

	@Override
	public void reExecute() {
		execute();
	}

}