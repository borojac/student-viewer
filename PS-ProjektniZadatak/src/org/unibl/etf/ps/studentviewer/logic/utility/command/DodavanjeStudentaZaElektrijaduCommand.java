package org.unibl.etf.ps.studentviewer.logic.utility.command;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.logic.controller.elektrijada.ElektrijadaController;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public class DodavanjeStudentaZaElektrijaduCommand implements Command {

	private ArrayList<StudentZaElektrijaduDTO> student;
	private ElektrijadaController kontroler;
	
	public DodavanjeStudentaZaElektrijaduCommand(ArrayList<StudentZaElektrijaduDTO> student, ElektrijadaController kontroler) {
		this.student = student;
		this.kontroler = kontroler;
	}

	@Override
	public void execute() {
		for (StudentZaElektrijaduDTO s : student) {
			kontroler.getListaStudenata().add(s);
		}

	}

	@Override
	public void unExecute() {
		for (StudentZaElektrijaduDTO s : student) {
			kontroler.getListaStudenata().remove(s);
		}

	}

	@Override
	public void reExecute() {
		this.execute();

	}

}
