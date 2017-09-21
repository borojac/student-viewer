package org.unibl.etf.ps.studentviewer.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.model.dto.GradingInfoDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public interface PredmetDAO {
	
	public PredmetDTO getPredmet(int predmetId);
	public ArrayList<StudentNaPredmetuDTO> getStudentsOnPredmet(int predmetId);
	public ArrayList<TestDTO> getTestsOnPredmet(int predmetId);
	public ArrayList<PredmetDTO> getAllPredmet();
	public boolean addPredmet(PredmetDTO predmetDTO);
	public boolean addPredmete(ArrayList<PredmetDTO> predmeti);
	public boolean deletePredmet(PredmetDTO predmetDTO);
	public boolean updatePredmet(PredmetDTO predmetStari, PredmetDTO predmetNovi);
	public boolean checkStudijskiProgram(String nazivSP, short ciklus);
	public boolean addStudijskiProgram(String nazivSP, int ects, short ciklus, short trajanje, String zvanje);
	public ArrayList<String> getAllStudijskiProgramAtCiklus(short ciklus);
	public List<StudentNaPredmetuDTO> getStudentsForGrading(int predmetId);
	public List<GradingInfoDTO> getGradingInfo(int studentId, int predmetId);
}
