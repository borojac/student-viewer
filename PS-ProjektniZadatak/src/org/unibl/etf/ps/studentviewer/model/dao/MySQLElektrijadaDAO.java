package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;
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

	@Override
	public List<ElektrijadaDTO> getListuElektrijada(int idNaloga) {
		List<ElektrijadaDTO> retVal = new ArrayList<ElektrijadaDTO>();
		List<Integer> idElektrijade = new ArrayList<Integer>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT  ElektrijadaId FROM zaduzen_za WHERE NalogId=?";
		String query2 = "SELECT ElektrijadaId, Datum, Lokacija FROM elektrijada WHERE ElektrijadaId=?";
		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, idNaloga);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				idElektrijade.add(rs.getInt(1));
			}
			for (int i : idElektrijade){
				ps = conn.prepareStatement(query2);
				ps.setInt(1, i);
				rs = ps.executeQuery();
				if (rs.next())
					retVal.add(new ElektrijadaDTO(rs.getInt(1), rs.getDate(2), rs.getString(3)));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		return retVal;
	}

}
