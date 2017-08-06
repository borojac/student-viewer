package org.unibl.etf.ps.studentviewer.model.dao;

public class MySQLDAOFactory implements DAOFactory {

	@Override
	public TestDAO getTestDAO() {
		return new MySQLTestDAO();
	}

}
