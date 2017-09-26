package org.unibl.etf.ps.studentviewer.persistence.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentZaElektrijaduDTO;

public interface StudentDAO {
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
	
	public abstract boolean obrisiStudentaSaSvihTestova(int studentId);
	
	public abstract boolean obrisiStudentaSaElektrijade(int studentId);
	
	public abstract boolean obrisiStudentaSaSvihPredmeta(int studentId);
	/* Stankovic end */
	
	public abstract boolean hasGrade(int studentId, int predmetId);
	public abstract boolean gradeStudent(int studentId, int predmetId, int grade);
	public abstract boolean recallGrade(int studentId, int predmetId);

	public abstract boolean ukloniUcesce(int id) ;

	public abstract boolean ukloniUcescePoDisciplini(DisciplinaDTO disciplinaDTO);
}
