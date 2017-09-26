package org.unibl.etf.ps.studentviewer.logic.utility.command;

import org.unibl.etf.ps.studentviewer.gui.view.test.StudentTableModel;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentNaTestuDTO;

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
