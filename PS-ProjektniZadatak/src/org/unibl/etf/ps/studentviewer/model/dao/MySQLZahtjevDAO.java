package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDTO;

public class MySQLZahtjevDAO implements ZahtjevDAO {

	@Override
	public boolean addZahtjev(ZahtjevDTO zahtjevDTO) {
		boolean retVal = false;
		
		String query = "INSERT INTO zahtjev VALUE (?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, zahtjevDTO.getNalogId());
			ps.setObject(2, null, java.sql.Types.INTEGER);
			ps.setInt(3, zahtjevDTO.getPredmetId());
			ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			ps.setObject(5, null, java.sql.Types.DATE);
			
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

	@Override
	public boolean updateZahtjev(ZahtjevDTO zahtjevDTO) {
		boolean retVal = false;
		
		String query = "UPDATE zahtjev SET AdminId = ?, DatumZahtjeva = ?, DatumOdobrenja = ? WHERE PredmetId = ? and NalogId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, zahtjevDTO.getAdminId());
			ps.setDate(2, new java.sql.Date(zahtjevDTO.getDatumZahtjeva().getTime()));
			ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
			ps.setInt(4, zahtjevDTO.getPredmetId());
			ps.setInt(5, zahtjevDTO.getNalogId());
			
			retVal = ps.executeUpdate() == 1;
			
			if(retVal) {
				conn.commit();
			} else {
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

	@Override
	public boolean deleteZahtjev(ZahtjevDTO zahtjevDTO) {
		boolean retVal = false;
		
		String query = "DELETE FROM zahtjev WHERE PredmetId = ? and NalogId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, zahtjevDTO.getPredmetId());
			ps.setInt(2, zahtjevDTO.getNalogId());
			
			retVal = ps.executeUpdate() == 1;
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}
		
		return retVal;
	}
	
	@Override
	public boolean deleteZahtjeve(PredmetDTO predmetDTO) {
		boolean retVal = false;
		
		String getBroj = "SELECT NalogId FROM zahtjev WHERE PredmetId = ?";
		String deleteZahtjeve = "DELETE FROM zahtjev WHERE PredmetId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(getBroj);
			ps.setInt(1, predmetDTO.getPredmetId());
			rs = ps.executeQuery();
			
			int broj = 0;
			while(rs.next()) {
				broj++;
			}
			if(broj == 0) {
				return true;
			}
			
			ps = conn.prepareStatement(deleteZahtjeve);
			ps.setInt(1, predmetDTO.getPredmetId());
			
			retVal = ps.executeUpdate() == broj;
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<ZahtjevDTO> getAllZahtjev() {
		ArrayList<ZahtjevDTO> retVals = new ArrayList<>();
		
		String query = "SELECT * FROM zahtjev WHERE DatumOdobrenja IS NULL";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				retVals.add(new ZahtjevDTO(rs.getInt(3), rs.getInt(1), rs.getInt(2), rs.getDate(4), rs.getDate(5)));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVals;
	}
	
	public ArrayList<PredmetDTO> getPredmeteSaZahtjevomZaDan(int nalogId, Date date) {
		ArrayList<PredmetDTO> retVals = new ArrayList<>();
		
		String getPredmetIdQuery = "SELECT PredmetId FROM zahtjev WHERE NalogId = ? and DatumZahtjeva = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(getPredmetIdQuery);
			ps.setInt(1, nalogId);
			ps.setDate(2, new java.sql.Date(date.getTime()));
			rs = ps.executeQuery();
			
			MySQLDAOFactory factory = new MySQLDAOFactory();
			PredmetDAO predmetDAO = factory.getPredmetDAO();
			
			while(rs.next()) {
				retVals.add(predmetDAO.getPredmet(rs.getInt(1)));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVals;
	}

}
