package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;

public class MySQLElektrijadaDAO implements ElektrijadaDAO {

	@Override
	public ElektrijadaDTO getElektrijadaZaNalogDTO(int idNaloga, String disciplina) {
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
		HashSet<Integer> idElektrijade = new HashSet<Integer>();
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

	@Override
	public List<ElektrijadaDTO> getSveElektrijade() {
		List<ElektrijadaDTO> retVal = new ArrayList<ElektrijadaDTO>();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT  ElektrijadaId, Datum, Lokacija FROM elektrijada";
		
		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			
			
			rs = ps.executeQuery();
			while (rs.next()) {
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

	@Override
	public ElektrijadaDTO getElektrijadaPoId(int idElektrijade) {
		ElektrijadaDTO retVal = null;
		String getIdElektrijadaQuery = "SELECT ElektrijadaId, Datum, Lokacija FROM elektrijada  WHERE ElektrijadaId=?";
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getIdElektrijadaQuery);
			ps.setInt(1, idElektrijade);
			
			rs = ps.executeQuery();

			if (rs.next()) {
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
	public boolean addElektrijada(ElektrijadaDTO elektrijadaDTO) {
		boolean retVal = false;

		String query = "INSERT INTO elektrijada VALUE (?, ?, ?)";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			ps.setInt(1, elektrijadaDTO.getId());
			ps.setDate(2,new  java.sql.Date(elektrijadaDTO.getDatum().getTime()));
			ps.setString(3, elektrijadaDTO.getLokacija());
			

			retVal = ps.executeUpdate() == 1;

			if (retVal) {
				conn.commit();
			} else {
				throw new SQLException("Rollback needed!");
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public boolean deleteElektrijada(ElektrijadaDTO elektrijadaDTO) {
		boolean retVal = false;

		String query = "DELETE FROM elektrijada WHERE ElektrijadaId=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			
		
			ps.setInt(1, elektrijadaDTO.getId());

			retVal = ps.executeUpdate() == 1;

			if (retVal) {
				conn.commit();
			} else {
				throw new SQLException("Rollback needed!");
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

}
