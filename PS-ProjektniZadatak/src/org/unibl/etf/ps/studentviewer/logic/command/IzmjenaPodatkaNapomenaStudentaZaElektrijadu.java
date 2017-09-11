package org.unibl.etf.ps.studentviewer.logic.command;

import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public class IzmjenaPodatkaNapomenaStudentaZaElektrijadu extends IzmjenaStudentaZaElektrijaduCommand {
	private StudentZaElektrijaduDTO student;
	private String novaNapomena;
	private String staraNapomena;

	public IzmjenaPodatkaNapomenaStudentaZaElektrijadu(StudentZaElektrijaduDTO student, String novaNapomena) {
		this.student = student;
		staraNapomena = student.getNapomena();
		this.novaNapomena = novaNapomena;
		student.setNapomena(novaNapomena);
	}

	@Override
	public void execute() {
		student.setNapomena(novaNapomena);
	}

	@Override
	public void unExecute() {
		student.setNapomena(staraNapomena);
	}

	@Override
	public void reExecute() {
		this.execute();
	}
}
