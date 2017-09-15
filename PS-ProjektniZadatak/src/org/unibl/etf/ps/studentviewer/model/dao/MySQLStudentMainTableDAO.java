package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class MySQLStudentMainTableDAO implements StudentMainTableDAO {

	public ArrayList<StudentMainTableDTO> getAllStudentsOnPredmet(PredmetDTO predmet) {
		String getAllStudentsIdsQuerry = "SELECT StudentId FROM slusa WHERE PredmetId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList<Integer> ids = new ArrayList<Integer>();

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getAllStudentsIdsQuerry);
			ps.setInt(1, predmet.getPredmetId());
			rs = ps.executeQuery();

			while (rs.next()) {
				ids.add(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}

		ArrayList<StudentMainTableDTO> list = new ArrayList<StudentMainTableDTO>();

		String getAllStudentsQuerry = "SELECT * FROM student WHERE StudentId=?";
		for (int i = 0; i < ids.size(); i++) {

			try {

				conn = DBUtility.open();
				ps = conn.prepareStatement(getAllStudentsQuerry);
				ps.setInt(1, ids.get(i));
				rs = ps.executeQuery();

				while (rs.next()) {
					StudentMainTableDTO student = new StudentMainTableDTO(rs.getString(2), rs.getString(3),
							rs.getString(4));
					setCommentForStudent(student, rs.getInt(1));
					list.add(student);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBUtility.close(conn, rs, ps);
			}
		}
		return list;

	}

	@Override
	public ArrayList<StudentMainTableDTO> getAllStudents(PredmetDTO predmet) {
		ArrayList<StudentMainTableDTO> list = new ArrayList<StudentMainTableDTO>();

		String getAllStudentsQuerry = "SELECT * FROM student";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getAllStudentsQuerry);
			rs = ps.executeQuery();

			while (rs.next()) {
				StudentMainTableDTO student = new StudentMainTableDTO(rs.getString(2), rs.getString(3),
						rs.getString(4));
				student.setId(rs.getInt(1));
				setCommentForStudent(student, rs.getInt(1));
				list.add(student);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		return list;
	}

	public void setCommentForStudent(StudentMainTableDTO student, int id) {
		String getCommentQuerry = "SELECT Komentar FROM slusa WHERE StudentId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getCommentQuerry);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				String s = rs.getString(1);
				if (s == null)
					student.setKomentar("");
				else
					student.setKomentar(s);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}

	}
	
	public String getStateOfMainTable(PredmetDTO predmet, NalogDTO nalog) {
		String getStateOfMainTableQuerry = "SELECT StanjeGT FROM predaje WHERE NalogId=? AND PredmetId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = null;
		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getStateOfMainTableQuerry);
			ps.setInt(1, nalog.getNalogId());
			ps.setInt(2, predmet.getPredmetId());
			rs = ps.executeQuery();

			if (rs.next()) {
				result = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		return result;
	}
	
	public boolean setStateOfMainTable(PredmetDTO predmet, NalogDTO nalog, String stanje) {
		String setStateOfMainTableQuerry = "UPDATE predaje SET StanjeGT=? WHERE NalogId=? AND PredmetId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		boolean retVal = false;
		
		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(setStateOfMainTableQuerry);
			ps.setString(1, stanje);
			ps.setInt(2, nalog.getNalogId());
			ps.setInt(3, predmet.getPredmetId());

			retVal = retVal &= ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		return retVal;
	}

}
