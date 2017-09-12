package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;

public class MySQLDodatnaNastavaDAO implements DodatnaNastavaDAO {

	@Override
	public List<DodatnaNastavaDTO> getDodatneNastave(int elektrijadaId, int nalogId, String naziv) {
		List<DodatnaNastavaDTO> retVal = new ArrayList<DodatnaNastavaDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT NastavaId,DatumVrijeme, Napomena, NazivTeme, NalogId, Naziv, ElektrijadaId FROM dodatna_nastava WHERE ElektrijadaId =? AND NalogId =? AND Naziv=?";

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, elektrijadaId);
			ps.setInt(2, nalogId);
			ps.setString(3, naziv);

			rs = ps.executeQuery();
			while (rs.next()) {
				retVal.add(new DodatnaNastavaDTO(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getString(6), rs.getInt(7)));
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
	public boolean azurirajDodatnuNastavu(DodatnaNastavaDTO dodatnaNastava) {
		boolean retVal = true;

		String updateTestQuery = "UPDATE dodatna_nastava SET DatumVrijeme=?, Napomena=?, NazivTeme=? WHERE NastavaId=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(updateTestQuery);

			java.sql.Timestamp timestamp = new java.sql.Timestamp(dodatnaNastava.getDatum().getTime());
			ps.setTimestamp(1, timestamp);
			ps.setString(2, dodatnaNastava.getNapomena());
			ps.setString(3, dodatnaNastava.getNazivTeme());
			ps.setInt(4, dodatnaNastava.getNastavaId());

			retVal &= ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBUtility.close(conn, ps);

			retVal = true;
		}
		return retVal;
	}

	@Override
	public boolean dodajDodatnuNastavu(DodatnaNastavaDTO dodatnaNastava) {
		String addTestQuery = "INSERT INTO dodatna_nastava VALUE (?, ?, ?, ?, ?,?,?)";

		boolean retVal = false;

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(addTestQuery);

			java.sql.Timestamp timestamp = new java.sql.Timestamp(dodatnaNastava.getDatum().getTime());

			ps.setInt(1, dodatnaNastava.getNastavaId());
			ps.setTimestamp(2, timestamp);
			ps.setString(3, dodatnaNastava.getNapomena());
			ps.setString(4, dodatnaNastava.getNazivTeme());
			ps.setInt(5, dodatnaNastava.getNalogId());
			ps.setString(6, dodatnaNastava.getNaziv());
			ps.setInt(7, dodatnaNastava.getElektrijadaId());

			retVal &= ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			ex.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
				retVal = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBUtility.close(conn, ps);

		}
		return retVal;
	}

	@Override
	public boolean obrisiDodatnuNastavu(int idDodatneNastave) {
		boolean retVal = false;

		String query = "DELETE FROM dodatna_nastava WHERE NastavaId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(query);
			ps.setInt(1, idDodatneNastave);

			retVal &= ps.executeUpdate() == 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
				retVal = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBUtility.close(conn, ps);

		}

		return retVal;
	}

}
