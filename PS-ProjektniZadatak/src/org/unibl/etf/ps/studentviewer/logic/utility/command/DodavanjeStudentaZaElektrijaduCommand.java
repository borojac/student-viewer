package org.unibl.etf.ps.studentviewer.logic.utility.command;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.logic.controller.ElektrijadaController;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public class DodavanjeStudentaZaElektrijaduCommand implements Command {

	private ArrayList<StudentZaElektrijaduDTO> st;

	public DodavanjeStudentaZaElektrijaduCommand(ArrayList<StudentZaElektrijaduDTO> st) {
		this.st = st;
	}

	@Override
	public void execute() {
		for (StudentZaElektrijaduDTO s : st) {
			ElektrijadaController.listaStudenata.add(s);
		}

	}

	@Override
	public void unExecute() {
		for (StudentZaElektrijaduDTO s : st) {
			ElektrijadaController.listaStudenata.remove(s);
		}

	}

	@Override
	public void reExecute() {
		this.execute();

	}

}
