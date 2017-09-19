package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDisciplinaDTO;

public class MySQLZahtjevDisciplinaDAO implements ZahtjevDisciplinaDAO {

	@Override
	public boolean addZahtjev(ZahtjevDisciplinaDTO zahtjevDTO) {
boolean retVal = false;
		
		String query = "INSERT INTO zahtjev_disciplina VALUE (?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			System.out.println(zahtjevDTO);
			
			ps.setInt(1, zahtjevDTO.getNalogId());
			ps.setInt(2, zahtjevDTO.getElektrijadaId());
			ps.setString(3, zahtjevDTO.getNaziv());
			ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			ps.setObject(5, null, java.sql.Types.DATE);
			ps.setObject(6, null, java.sql.Types.INTEGER);
						
			retVal = ps.executeUpdate() == 1;
			
			if (retVal) {
				conn.commit();
			}
			else {
				throw new SQLException("Rollback needed!");
			}
			
		} catch(SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {}
			DBUtility.close(conn, ps);
		}
		
		return retVal;
	}

}
