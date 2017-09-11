package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.logic.utility.dbutility.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;

public class MySQLDodatnaNastavaDAO implements DodatnaNastavaDAO {

	@Override
	public List<DodatnaNastavaDTO> dodatneNastave(int elektrijadaId, int nalogId) {
		List<DodatnaNastavaDTO> retVal = new ArrayList<DodatnaNastavaDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT NastavaId,Datum, Naziv, Napomena, ElektrijadaId, NalogId FROM dodatna_nastava WHERE ElektrijadaId =? AND NalogId =?";
				
		
		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, elektrijadaId);
			ps.setInt(2, nalogId);

			rs = ps.executeQuery();
			while (rs.next()) {
				retVal.add(new DodatnaNastavaDTO(rs.getInt(1),rs.getDate(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(5)));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBUtility.close(conn, rs, ps);
		}
		return retVal;
	}

	@Override
	public boolean azurirajDodatnuNastavu(DodatnaNastavaDTO dodatnaNastava) {
		boolean retVal = true;

		String updateTestQuery = "UPDATE dodatna_nastava SET Datum=?, Naziv=?, Napomena=?, ElektrijadaId=?, NalogId=? WHERE TestId=?";

//		Connection conn = null;
//		PreparedStatement ps = null;
//		CallableStatement cs = null;
//
//		try {
//
//			conn = DBUtility.open();
//			conn.setAutoCommit(false);
//			cs = conn.prepareCall(deleteStudentsQuery);
//			cs.setInt(1, test.getTestId());
//			cs.registerOutParameter(2, Types.BOOLEAN);
//			cs.executeUpdate();
//			retVal = cs.getBoolean(2);
//			
//			ps = conn.prepareStatement(updateTestQuery);
//			ps.setString(1, test.getNaziv());
//			ps.setDate(2, new java.sql.Date(test.getDatum().getTime()));
//			ps.setString(3, test.getNapomena());
//			ps.setInt(4, test.getProcenat());
//			ps.setInt(5, test.getPredmetId());
//			ps.setInt(6, test.getTestId());
//			retVal &= ps.executeUpdate() == 1;
//			
//			ps = conn.prepareStatement(addStudentsQuery);
//			if (test.getStudenti().size() > 0) {
//				for (StudentNaTestuDTO student : test.getStudenti()) {
//					ps.setInt(1, test.getTestId());
//					ps.setInt(2, student.getStudentId());
//					ps.setInt(3, student.getBrojBodova());
//					ps.setString(4, student.getKomentar());
//					retVal &= ps.executeUpdate() == 1;
//				}
//			}
//			
//			if (retVal) 
//				conn.commit();
//			else
//				throw new SQLException("Rollback needed!");
//			
//		} catch (SQLException e) {
//			try {
//				conn.rollback();
//			} catch (SQLException e1) {}
//			e.printStackTrace();
//		} finally {
//			try {
//				conn.setAutoCommit(true);
//			} catch (SQLException e) {}
//			DBUtility.close(conn, ps);
//		}

		return retVal;
	}

	@Override
	public boolean dodajDodatnuNastavu(DodatnaNastavaDTO dodatnaNastava) {
		String addTestQuery = "INSERT INTO dodatna_nastava VALUE (?, ?, ?, ?, ?)";

		boolean retVal = false;

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(addTestQuery);

			ps.setDate(1, new java.sql.Date(dodatnaNastava.getDatum().getTime()));
			ps.setString(2, dodatnaNastava.getNaziv());
			ps.setString(3, dodatnaNastava.getNapomena());
			ps.setInt(4, dodatnaNastava.getElektrijadaID());
			ps.setInt(5, dodatnaNastava.getNalogID());
			
			retVal = ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}
		return retVal;
	}

	@Override
	public boolean obrisiDodatnuNastavu(DodatnaNastavaDTO dodatnaNastava) {
		boolean retVal = false;

		String query = "DELETE FROM dodatna_nastava WHERE Datum=? AND Naziv=? AND ElektrijadaId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(query);
			ps.setDate(1, new java.sql.Date(dodatnaNastava.getDatum().getTime()));
			ps.setString(2, dodatnaNastava.getNaziv());
			ps.setInt(3, dodatnaNastava.getElektrijadaID());
			
			retVal = ps.executeUpdate() == 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

}
