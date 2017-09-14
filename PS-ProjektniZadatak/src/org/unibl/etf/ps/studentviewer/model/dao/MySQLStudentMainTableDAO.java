package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;

public class MySQLStudentMainTableDAO implements StudentMainTableDAO {

	@Override
	public ArrayList<StudentMainTableDTO> getAllStudents() {
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
				StudentMainTableDTO student = new StudentMainTableDTO(rs.getString(2), rs.getString(3), rs.getString(4));
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

}
