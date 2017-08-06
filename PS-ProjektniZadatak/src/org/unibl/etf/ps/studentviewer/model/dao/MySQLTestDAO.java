package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.unibl.etf.ps.studentviewer.model.dto.StudentNaTestuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;
import org.unibl.etf.ps.studentviewer.utility.DBUtility;

public class MySQLTestDAO implements TestDAO {

	@Override
	public TestDTO getTest(int idTesta) {
		TestDTO retVal = null;

		String getTestQuery = "SELECT TestId, Naziv, Datum, Napomna, PredmetId"
				+ " FROM test WHERE TestId=?";
		String getStudentsQuery = "SELECT StudentId, BrojIndeksa, Ime, Prezime, BrojBodova, Komentar"
				+ " FROM izlazi_na INNER JOIN student USING(StudentId) WHERE TestId=?";
		Connection conn = null;
		ResultSet rs = null;
		try (PreparedStatement ps = conn.prepareStatement(getTestQuery)) {

			ps.setInt(1, idTesta);

			rs = ps.executeQuery();
			if (rs.next()) {
				retVal = new TestDTO(rs.getInt(1), rs.getString(2), rs.getDate(3),
						rs.getString(4), rs.getInt(5));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(rs);
		}
		List<StudentNaTestuDTO> students = new ArrayList<>();
		
		try (PreparedStatement ps = conn.prepareStatement(getStudentsQuery)) {
			
			ps.setInt(1, idTesta);
			rs = ps.executeQuery();
			while (rs.next()) {
				students.add(new StudentNaTestuDTO(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getInt(5), rs.getString(6)));
			}
			
			retVal.setStudenti(students);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs);
		}
		return retVal;
	}

	@Override
	public boolean updateTest(TestDTO test) {

		String updateTest = "UPDATE test SET Naziv=?, Datum=?, Napomena=? WHERE TestId=?";
		String getStudents = "SELECT StudentId FROM izlazi_na WHERE TestId=?";
		String deleteStudent = "DELETE FROM izlazi_na WHERE StudentId=?";
		String insertStudent = "INSERT INTO izlazi_na VALUE (?, ?, ?, ?)";
		List<Integer> students = new ArrayList<>();
		boolean retVal = false;
		ResultSet rs = null;
		Connection conn = null;
		try (PreparedStatement ps = conn.prepareStatement(getStudents)) {

			ps.setInt(1, test.getTestId());
			rs = ps.executeQuery();
			while (rs.next()) {
				students.add(rs.getInt(1));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}


		boolean deleted = true;
		for (Integer i : students) {
			boolean found = false;
			for (StudentNaTestuDTO student : test.getStudenti()) {
				if (i == student.getStudentId()) {
					found = true;
					break;
				}
			}
			if (!found) {

				try (PreparedStatement ps = conn.prepareStatement(deleteStudent)) {

					ps.setInt(1, i);
					deleted &= ps.executeUpdate() == 1;

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		boolean inserted = true;
		for (StudentNaTestuDTO student : test.getStudenti()) {
			boolean found = false;
			for (Integer i : students) {
				if (i == student.getStudentId()) {
					found = true;
					break;
				}
			}

			if (!found) {

				try (PreparedStatement ps = conn.prepareStatement(insertStudent)) {

					ps.setInt(1, student.getStudentId());
					ps.setInt(2, test.getTestId());
					ps.setInt(3, student.getBrojBodova());
					ps.setString(4, student.getKomentar());
					
					
					inserted &= ps.executeUpdate() == 1;

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		retVal = deleted & inserted;

		try (PreparedStatement ps = conn.prepareStatement(updateTest)) {

			ps.setString(1, test.getNaziv());
			ps.setDate(2, new java.sql.Date(test.getDatum().getTime()));
			ps.setString(3, test.getNapomena());
			ps.setInt(4, test.getTestId());
			
			retVal &= ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtility.close(conn, rs);
		
		return retVal;
	}

	@Override
	public boolean addTest(TestDTO test) {
		String addTestQuery = "INSERT INTO test VALUE (null, ?, ?, ?, ?)";
		String updateStudentsQuery = "INSERT INTO izlazi_na VALUE (?, ?, ?, ?)";

		boolean retVal = false;

		Connection conn = null;
		try (PreparedStatement ps = conn.prepareStatement(addTestQuery)) {

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
			ex.printStackTrace();
		} finally {
			DBUtility.close(conn);
		}
		return retVal;
	}

	@Override
	/*
	 * Samo se moze obrisati prazan test (nema unesenih studenata)
	 * Ako ima unesenih studenata na testu, potrebno ih je prvo ukloniti prije brisanja
	 */
	public boolean deleteTest(TestDTO test) {
		boolean retVal = false;

		String query = "DELETE FROM test WHERE TestId=?";

		Connection conn = null;

		try (PreparedStatement ps = conn.prepareStatement(query)) {

			retVal = ps.executeUpdate() == 1;

		} catch (SQLException e) {
			// Ima studenata na ispitu pa se ne moze obrisati
			e.printStackTrace();
		} finally {
			DBUtility.close(conn);
		}

		return retVal;
	}

	@Override
	public List<StudentNaTestuDTO> pretraga(String parameter) {
		List<StudentNaTestuDTO> retVals = new ArrayList<>();
		String query = "SELECT StudentId, BrojIndeksa, Ime, Prezime, BrojBodova, Komentar"
				+ " FROM student INNER JOIN izlazi_na USING (StudentId)"
				+ " WHERE BrojIndeksa LIKE '?%' OR Ime LIKE '?%' OR Prezime LIKE '?%'";
		Connection conn = null;
		ResultSet rs = null;
		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setString(1, parameter);
			ps.setString(2, parameter);
			ps.setString(3, parameter);

			rs = ps.executeQuery();

			while (rs.next()) {
				retVals.add(new StudentNaTestuDTO(rs.getInt(1), rs.getString(2), 
						rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs);
		}
		return retVals;
	}

	@Override
	public List<StudentNaTestuDTO> filter(int brojBodova, char diskriminator) {
		List<StudentNaTestuDTO> retVals = new ArrayList<>();
		String query = "SELECT StudentId, BrojIndeksa, Ime, Prezime, BrojBodova, Komentar"
				+ " FROM student INNER JOIN izlazi_na USING(StudentId) "
				+ "WHERE BrojBodova " + diskriminator + " ?";
		Connection conn = null;
		ResultSet rs = null;
		try (PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, brojBodova);

			rs = ps.executeQuery();

			while (rs.next()) {
				retVals.add(new StudentNaTestuDTO(rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs);
		}
		return retVals;
	}

}
