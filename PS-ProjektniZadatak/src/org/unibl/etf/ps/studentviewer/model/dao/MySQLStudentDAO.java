package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.DodatnaNastavaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentMainTableDTO;
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

	@SuppressWarnings("resource")
	@Override
	public List<StudentZaElektrijaduDTO> getIzborStudentaZaElektrijadu(int idNaloga, String disciplina, int idElektrijade) {
		List<StudentZaElektrijaduDTO> retVal = new ArrayList<StudentZaElektrijaduDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT DISTINCT s.StudentId, s.BrojIndeksa, s.Ime, s.Prezime FROM student s INNER JOIN slusa USING(StudentId) WHERE PredmetId IN (SELECT PredmetId FROM predaje WHERE NalogId=?)";
		String query2 = "SELECT Komentar from ucestvuje WHERE Naziv=? AND ElektrijadaId=? AND StudentId=?";
		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, idNaloga);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				retVal.add(new StudentZaElektrijaduDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), ""));
			}

			ps = conn.prepareStatement(query2);
			for (int i=0; i<retVal.size(); i++){
				ps.setString(1, disciplina);
				ps.setInt(2, idElektrijade);
				ps.setInt(3, retVal.get(i).getId());
				rs = ps.executeQuery();
				String odg = rs.getString(1);
				if (!odg.isEmpty())
					retVal.get(i).setKomentar(odg);
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
			ps.setString(4, student.getKomentar());

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

			ps.setString(1, student.getKomentar());
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

	/* Stankovic */
	@Override
	public boolean dodajStudentaUListu(StudentMainTableDTO student) {
		boolean retVal = true;

		String addStudentQuery = "INSERT INTO student VALUE (?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(addStudentQuery);

			ps.setInt(1, 0);
			ps.setString(2, student.getBrojIndeksa());
			ps.setString(3, student.getIme());
			ps.setString(4, student.getPrezime());

			retVal &= ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			try {
				retVal = false;
				conn.rollback();
			} catch (SQLException e) {

			}
		} finally {
			DBUtility.close(conn, ps);

		}
		return retVal;
	}

	@Override
	public boolean obrisiStudentaSaPredmeta(int studentID, PredmetDTO predmet) {
		boolean retVal = true;

		String deleteStudentQuery = "DELETE FROM slusa WHERE StudentId=? AND PredmetId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(deleteStudentQuery);
			ps.setInt(1, studentID);
			ps.setInt(2, predmet.getPredmetId());

			retVal &= ps.executeUpdate() == 1;

		} catch (SQLException e) {
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
	public boolean azurirajStudentaUListi(StudentMainTableDTO student, String stariIndeks) {
		boolean retVal = true;

		String updateTestQuery = "UPDATE student SET BrojIndeksa=?, Ime=?, Prezime=? WHERE BrojIndeksa=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(updateTestQuery);

			ps.setString(1, student.getBrojIndeksa());
			ps.setString(2, student.getIme());
			ps.setString(3, student.getPrezime());
			ps.setString(4, stariIndeks);

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
	public boolean dodajStudentaNaPredmet(StudentMainTableDTO student, PredmetDTO predmet) {
		boolean retVal = true;

		String addStudentQuery = "INSERT INTO slusa VALUE (?, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(addStudentQuery);

			ps.setInt(1, getStudentBy(student.getBrojIndeksa()).getStudentId());
			ps.setInt(2, predmet.getPredmetId());
			ps.setNull(3, 0);
			ps.setNull(4, 0);
			String komentar = student.getKomentar().trim();
			if(!"".equals(komentar)) {
				ps.setString(5, komentar);
			}
			else {
				ps.setNull(5, 0);
			}
			retVal &= ps.executeUpdate() == 1;
		} catch (SQLException ex) {
			try {
				retVal = false;
				conn.rollback();
			} catch (SQLException e) {

			}
		} finally {
			DBUtility.close(conn, ps);

		}
		return retVal;
	}

	@Override
	public boolean azurirajStudentaNaPredmetu(StudentMainTableDTO student, PredmetDTO predmet) {
		boolean retVal = true;

		String updateTestQuery = "UPDATE slusa SET Komentar=? WHERE StudentId=? AND PredmetId=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(updateTestQuery);

			String komentar = student.getKomentar().trim();
			if("".equals(komentar))
				ps.setNull(1, 0);
			else
				ps.setString(1,komentar);
			ps.setInt(2, student.getId());
			ps.setInt(3, predmet.getPredmetId());

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
	/* Stankovic end */

	@Override
	public List<StudentZaElektrijaduDTO> getStudentiZaElektrijadu(String disciplina, int idElektrijade) {
		List<StudentZaElektrijaduDTO> retVal = new ArrayList<StudentZaElektrijaduDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT StudentId, BrojIndeksa, Ime, Prezime, Komentar FROM student INNER JOIN ucestvuje u USING(StudentId) WHERE u.ElektrijadaId=? AND u.Naziv=?";
		try {

			
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, idElektrijade);
			ps.setString(2, disciplina);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				retVal.add(new StudentZaElektrijaduDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(3), rs.getString(4)));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		return retVal;
	}

	

}
