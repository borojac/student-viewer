package org.unibl.etf.ps.studentviewer.persistence.model.dao;

import org.unibl.etf.ps.studentviewer.persistence.model.dto.TestDTO;

public interface DAOFactory {
	
	public TestDAO getTestDAO();
	public PredmetDAO getPredmetDAO();
	public NalogDAO getNalogDAO();
	public StudentDAO getStudentDAO();
	public DodatnaNastavaDAO getDodatnaNastavaDAO();
	public ZahtjevDAO getZahtjevDAO();
	public ElektrijadaDAO getElektrijadaDAO();
	public DisciplinaDAO getDisciplinaDAO();
	public StudentMainTableDAO getStudentMainTableDAO();
	public ZahtjevDisciplinaDAO getZahtjevDiciplinaDAO();
}
