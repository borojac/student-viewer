package org.unibl.etf.ps.studentviewer.logic.command;

import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;

public class IzmjenaKomentaraTestCommand implements Command {
	
	private String prethodniKomentar, sljedeciKomentar;
	
	private StudentNaTestuDTO student;
	
	public IzmjenaKomentaraTestCommand(StudentNaTestuDTO student, String sljedeciKomentar) {
		this.student = student;
		prethodniKomentar = student.getKomentar();
		this.sljedeciKomentar = sljedeciKomentar;
	}
	
	@Override
	public void execute() {
		student.setKomentar(sljedeciKomentar);
	}

	@Override
	public void unExecute() {
		student.setKomentar(prethodniKomentar);

	}

	@Override
	public void reExecute() {
		execute();
	}

}
