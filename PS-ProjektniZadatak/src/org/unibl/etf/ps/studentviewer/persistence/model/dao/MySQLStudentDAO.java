package org.unibl.etf.ps.studentviewer.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.DodatnaNastavaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentMainTableDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.StudentZaElektrijaduDTO;

public class MySQLStudentDAO implements StudentDAO {

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
		String query2 = "SELECT Komentar FROM ucestvuje WHERE Naziv=? AND ElektrijadaId=? AND StudentId=?";
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
				String odg = "";
				while(rs.next())
					odg = rs.getString(1);
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

			retVal = ps.executeUpdate() == 1;
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

			retVal = ps.executeUpdate() == 1;

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

			retVal = ps.executeUpdate() == 1;
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
	public boolean obrisiStudentaSaPredmeta(int studentID, int predmetID) {
		boolean retVal = true;

		String deleteStudentQuery = "DELETE FROM slusa WHERE StudentId=? AND PredmetId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(deleteStudentQuery);
			ps.setInt(1, studentID);
			ps.setInt(2, predmetID);

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
			ps.setInt(2, student.getStudentId());
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
	
	@Override
	public boolean obrisiStudentaIzListe(String brojIndeksa) {
		boolean retVal = true;

		String deleteStudentQuery = "DELETE FROM student WHERE BrojIndeksa=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(deleteStudentQuery);
			ps.setString(1, brojIndeksa.trim());


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
	public String[][] getDataOfAllStudentsFromStudentDatabaseTable() {
		String getAllStudentsQuerry = "SELECT * FROM student";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList <String[]> studenti = new ArrayList<>();

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getAllStudentsQuerry);
			rs = ps.executeQuery();

			while (rs.next()) {
				String[] tmp = new String[3];
				tmp[0] = rs.getString(2);
				tmp[1] = rs.getString(3);
				tmp[2] = rs.getString(4);
				studenti.add(tmp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		String[][] retValue = new String[studenti.size()][3];
		for(int i = 0; i < studenti.size(); i++)
			retValue[i] = studenti.get(i);
		return retValue;
	}

	@Override
	public int[] listaPredmetIDNaKojimaJeStudent(int studentId) {
		String getAllStudentsQuerry = "SELECT PredmetId FROM slusa WHERE StudentId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList <Integer> predmetIds = new ArrayList<>();

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getAllStudentsQuerry);
			ps.setInt(1, studentId);
			rs = ps.executeQuery();

			while (rs.next()) {
				predmetIds.add(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		int[] retValue = new int[predmetIds.size()];
		for(int i = 0; i < predmetIds.size(); i++)
			retValue[i] = predmetIds.get(i);
		return retValue;
	}

	@Override
	public ArrayList<StudentMainTableDTO> studentiKojiNisuNaPredmetu(int predmetID) {
String getAllStudentsQuerry = "select * from student where StudentId not in (select StudentId from slusa where PredmetId=?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ArrayList <StudentMainTableDTO> studenti = new ArrayList<>();

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getAllStudentsQuerry);
			ps.setInt(1, predmetID);
			rs = ps.executeQuery();

			while (rs.next()) {
				StudentMainTableDTO student = new StudentMainTableDTO(rs.getString(2), rs.getString(3), rs.getString(4));
				student.setStudentId(rs.getInt(1));
				studenti.add(student);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}

		return studenti;
	}

	@Override
	public boolean obrisiStudentaSaSvihTestova(int studentId) {
		boolean retVal = true;

		String deleteStudentQuery = "DELETE FROM izlazi_na WHERE studentId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(deleteStudentQuery);
			ps.setInt(1, studentId);


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
	public boolean obrisiStudentaSaElektrijade(int studentId) {
		boolean retVal = true;

		String deleteStudentQuery = "DELETE FROM slusa WHERE studentId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(deleteStudentQuery);
			ps.setInt(1, studentId);


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
	public boolean obrisiStudentaSaSvihPredmeta(int studentId) {
		boolean retVal = true;

		String deleteStudentQuery = "DELETE FROM ucestvuje WHERE studentId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(deleteStudentQuery);
			ps.setInt(1, studentId);


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
				retVal.add(new StudentZaElektrijaduDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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
	public boolean gradeStudent(int studentId, int predmetId, int grade) {
		boolean retVal = false;
		String query = "UPDATE slusa SET Ocjena=?, DatumPolaganja=? "
				+ "WHERE StudentId=? AND PredmetId=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, grade);
			ps.setDate(2, new java.sql.Date(new Date().getTime()));
			ps.setInt(3, studentId);
			ps.setInt(4, predmetId);
			retVal = ps.executeUpdate() == 1;
			if (!retVal)
				throw new SQLException();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Gre�ka sa serverom. Student nije ocjenjen.", "Gre�ka",
					JOptionPane.ERROR_MESSAGE, null);
			e.printStackTrace();
			retVal = false;
		} finally {
			DBUtility.close(ps, conn);
		}
		return retVal;
	}

	@Override
	public boolean hasGrade(int studentId, int predmetId) {
		boolean retVal = true;
		String query = "SELECT Ocjena IS NOT NULL "
				+ "FROM slusa "
				+ "WHERE StudentId=? AND PredmetId=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, studentId);
			ps.setInt(2, predmetId);
			rs = ps.executeQuery();
			if (rs.next()) {
				retVal = rs.getBoolean(1);
			}
		} catch (SQLException e) {
			retVal = false;
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		return retVal;
	}

	@Override
	public boolean recallGrade(int studentId, int predmetId) {
		boolean retVal = false;
		String query = "UPDATE slusa SET Ocjena=null, DatumPolaganja=null "
				+ "WHERE StudentId=? AND PredmetId=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, studentId);
			ps.setInt(2, predmetId);
			retVal = ps.executeUpdate() == 1;
		} catch (SQLException e) {
			retVal = false;
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}
		return retVal;
	}

	@Override
	public boolean ukloniUcesce(int id) {
		boolean retVal = false;

		String query = "DELETE FROM ucestvuje WHERE ElektrijadaId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(query);
			ps.setInt(1, id);

			retVal = ps.executeUpdate() == 1;

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
	public boolean ukloniUcescePoDisciplini(DisciplinaDTO disciplinaDTO) {
		boolean retVal = false;

		String query = "DELETE FROM ucestvuje WHERE Naziv=? AND ElektrijadaId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtility.open();

			ps = conn.prepareStatement(query);
			ps.setString(1, disciplinaDTO.getNaziv());
			ps.setInt(2, disciplinaDTO.getElektrijadaId());

			retVal = ps.executeUpdate() == 1;

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
	public boolean ucesnikElekrijade(int idStudenta) {
		boolean retVal = false;
		
		String provjera = "SELECT StudentId"
				+ " FROM ucestvuje WHERE StudentId = ?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(provjera);
			ps.setInt(1, idStudenta);
			rs = ps.executeQuery();

			if (rs.next()) {
				retVal = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}

		return retVal;
	}

	
	
}
