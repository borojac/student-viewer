package org.unibl.etf.ps.studentviewer.command;

import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;

public class IzmjenaBrojaBodovaTestCommand implements Command {

	private int prethodniBrojBodova, sljedeciBrojBodova;
	
	private StudentNaTestuDTO student;
	private StudentTableModel model;
	private int index;
	public IzmjenaBrojaBodovaTestCommand(StudentTableModel model, StudentNaTestuDTO student, int sljedeciBrojBodova) {
		this.student = student;
		prethodniBrojBodova = student.getBrojBodova();
		this.sljedeciBrojBodova = sljedeciBrojBodova;
		this.model = model;
		index = model.getTestDTO().getStudenti().indexOf(student);
	}
	
	@Override
	public void execute() {
		student.setBrojBodova(sljedeciBrojBodova);
		model.getTestDTO().getStudenti().get(index).setBrojBodova(sljedeciBrojBodova);
		model.fireTableDataChanged();
	}

	@Override
	public void unExecute() {
		student.setBrojBodova(prethodniBrojBodova);
		model.getTestDTO().getStudenti().get(index).setBrojBodova(prethodniBrojBodova);
		model.fireTableDataChanged();
	}

	@Override
	public void reExecute() {
		execute();
	}

}
