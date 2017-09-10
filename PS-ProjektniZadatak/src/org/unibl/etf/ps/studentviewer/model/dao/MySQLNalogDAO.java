package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.logic.utility.dbutility.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class MySQLNalogDAO implements NalogDAO {

	@Override
	public NalogDTO getNalog(String korisnickoIme, String lozinka) {
		NalogDTO retVal = null;
		
		String getNalogQuery = "SELECT NalogId, Ime, Prezime, KorisnickoIme, Lozinka, TipNaloga"
				+ " FROM nalog WHERE KorisnickoIme = ? and Lozinka = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(getNalogQuery);
			ps.setString(1, korisnickoIme);
			ps.setString(2, lozinka);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				retVal = new NalogDTO(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), (rs.getString(6)).charAt(0));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		retVal.setPredmeti(getPredmeteNaNalogu(retVal.getNalogId()));
		
		return retVal;
	}

	@Override
	public boolean addNalog(NalogDTO nalog) {
		boolean retVal = false;
		
		String query = "INSERT INTO nalog VALUE (null, ?, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setString(1, nalog.getIme());
			ps.setString(2, nalog.getPrezime());
			ps.setString(3, nalog.getKorisnickoIme());
			ps.setString(4, nalog.getLozinka());
			ps.setString(5, String.valueOf(nalog.getTipNaloga()));
			ps.setObject(6, null, java.sql.Types.BLOB);
			
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
	public boolean updateNalog(NalogDTO nalog) {
		boolean retVal = false;
		
		String query = "UPDATE nalog SET Ime = ?, Prezime = ?, KorisnickoIme = ?, Lozinka = ?, TipNaloga = ?, StanjeGT = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setString(1, nalog.getIme());
			ps.setString(2, nalog.getPrezime());
			ps.setString(3, nalog.getKorisnickoIme());
			ps.setString(4, nalog.getLozinka());
			ps.setString(5, String.valueOf(nalog.getTipNaloga()));
			ps.setObject(6, null, java.sql.Types.BLOB);
			
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
	public boolean addPredmet(PredmetDTO predmet, NalogDTO nalog) {
		boolean retVal = false;
		
		String query = "INSERT INTO predaje VALUE (?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, nalog.getNalogId());
			ps.setInt(2, predmet.getPredmetId());
			ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
			
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
	public boolean removePredmet(PredmetDTO predmet, NalogDTO nalog) {
		boolean retVal = false;
		
		String query = "DELETE FROM predaje WHERE PredmetId = ? and NalogId =";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, predmet.getPredmetId());
			ps.setInt(2, nalog.getNalogId());
			
			retVal = ps.executeUpdate() == 1;
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<PredmetDTO> getPredmeteNaNalogu(int nalogId) {
		ArrayList<PredmetDTO> retVals = new ArrayList<>();
		
		String query = "SELECT PredmetId FROM predaje WHERE NalogId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MySQLDAOFactory predmetFactory = new MySQLDAOFactory();
		PredmetDAO predmetDAO = predmetFactory.getPredmetDAO();
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, nalogId);
			rs = ps.executeQuery();
			
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