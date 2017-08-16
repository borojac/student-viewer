package org.unibl.etf.ps.studentviewer.command;

import org.unibl.etf.ps.studentviewer.gui.StudentTableModel;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;

public class IzmjenaKomentaraTestCommand implements Command {
	
	private String prethodniKomentar, sljedeciKomentar;
	
	private StudentNaTestuDTO student;
	private StudentTableModel model;
	private int index;
	
	public IzmjenaKomentaraTestCommand(StudentTableModel model, StudentNaTestuDTO student, String sljedeciKomentar) {
		this.student = student;
		prethodniKomentar = student.getKomentar();
		this.sljedeciKomentar = sljedeciKomentar;
		this.model = model;
		index = model.getTestDTO().getStudenti().indexOf(student);
	}
	
	@Override
	public void execute() {
		student.setKomentar(sljedeciKomentar);
		model.getTestDTO().getStudenti().get(index).setKomentar(sljedeciKomentar);
		model.fireTableDataChanged();
	}

	@Override
	public void unExecute() {
		student.setKomentar(prethodniKomentar);
		model.getTestDTO().getStudenti().get(index).setKomentar(prethodniKomentar);
		model.fireTableDataChanged();

	}

	@Override
	public void reExecute() {
		execute();
	}

}
