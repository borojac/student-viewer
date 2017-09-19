package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Statement;
import java.sql.Types;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

import java.sql.CallableStatement;
/**
 * Nezavrseno, netestirano
 * @author Nemanja Stokuca
 *
 */
public class MySQLTestDAO implements TestDAO {

	@Override
	public TestDTO getTest(int idTesta) {
		TestDTO retVal = null;

		String getTestQuery = "SELECT * FROM test WHERE TestId=?";
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
	public boolean updateTest(TestDTO test) throws SQLException {
		boolean retVal = true;

		String updateTestQuery = "UPDATE test SET Naziv=?, Datum=?, Napomena=?, Procenat=?, PredmetId=? WHERE TestId=?";
		String addStudentsQuery = "INSERT INTO izlazi_na VALUES(?, ?, ?, ?)";
		String deleteStudentsQuery = "{call update_test_remove_students(?, ?)}";

		Connection conn = null;
		PreparedStatement ps = null;
		CallableStatement cs = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			cs = conn.prepareCall(deleteStudentsQuery);
			cs.setInt(1, test.getTestId());
			cs.registerOutParameter(2, Types.BOOLEAN);
			if (cs.executeUpdate() > 0)
				retVal &= cs.getBoolean(2);
			
			ps = conn.prepareStatement(updateTestQuery);
			ps.setString(1, test.getNaziv());
			ps.setDate(2, new java.sql.Date(test.getDatum().getTime()));
			ps.setString(3, test.getNapomena());
			ps.setInt(4, test.getProcenat());
			ps.setInt(5, test.getPredmetId());
			ps.setInt(6, test.getTestId());
			retVal &= ps.executeUpdate() == 1;
			
			DBUtility.close(ps);
			
			ps = conn.prepareStatement(addStudentsQuery);
			if (test.getStudenti().size() > 0) {
				for (StudentNaTestuDTO student : test.getStudenti()) {
					ps.setInt(1, test.getTestId());
					ps.setInt(2, student.getStudentId());
					ps.setInt(3, student.getBrojBodova());
					ps.setString(4, student.getKomentar());
					retVal &= ps.executeUpdate() == 1;
				}
			}
			
			if (retVal) 
				conn.commit();
			else
				throw new SQLException("Ažuriranje nije uspjelo. Pokušajte ponovo.");
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {}
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public boolean addTest(TestDTO test) throws SQLException {
		String addTestQuery = "{CALL dodaj_test(?, ?, ?, ?, ?, ?)}";
		String updateStudentsQuery = "INSERT INTO izlazi_na VALUE (?, ?, ?, ?)";

		boolean retVal = true;

		Connection conn = null;
		PreparedStatement ps = null;
		CallableStatement cs = null;

		try {
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			cs = conn.prepareCall(addTestQuery);
			cs.setString(1, test.getNaziv());
			Date datum = test.getDatum();
			cs.setDate(2, new java.sql.Date(datum.getTime()));
			cs.setString(3, test.getNapomena());
			cs.setInt(4, test.getProcenat());
			cs.setInt(5, test.getPredmetId());
			cs.registerOutParameter(6, Types.INTEGER);
			
			cs.executeUpdate();
			int testId = cs.getInt(6);
			test.setTestId(testId);
			retVal &= testId > 0;

			for (StudentNaTestuDTO student : test.getStudenti()) {
				ps = conn.prepareStatement(updateStudentsQuery);
				ps.setInt(1, test.getTestId());
				ps.setInt(2, student.getStudentId());
				ps.setInt(3, student.getBrojBodova());
				ps.setString(4, student.getKomentar());
				retVal &= ps.executeUpdate() == 1;
				
			}
			if (retVal)
				conn.commit();
			else
				throw new SQLException("Dodavanje testa nije uspjelo. Pokušajte ponovo!");

		} catch (SQLException ex) {
			try {
				conn.rollback();
			} catch (SQLException e) {}
			ex.printStackTrace();
			throw ex;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {}
			DBUtility.close(conn, ps, cs);
		}
		return retVal;
	}

	@Override
	/*
	 * Samo se moze obrisati prazan test (nema unesenih studenata)
	 * Ako ima unesenih studenata na testu, potrebno ih je prvo otvoriti test i 
	 * ukloniti ih prije brisanja samog testa
	 */
	public boolean deleteTest(TestDTO test) throws SQLException {
		boolean retVal = false;

		String query = "DELETE FROM test WHERE TestId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(query);
			ps.setInt(1, test.getTestId());

			retVal = ps.executeUpdate() == 1;
			if (!retVal) {
				throw new SQLException("Test ne može biti obrisan. Lista studenata na testu mora biti prazna da bi se test mogao brisati.");
			}

		} catch (SQLException e) {
			// Ima studenata na ispitu pa se ne moze obrisati
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public List<StudentNaTestuDTO> getStudentsOnTest(int idTesta) {
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
	public List<TestDTO> getAllTests(int predmetId) {
		List<TestDTO> retVals = new ArrayList<>();

		String query = "SELECT * FROM test WHERE PredmetId=?";

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(query);
			ps.setInt(1, predmetId);
			
			rs = ps.executeQuery();

			while (rs.next()) {
				TestDTO tmp = new TestDTO(rs.getInt(1), rs.getString(2), 
						new Date(rs.getDate(3).getTime()), rs.getString(4),
						rs.getInt(5), rs.getInt(6));
				tmp.setStudenti(getStudentsOnTest(tmp.getTestId()));
				retVals.add(tmp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(rs, ps, conn);
		}


		return retVals;
	}

	@Override
	public List<StudentNaTestuDTO> getStudentsOnPredmet(TestDTO test) {
		List<StudentNaTestuDTO> retVals = new ArrayList<>();
		
		String query = "SELECT StudentId, BrojIndeksa, Ime, Prezime FROM slusa"
				+ " INNER JOIN student USING(StudentId)"
				+ " WHERE PredmetId=?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, test.getPredmetId());
			rs = ps.executeQuery();
			
			while (rs.next()) {
				StudentNaTestuDTO tmp = new StudentNaTestuDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 0, "");
				retVals.add(tmp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(rs, ps, conn);
		}
		
		return retVals;
	}

	@Override
	public boolean verifyStudent(String brojIndeksa, int idTesta) throws SQLException {
		boolean retVal = false;
		String call = "{CALL verify_student(?, ?, ?)}";
		
		Connection conn = null;
		CallableStatement cs = null;
		try {
			conn = DBUtility.open();
			cs = conn.prepareCall(call);
			cs.setString(1, brojIndeksa);
			cs.setInt(2, idTesta);
			cs.registerOutParameter(3, Types.BOOLEAN);
			cs.executeUpdate();
			retVal = cs.getBoolean(3);
		} catch (SQLException e) {
			retVal = false;
			e.printStackTrace();
			throw e;
		} finally {
			DBUtility.close(cs, conn);
		}
		return retVal;
	}

}
