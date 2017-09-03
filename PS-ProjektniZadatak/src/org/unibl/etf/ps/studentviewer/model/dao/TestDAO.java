package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.List;

import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public interface TestDAO {

	public TestDTO getTest(int idTesta);
	public boolean updateTest(TestDTO test);
	public boolean addTest(TestDTO test);
	public boolean deleteTest(TestDTO test);
	public List<StudentNaTestuDTO> getAllStudents(int idTesta);
	public List<TestDTO> getAllTests();
	public List<StudentNaTestuDTO> getStudentsOnPredmet(int predmetId);
}
