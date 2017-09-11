package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public interface PredmetDAO {
	
	public PredmetDTO getPredmet(int predmetId);
	public ArrayList<StudentNaPredmetuDTO> getStudentsOnPredmet(int predmetId);
	public ArrayList<TestDTO> getTestsOnPredmet(int predmetId);
	public ArrayList<PredmetDTO> getAllPredmet();

}
