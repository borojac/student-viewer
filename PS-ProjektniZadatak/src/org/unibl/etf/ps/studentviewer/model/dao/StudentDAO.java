package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public abstract class StudentDAO {
	public abstract StudentNaPredmetuDTO getStudentBy(String brojIndeksa);

	public abstract List<StudentZaElektrijaduDTO> getIzborStudentaZaElektrijadu(int idNaloga, String disciplina, int idElektrijade);
	
	public abstract List<StudentZaElektrijaduDTO> getStudentiZaElektrijadu(String disciplina, int idElektrijade);

	public abstract boolean dodajStudentaZaElektrijadu(StudentZaElektrijaduDTO student, int idElektrijade,
			String nazivDiscipline);

	public abstract boolean obrisiStudentaZaElektrijadu(int idStudenta);

	public abstract boolean azurirajStudentaZaElektrijadu(StudentZaElektrijaduDTO student);

	/* Stankovic */
	public abstract boolean dodajStudentaUListu(StudentMainTableDTO student);

	public abstract boolean obrisiStudentaSaPredmeta(int studentID, int predmetID);
	
	public abstract boolean obrisiStudentaIzListe(String brojIndeksa);
	
	public abstract boolean azurirajStudentaUListi(StudentMainTableDTO student, String stariIndeks);
	
	public abstract boolean azurirajStudentaNaPredmetu(StudentMainTableDTO student, PredmetDTO predmet);

	public abstract boolean dodajStudentaNaPredmet(StudentMainTableDTO student, PredmetDTO predmet);
	
	public abstract String[][] getDataOfAllStudentsFromStudentDatabaseTable();
	
	public abstract int[] listaPredmetIDNaKojimaJeStudent(int studentId);
	
	public abstract ArrayList<StudentMainTableDTO> studentiKojiNisuNaPredmetu(int predmetID);
	/* Stankovic end */
	
	
	public abstract boolean gradeStudent(int studentId, int predmetId, int grade);
}
