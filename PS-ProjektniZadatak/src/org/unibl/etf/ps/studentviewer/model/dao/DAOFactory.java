package org.unibl.etf.ps.studentviewer.model.dao;

import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public interface DAOFactory {
	
	public TestDAO getTestDAO();

}
