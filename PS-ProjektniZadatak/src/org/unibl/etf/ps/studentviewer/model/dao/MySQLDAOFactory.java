package org.unibl.etf.ps.studentviewer.model.dao;

public class MySQLDAOFactory implements DAOFactory {

	@Override
	public TestDAO getTestDAO() {
		return new MySQLTestDAO();
	}
	
	@Override
	public PredmetDAO getPredmetDAO() {
		return new MySQLPredmetDAO();
	}
	
	@Override 
	public NalogDAO getNalogDAO() {
		return new MySQLNalogDAO();
	}

	@Override
	public StudentDAO getStudentDAO() {
		return new MySQLStudentDAO();
	}

	@Override
	public DodatnaNastavaDAO getDodatnaNastavaDAO() {
		return new MySQLDodatnaNastavaDAO();
	}
	
	@Override
	public ZahtjevDAO getZahtjevDAO() {
		return new MySQLZahtjevDAO();
	}

}
