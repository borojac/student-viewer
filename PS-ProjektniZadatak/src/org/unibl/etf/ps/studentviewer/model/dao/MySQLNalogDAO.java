package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.logic.utility.dbutility.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;

public class MySQLNalogDAO implements NalogDAO {

	@Override
	public NalogDTO getNalog(String korisnickoIme, String lozinka) {
		NalogDTO retVal = null;
		
		String getNalogQuery = "SELECT NalogId, Ime, Prezime, KorisnickoIme, Lozinka, TipNaloga"
				+ " FROM nalog WHERE KorisnickoIme = ? and Lozinka = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(getNalogQuery);
			ps.setString(1, korisnickoIme);
			ps.setString(2, lozinka);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				retVal = new NalogDTO(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), (rs.getString(6)).charAt(0));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		retVal.setPredmeti(getPredmeteNaNalogu(retVal.getNalogId()));
		
		return retVal;
	}

	@Override
	public boolean addNalog(NalogDTO nalog) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateNalog(NalogDTO nalog) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addPredmet(PredmetDTO predmet, NalogDTO nalog) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removePredmet(PredmetDTO predmet, NalogDTO nalog) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<PredmetDTO> getPredmeteNaNalogu(int nalogId) {
		ArrayList<PredmetDTO> retVals = new ArrayList<>();
		
		String query = "SELECT PredmetId FROM predaje WHERE NalogId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MySQLDAOFactory predmetFactory = new MySQLDAOFactory();
		PredmetDAO predmetDAO = predmetFactory.getPredmetDAO();
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, nalogId);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				retVals.add(predmetDAO.getPredmet(rs.getInt(1)));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}
		
		return retVals;
	}

}
