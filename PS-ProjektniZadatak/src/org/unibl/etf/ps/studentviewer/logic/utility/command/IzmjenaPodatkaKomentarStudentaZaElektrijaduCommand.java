package org.unibl.etf.ps.studentviewer.logic.utility.command;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentZaElektrijaduDTO;

public class IzmjenaPodatkaKomentarStudentaZaElektrijaduCommand extends IzmjenaStudentaZaElektrijaduCommand {

	private StudentZaElektrijaduDTO student;
	private String noviKomentar;
	private String stariKomentar;

	public IzmjenaPodatkaKomentarStudentaZaElektrijaduCommand(StudentZaElektrijaduDTO student, String noviKomentar) {
		this.student = student;
		stariKomentar = student.getKomentar();
		this.noviKomentar = noviKomentar;
		student.setKomentar(noviKomentar);
	}

	@Override
	public void execute() {
		student.setKomentar(noviKomentar);
	}

	@Override
	public void unExecute() {
		student.setKomentar(stariKomentar);
	}

	@Override
	public void reExecute() {
		this.execute();
	}

}
