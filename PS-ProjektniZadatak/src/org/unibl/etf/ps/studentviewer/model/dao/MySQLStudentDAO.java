package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.unibl.etf.ps.studentviewer.logic.utility.dbutility.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;

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
				student = new StudentNaPredmetuDTO(rs.getInt(1), brojIndeksa,
						rs.getString(3), rs.getString(4));
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBUtility.close(rs, ps, conn);
		}
		return student;
	}

}
