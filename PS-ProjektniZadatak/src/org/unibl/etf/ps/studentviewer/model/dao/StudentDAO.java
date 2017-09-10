package org.unibl.etf.ps.studentviewer.model.dao;

import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;

public abstract class StudentDAO {
	public abstract StudentNaPredmetuDTO getStudentBy(String brojIndeksa);
}
