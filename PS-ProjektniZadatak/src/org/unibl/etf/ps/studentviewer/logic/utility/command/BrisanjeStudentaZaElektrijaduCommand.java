package org.unibl.etf.ps.studentviewer.logic.utility.command;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.logic.controller.elektrijada.ElektrijadaController;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public class BrisanjeStudentaZaElektrijaduCommand implements Command {

	private ArrayList<StudentZaElektrijaduDTO> st;
	private ElektrijadaController kontroler;
	
	public BrisanjeStudentaZaElektrijaduCommand(ArrayList<StudentZaElektrijaduDTO> st, ElektrijadaController kontroler) {
		this.st = st;
		this.kontroler = kontroler;
	}

	@Override
	public void execute() {
		for (StudentZaElektrijaduDTO s : st) {
			kontroler.getListaStudenata().remove(s);
		}

	}

	@Override
	public void unExecute() {
		for (StudentZaElektrijaduDTO s : st) {
			kontroler.getListaStudenata().add(s);
		}

	}

	@Override
	public void reExecute() {
		this.execute();

	}

}
