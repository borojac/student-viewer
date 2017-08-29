package org.unibl.etf.ps.studentviewer.command;

import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;

public class IzmjenaBrojaBodovaTestCommand implements Command {

	private int prethodniBrojBodova, sljedeciBrojBodova;
	
	private StudentNaTestuDTO student;
	private int index;
	public IzmjenaBrojaBodovaTestCommand(StudentNaTestuDTO student, int sljedeciBrojBodova) {
		this.student = student;
		prethodniBrojBodova = student.getBrojBodova();
		this.sljedeciBrojBodova = sljedeciBrojBodova;
	}
	
	@Override
	public void execute() {
		student.setBrojBodova(sljedeciBrojBodova);
	}

	@Override
	public void unExecute() {
		student.setBrojBodova(prethodniBrojBodova);
	}

	@Override
	public void reExecute() {
		execute();
	}

}
