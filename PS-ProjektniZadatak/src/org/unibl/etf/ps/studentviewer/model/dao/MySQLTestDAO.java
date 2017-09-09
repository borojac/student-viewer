package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.unibl.etf.ps.studentviewer.logic.utility.dbutility.ConnectionPool;
import org.unibl.etf.ps.studentviewer.logic.utility.dbutility.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;
/**
 * Nezavrseno, netestirano
 * @author Nemanja Stokuca
 *
 */
public class MySQLTestDAO implements TestDAO {

	@Override
	public TestDTO getTest(int idTesta) {
		TestDTO retVal = null;

		String getTestQuery = "SELECT TestId, Naziv, Datum, Napomna, PredmetId"
				+ " FROM test WHERE TestId=?";
		String getStudentsQuery = "SELECT StudentId, BrojIndeksa, Ime, Prezime, BrojBodova, Komentar"
				+ " FROM izlazi_na INNER JOIN student USING(StudentId) WHERE TestId=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getTestQuery);
			ps.setInt(1, idTesta);

			rs = ps.executeQuery();
			if (rs.next()) {
				retVal = new TestDTO(rs.getInt(1), rs.getString(2), rs.getDate(3),
						rs.getString(4), rs.getInt(5), rs.getInt(6));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<StudentNaTestuDTO> students = new ArrayList<>();

		try {
			ps = conn.prepareStatement(getStudentsQuery);
			ps.setInt(1, idTesta);
			rs = ps.executeQuery();
			while (rs.next()) {
				students.add(new StudentNaTestuDTO(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getInt(5), rs.getString(6)));
			}
			if (retVal != null)
				retVal.setStudenti(students);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		return retVal;
	}

	@Override
	public boolean updateTest(TestDTO test) {
		boolean retVal = true;

		String updateTestQuery = "UPDATE test SET Naziv=?, Datum=?, Napomena=?, Procenat=?, PredmetId=? WHERE TestId=?";
		String addStudentsQuery = "INSERT INTO izlazi_na VALUES(?, ?, ?, ?)";
		String deleteStudentsQuery = "DELETE FROM izlazi_na WHERE TestId=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(deleteStudentsQuery);
			ps.setInt(1, test.getTestId());
			retVal = ps.executeUpdate() > 0;
			ps = conn.prepareStatement(updateTestQuery);
			ps.setString(1, test.getNaziv());
			ps.setDate(2, new java.sql.Date(test.getDatum().getTime()));
			ps.setString(3, test.getNapomena());
			ps.setInt(4, test.getProcenat());
			ps.setInt(5, test.getPredmetId());
			ps.setInt(6, test.getTestId());
			retVal &= ps.executeUpdate() == 1;
			ps = conn.prepareStatement(addStudentsQuery);
			for (StudentNaTestuDTO student : test.getStudenti()) {
				ps.setInt(1, test.getTestId());
				ps.setInt(2, student.getStudentId());
				ps.setInt(3, student.getBrojBodova());
				ps.setString(4, student.getKomentar());
				retVal &= ps.executeUpdate() == 1;
			}
			
			if (retVal) 
				conn.commit();
			else
				conn.rollback();
			
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public boolean addTest(TestDTO test) {
		String addTestQuery = "INSERT INTO test VALUE (null, ?, ?, ?, ?)";
		String updateStudentsQuery = "INSERT INTO izlazi_na VALUE (?, ?, ?, ?)";

		boolean retVal = false;

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(addTestQuery);

			ps.setInt(1, test.getPredmetId());
			ps.setString(2, test.getNaziv());
			Date datum = test.getDatum();
			ps.setDate(3, new java.sql.Date(datum.getTime()));
			ps.setString(4, test.getNapomena());

			retVal = ps.executeUpdate() == 1;

			for (StudentNaTestuDTO student : test.getStudenti()) {
				try (PreparedStatement ps2 = conn.prepareStatement(updateStudentsQuery)) {
					ps2.setInt(1, test.getTestId());
					ps2.setInt(2, student.getStudentId());
					ps2.setInt(3, student.getBrojBodova());
					ps2.setString(4, student.getKomentar());
					retVal &= ps2.executeUpdate() == 1;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}
		return retVal;
	}

	@Override
	/*
	 * Samo se moze obrisati prazan test (nema unesenih studenata)
	 * Ako ima unesenih studenata na testu, potrebno ih je prvo otvoriti test i 
	 * ukloniti ih prije brisanja samog testa
	 */
	public boolean deleteTest(TestDTO test) {
		boolean retVal = false;

		String query = "DELETE FROM test WHERE TestId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(query);
			ps.setInt(1, test.getTestId());

			retVal = ps.executeUpdate() == 1;

		} catch (SQLException e) {
			// Ima studenata na ispitu pa se ne moze obrisati
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public List<StudentNaTestuDTO> getAllStudents(int idTesta) {
		List<StudentNaTestuDTO> retVals = new ArrayList<>();
		String query = "SELECT StudentId, BrojIndeksa, Ime, Prezime, BrojBodova, Komentar"
				+ " FROM student INNER JOIN izlazi_na USING(StudentId) WHERE TestId = ?";

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(query);
			ps.setInt(1, idTesta);

			rs = ps.executeQuery();

			while (rs.next()) {
				StudentNaTestuDTO tmp = new StudentNaTestuDTO(rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
				retVals.add(tmp);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}

		return retVals;
	}

	@Override
	public List<TestDTO> getAllTests() {
		List<TestDTO> retVals = new ArrayList<>();

		String query = "SELECT * FROM test";

		Connection conn = null;
		ResultSet rs = null;
		Statement s = null;

		try {
			conn = DBUtility.open();

			s = conn.createStatement();			
			rs = s.executeQuery(query);

			while (rs.next()) {
				TestDTO tmp = new TestDTO(rs.getInt(1), rs.getString(3), new Date(rs.getDate(4).getTime()), rs.getString(5), rs.getInt(6), rs.getInt(2));
				tmp.setStudenti(getAllStudents(tmp.getTestId()));
				retVals.add(tmp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(rs, s, conn);
		}


		return retVals;
	}

	@Override
	public List<StudentNaTestuDTO> getStudentsOnPredmet(int predmetId) {
		List<StudentNaTestuDTO> retVals = new ArrayList<>();
		
		String query = "SELECT StudentId, BrojIndeksa, Ime, Prezime FROM slusa INNER JOIN student USING(StudentId) WHERE PredmetId=?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, predmetId);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				StudentNaTestuDTO tmp = new StudentNaTestuDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 0, "");
				retVals.add(tmp);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBUtility.close(rs, ps, conn);
		}
		
		return retVals;
	}

}
