package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public interface StudentMainTableDAO {
	public ArrayList<StudentMainTableDTO> getAllStudentsOnPredmet(PredmetDTO predmet);
	public void setCommentForStudent(StudentMainTableDTO student, int id);
	public String getStateOfMainTable(PredmetDTO predmet, NalogDTO nalog);
	public boolean setStateOfMainTable(PredmetDTO predmet, NalogDTO nalog, String stanje);
	public void setOcjenaForStudent(StudentMainTableDTO student, int id);
}
