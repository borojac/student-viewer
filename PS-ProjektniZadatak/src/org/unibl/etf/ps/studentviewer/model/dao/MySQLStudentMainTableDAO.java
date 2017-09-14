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

		String getAllStudentsQuerry = "Select * FROM student";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getAllStudentsQuerry);
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new StudentMainTableDTO(rs.getString(2), rs.getString(3), rs.getString(4)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		return list;
	}

}
