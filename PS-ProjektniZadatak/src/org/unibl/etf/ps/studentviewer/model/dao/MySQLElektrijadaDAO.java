package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;

public class MySQLElektrijadaDAO implements ElektrijadaDAO {

	@Override
	public ElektrijadaDTO getElektrijadaDTO(int idNaloga, String disciplina) {
		ElektrijadaDTO retVal = null;
		int idElektrijade = 0;
		String getIdElektrijadeQuery = "SELECT ElektrijadaId FROM zaduzen_za  WHERE NalogId=? AND Naziv=?";
		String getElektrijadaQuery = "SELECT ElektrijadaId, Datum, Lokacija FROM elektrijada WHERE ElektrijadaId=?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getIdElektrijadeQuery);
			ps.setInt(1, idNaloga);
			ps.setString(2, disciplina);
			rs = ps.executeQuery();

			if (rs.next()) {
				idElektrijade = rs.getInt(1);
			}
			
			ps = conn.prepareStatement(getElektrijadaQuery);
			ps.setInt(1, idElektrijade);
			rs = ps.executeQuery();
			if (rs.next()){
				retVal = new ElektrijadaDTO(rs.getInt(1), rs.getDate(2), rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}

		return retVal;
	}

}
