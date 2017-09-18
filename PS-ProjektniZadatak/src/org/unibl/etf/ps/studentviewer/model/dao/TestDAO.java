package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public interface TestDAO {

	public TestDTO getTest(int idTesta);
	public boolean updateTest(TestDTO test) throws SQLException;
	public boolean addTest(TestDTO test) throws SQLException;
	public boolean deleteTest(TestDTO test);
	public List<StudentNaTestuDTO> getStudentsOnTest(int idTesta);
	public List<TestDTO> getAllTests(int predmetId);
	public List<StudentNaTestuDTO> getStudentsOnPredmet(TestDTO test);
	public boolean verifyStudent(String brojIndeksa, int idTesta);
}
