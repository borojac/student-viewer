package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public interface StudentMainTableDAO {
	public ArrayList<StudentMainTableDTO> getAllStudents(PredmetDTO predmet);
}
