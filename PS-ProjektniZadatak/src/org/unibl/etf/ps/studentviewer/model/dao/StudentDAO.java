package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.List;

import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public abstract class StudentDAO {
	public abstract StudentNaPredmetuDTO getStudentBy(String brojIndeksa);

	public abstract List<StudentZaElektrijaduDTO> getStudentiZaElektrijadu(int idNaloga, int idElektrijade,
			String nazivDiscipline);

	public abstract boolean dodajStudentaZaElektrijadu(StudentZaElektrijaduDTO student, int idElektrijade,
			String nazivDiscipline);

	public abstract boolean obrisiStudentaZaElektrijadu(int idStudenta);

	public abstract boolean azurirajStudentaZaElektrijadu(StudentZaElektrijaduDTO student);
}
