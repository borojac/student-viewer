package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.logic.utility.dbutility.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.model.dto.StudentNaPredmetuDTO;
import org.unibl.etf.ps.studentviewer.model.dto.TestDTO;

public class MySQLPredmetDAO implements PredmetDAO {

	@Override
	public PredmetDTO getPredmet(int predmetId) {
		PredmetDTO retVal = null;
		
		String getPredmetQuery = "SELECT PredmetId, SifraPredmeta, Naziv, ECTS, Semestar, TipPredmeta, NazivSP, SkolskaGodina"
				+ " FROM ((predmet INNER JOIN studijski_program USING(SPId)) INNER JOIN predmet_na_fakultetu USING(SifraPredmeta)) INNER JOIN p_na_sp USING(SifraPredmeta)"
				+ " WHERE PredmetId = ?";
		
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
						rs.getShort(5), (rs.getString(6)).charAt(0), rs.getString(7), rs.getString(8));
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

}
