package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.GradingInfoDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class MySQLPredmetDAO implements PredmetDAO {

	@Override
	public PredmetDTO getPredmet(int predmetId) {
		PredmetDTO retVal = null;
		
		String getPredmetQuery = "SELECT PredmetId, SifraPredmeta, Naziv, ECTS, Semestar, TipPredmeta, NazivSP, SkolskaGodina, Ciklus"
				+ " FROM ((predmet INNER JOIN studijski_program USING(SPId)) INNER JOIN predmet_na_fakultetu USING(SifraPredmeta)) INNER JOIN p_na_sp USING(SifraPredmeta)"
				+ " WHERE PredmetId = ?";
		
		String getSkolskaGodinaQuery = "SELECT SkolskaGodina FROM skolska_godina WHERE SGId = ?";
		
		String getStudentsQuery = "SELECT * FROM student";
		
		String getTestsQuery = "SELECT TestId FROM test WHERE PredmetId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(getPredmetQuery);
			ps.setInt(1, predmetId);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				retVal = new PredmetDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getShort(4),
						rs.getShort(5), (rs.getString(6)).charAt(0), rs.getString(7), rs.getString(8), rs.getShort(9));
			}
			
			ps = conn.prepareStatement(getSkolskaGodinaQuery);
			ps.setInt(1, rs.getInt(8));
			rs = ps.executeQuery();
			
			if(rs.next()) {
				retVal.setSkolskaGodina(rs.getString(1));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<StudentNaPredmetuDTO> studenti = new ArrayList<>();
		
		try {
			
			ps = conn.prepareStatement(getStudentsQuery);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				studenti.add(new StudentNaPredmetuDTO(rs.getInt(1), rs.getString(4), rs.getString(3), rs.getString(2)));
			}
			if(retVal != null) {
				retVal.setStudenti(studenti);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		ArrayList<TestDTO> testovi = new ArrayList<>();
		MySQLDAOFactory testFactory = new MySQLDAOFactory();
		TestDAO testDAO = testFactory.getTestDAO();
		
		try {
			
			ps = conn.prepareStatement(getTestsQuery);
			ps.setInt(1, predmetId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				testovi.add(testDAO.getTest(rs.getInt(1)));
			}
			if(retVal != null) {
				retVal.setTestovi(testovi);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}

	@Override
	public ArrayList<StudentNaPredmetuDTO> getStudentsOnPredmet(int predmetId) {
		ArrayList<StudentNaPredmetuDTO> retVals = new ArrayList<>();
		
		String query = "SELECT StudentId, BrojIndeksa, Ime, Prezime FROM slusa INNER JOIN student USING(StudentId) WHERE PredmetId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, predmetId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				retVals.add(new StudentNaPredmetuDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVals;
	}

	@Override
	public ArrayList<TestDTO> getTestsOnPredmet(int predmetId) {
		ArrayList<TestDTO> retVals = new ArrayList<>();
		
		String query = "SELECT TestId FROM test WHERE PredmetId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		MySQLDAOFactory testFactory = new MySQLDAOFactory();
		TestDAO testDAO = testFactory.getTestDAO();
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, predmetId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				retVals.add(testDAO.getTest(rs.getInt(1)));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVals;
	}
	
	public ArrayList<PredmetDTO> getAllPredmet() {
		ArrayList<PredmetDTO> retVals = new ArrayList<>();
		
		String query = "SELECT PredmetId FROM predmet";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		MySQLDAOFactory predmetFactory = new MySQLDAOFactory();
		PredmetDAO predmetDAO = predmetFactory.getPredmetDAO();
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				retVals.add(predmetDAO.getPredmet(rs.getInt(1)));
			}
			
			if(retVals.size() == 0) {
				retVals = null;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVals;
	}
	
	public boolean addPredmet(PredmetDTO predmetDTO) {
		boolean retVal = false;
		
		String getSGId = "SELECT SGId FROM skolska_godina WHERE SkolskaGodina = ?";
		String getSPId = "SELECT SPId FROM studijski_program WHERE NazivSP = ? and Ciklus = ?";
		String addToPredmet = "INSERT INTO predmet VALUE (null , ?, ?, ?)";
		String addToPredmetNaFakultetu = "INSERT INTO predmet_na_fakultetu VALUE (?, ?, ?)";
		String addToPNaSP = "INSERT INTO p_na_sp VALUE (?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(getSGId);
			ps.setString(1, predmetDTO.getSkolskaGodina());
			rs = ps.executeQuery();
			
			int sgId = 0;
			int spId = 0;
			
			if(rs.next()) {
				sgId = rs.getInt(1);
			} else {
				JOptionPane.showMessageDialog(null, "Nekorektno unesena skolska godina.");
				return false;
			}
			
			ps = conn.prepareStatement(getSPId);
			ps.setString(1, predmetDTO.getNazivSP());
			ps.setShort(2, predmetDTO.getCiklus());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				spId = rs.getInt(1);
			} else {
				JOptionPane.showMessageDialog(null, "Nekorektno unesen naziv studijskog programa.");
				return false;
			}
			
			ps = conn.prepareStatement(addToPredmetNaFakultetu);
			ps.setString(1, predmetDTO.getSifraPredmeta());
			ps.setString(2, predmetDTO.getNazivPredmeta());
			ps.setShort(3, predmetDTO.getEcts());
			
			retVal = ps.executeUpdate() == 1;
			
			ps = conn.prepareStatement(addToPNaSP);
			ps.setString(1, predmetDTO.getSifraPredmeta());
			ps.setInt(2, spId);
			ps.setShort(3, predmetDTO.getSemestar());
			ps.setString(4, String.valueOf(predmetDTO.getTipPredmeta()));
			
			retVal = ps.executeUpdate() == 1;
			
			ps = conn.prepareStatement(addToPredmet);
			ps.setInt(1, sgId);
			ps.setString(2, predmetDTO.getSifraPredmeta());
			ps.setInt(3, spId);
			
			retVal = ps.executeUpdate() == 1;
			
			if(retVal) {
				conn.commit();
			} else {
				throw new SQLException("Rollback needed!");
			}
			
		} catch(SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {}
			DBUtility.close(conn, ps);
		}
		
		return retVal;
	}
	
	public boolean addPredmete(ArrayList<PredmetDTO> predmeti) {
		boolean retVal = true;
		
		for(int i = 0; i < predmeti.size(); i++) {
			if(!addPredmet(predmeti.get(i))) {
				retVal = false;
				break;
			}
		}
		
		return retVal;
	}
	
	public boolean checkStudijskiProgram(String nazivSP, short ciklus) {
		boolean retVal = false;
		
		String query = "SELECT SPId FROM studijski_program WHERE NazivSP = ? and Ciklus = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setString(1, nazivSP);
			ps.setInt(2, ciklus);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				retVal = true;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVal;
	}
	
	public boolean addStudijskiProgram(String nazivSP, int ects, short ciklus, short trajanje, String zvanje) {
		boolean retVal = false;
		
		String query = "INSERT INTO studijski_program VALUE (null, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			
			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			ps.setString(1, nazivSP);
			ps.setInt(2, ects);
			ps.setShort(3, ciklus);
			ps.setShort(4, trajanje);
			ps.setString(5, zvanje);
			
			retVal = ps.executeUpdate() == 1;
			
			if(retVal) {
				conn.commit();
			} else {
				throw new SQLException("Rollback needed!");
			}
			
		} catch(SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {}
			DBUtility.close(conn, ps);
		}
		
		return retVal;
	}

	@Override
	public List<StudentNaPredmetuDTO> getStudentsForGrading(int predmetId) {
		List<StudentNaPredmetuDTO> retVals = new LinkedList<>();
		String query = "SELECT StudentId, BrojIndeksa, Ime, Prezime "
				+ "FROM student INNER JOIN slusa USING(StudentId) "
				+ "WHERE PredmetId=? AND Ocjena IS null";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, predmetId);
			rs = ps.executeQuery();
			while (rs.next()) {
				retVals.add(new StudentNaPredmetuDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVals;
	}

	@Override
	public List<GradingInfoDTO> getGradingInfo(int studentId, int predmetId) {
		List<GradingInfoDTO> retVals = new ArrayList<>();
		
		String dbquery = "SELECT test.*, BrojBodova, Komentar "
				+ "FROM test INNER JOIN izlazi_na USING(TestId) "
				+ "WHERE StudentId=? AND PredmetId=? AND BrojBodova > 50";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtility.open();
			ps = conn.prepareStatement(dbquery);
			ps.setInt(1, studentId);
			ps.setInt(2, predmetId);
			rs = ps.executeQuery();
			while (rs.next()) {
				TestDTO tmp = new TestDTO(rs.getInt(1), rs.getString(2), rs.getDate(3),
						rs.getString(4), rs.getInt(5), rs.getInt(6));
				retVals.add(new GradingInfoDTO(tmp, rs.getInt(7), rs.getString(8)));
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
