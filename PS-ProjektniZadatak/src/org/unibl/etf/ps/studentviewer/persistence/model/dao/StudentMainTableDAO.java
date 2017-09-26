package org.unibl.etf.ps.studentviewer.persistence.model.dao;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;

public interface StudentMainTableDAO {
	public ArrayList<StudentMainTableDTO> getAllStudentsOnPredmet(PredmetDTO predmet);
	public void setCommentForStudent(StudentMainTableDTO student, int id);
	public String getStateOfMainTable(PredmetDTO predmet, NalogDTO nalog);
	public boolean setStateOfMainTable(PredmetDTO predmet, NalogDTO nalog, String stanje);
	public void setOcjenaForStudent(StudentMainTableDTO student, int id);
}
