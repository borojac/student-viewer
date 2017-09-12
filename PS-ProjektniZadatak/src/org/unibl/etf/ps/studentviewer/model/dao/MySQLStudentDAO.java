package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentZaElektrijaduDTO;

public class MySQLStudentDAO extends StudentDAO {

	@Override
	public StudentNaPredmetuDTO getStudentBy(String brojIndeksa) {
		StudentNaPredmetuDTO student = null;
		String query = "SELECT StudentId, BrojIndeksa, Ime, Prezime FROM student WHERE BrojIndeksa=?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setString(1, brojIndeksa);
			rs = ps.executeQuery();
			if (rs.next())
				student = new StudentNaPredmetuDTO(rs.getInt(1), brojIndeksa, rs.getString(3), rs.getString(4));
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBUtility.close(rs, ps, conn);
		}
		return student;
	}

	@Override
	public List<StudentZaElektrijaduDTO> getStudentiZaElektrijadu(int idNaloga, int idElektrijade,
			String nazivDiscipline) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean dodajStudentaZaElektrijadu(StudentZaElektrijaduDTO student, int idElektrijade,
			String nazivDiscipline) {
		String addTestQuery = "INSERT INTO ucestvuje VALUE (?, ?, ?, ?)";

		boolean retVal = false;

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(addTestQuery);

			ps.setString(1, nazivDiscipline);
			ps.setInt(2, idElektrijade);
			ps.setInt(3, student.getId());
			ps.setString(4, student.getNapomena());

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
	public boolean obrisiStudentaZaElektrijadu(int idStudenta) {
		boolean retVal = false;

		String query = "DELETE FROM ucestvuje WHERE StudentId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(query);
			ps.setInt(1, idStudenta);

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

	@Override
	public boolean azurirajStudentaZaElektrijadu(StudentZaElektrijaduDTO student) {
		boolean retVal = true;

		String updateTestQuery = "UPDATE ucestvuje SET Komentar=? WHERE StudentId=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(updateTestQuery);

			ps.setString(1, student.getNapomena());
			ps.setInt(2, student.getId());

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

}
