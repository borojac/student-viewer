package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.model.dto.PredmetDTO;
import org.unibl.etf.ps.studentviewer.persistence.dbutility.mysql.DBUtility;

public class MySQLNalogDAO implements NalogDAO {

	@Override
	public NalogDTO getNalog(int nalogId) {
		NalogDTO retVal = null;

		String getNalogQuery = "SELECT NalogId, Ime, Prezime, KorisnickoIme, Lozinka, TipNaloga"
				+ " FROM nalog WHERE NalogId = ?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getNalogQuery);
			ps.setInt(1, nalogId);
			rs = ps.executeQuery();

			if (rs.next()) {
				retVal = new NalogDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						(rs.getString(6)).charAt(0));
				retVal.setPredmeti(getPredmeteNaNalogu(retVal.getNalogId()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}

		return retVal;
	}

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

			if (rs.next()) {
				retVal = new NalogDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						(rs.getString(6)).charAt(0));
				retVal.setPredmeti(getPredmeteNaNalogu(retVal.getNalogId()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}

		return retVal;
	}

	public boolean checkNalog(String korisnickoIme) {
		boolean retVal = false;

		String getNalogQuery = "SELECT NalogId, Ime, Prezime, KorisnickoIme, Lozinka, TipNaloga"
				+ " FROM nalog WHERE KorisnickoIme = ?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getNalogQuery);
			ps.setString(1, korisnickoIme);
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

	@Override
	public boolean addNalog(NalogDTO nalog) {
		boolean retVal = false;

		String query = "INSERT INTO nalog VALUE (null, ?, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			ps.setString(1, nalog.getIme());
			ps.setString(2, nalog.getPrezime());
			ps.setString(3, nalog.getKorisnickoIme());
			ps.setString(4, nalog.getLozinka());
			ps.setString(5, String.valueOf(nalog.getTipNaloga()));

			retVal = ps.executeUpdate() == 1;

			if (retVal) {
				conn.commit();
			} else {
				throw new SQLException("Rollback needed!");
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public boolean updateNalog(NalogDTO nalog) {
		boolean retVal = false;

		String query = "UPDATE nalog SET Ime = ?, Prezime = ?, KorisnickoIme = ?, Lozinka = ?, TipNaloga = ? WHERE NalogId = ?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			ps.setString(1, nalog.getIme());
			ps.setString(2, nalog.getPrezime());
			ps.setString(3, nalog.getKorisnickoIme());
			ps.setString(4, nalog.getLozinka());
			ps.setString(5, String.valueOf(nalog.getTipNaloga()));
			ps.setInt(6, nalog.getNalogId());

			retVal = ps.executeUpdate() == 1;

			if (retVal) {
				conn.commit();
			} else {
				throw new SQLException("Rollback needed!");
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public boolean addPredmet(PredmetDTO predmet, NalogDTO nalog) {
		boolean retVal = false;

		String query = "INSERT INTO predaje VALUE (?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			ps.setInt(1, nalog.getNalogId());
			ps.setInt(2, predmet.getPredmetId());
			ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
			ps.setObject(4, null, java.sql.Types.BLOB);

			retVal = ps.executeUpdate() == 1;

			if (retVal) {
				conn.commit();
			} else {
				throw new SQLException("Rollback needed!");
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public boolean removePredmet(PredmetDTO predmet, NalogDTO nalog) {
		boolean retVal = false;

		String query = "DELETE FROM predaje WHERE PredmetId = ? and NalogId = ?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);

			ps.setInt(1, predmet.getPredmetId());
			ps.setInt(2, nalog.getNalogId());

			retVal = ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}
	
	@Override
	public boolean removePredmete(PredmetDTO predmetDTO) {
		boolean retVal = false;
		
		String getBroj = "SELECT NalogId FROM predaje WHERE PredmetId = ?";
		String deletePredmete = "DELETE FROM predaje WHERE PredmetId = ?";
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBUtility.open();
			ps = conn.prepareStatement(getBroj);
			ps.setInt(1, predmetDTO.getPredmetId());
			rs = ps.executeQuery();
			
			int broj = 0;
			while(rs.next()) {
				broj++;
			}
			if(broj == 0) {
				return true;
			}
			
			ps = conn.prepareStatement(deletePredmete);
			ps.setInt(1, predmetDTO.getPredmetId());
			
			retVal = ps.executeUpdate() == broj;
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}
		
		return retVal;
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

			while (rs.next()) {
				retVals.add(predmetDAO.getPredmet(rs.getInt(1)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}

		return retVals;
	}

	@Override
	public boolean removeDisciplina(DisciplinaDTO disciplina, NalogDTO nalog) {
		boolean retVal = false;

		String query = "DELETE FROM zaduzen_za WHERE  NalogId = ? AND Naziv=? AND ElektrijadaId=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);

			ps.setInt(1, nalog.getNalogId());
			ps.setString(2, disciplina.getNaziv());
			ps.setInt(3, disciplina.getElektrijadaId());

			retVal = ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public boolean addDisciplinuNaNalog(DisciplinaDTO disciplinaDTO, NalogDTO nalog) {
		boolean retVal = false;

		String query = "INSERT INTO zaduzen_za VALUE (?, ?, ?)";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			ps.setInt(1, nalog.getNalogId());
			ps.setString(2, disciplinaDTO.getNaziv());
			ps.setInt(3, disciplinaDTO.getElektrijadaId());

			retVal = ps.executeUpdate() == 1;

			if (retVal) {
				conn.commit();
			} else {
				throw new SQLException("Rollback needed!");
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
			}
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public boolean ukloniZaduzenja(int id) {
		boolean retVal = false;

		String query = "DELETE FROM zaduzen_za WHERE  ElektrijadaId=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);

			ps.setInt(1, id);
			

			retVal = ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
		
	}

	@Override
	public boolean ukloniZaduzenjaPoDisciplini(DisciplinaDTO disciplinaDTO) {
		boolean retVal = false;

		String query = "DELETE FROM zaduzen_za WHERE  Naziv=? AND ElektrijadaId=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);

			ps.setString(1, disciplinaDTO.getNaziv());
			ps.setInt(2, disciplinaDTO.getElektrijadaId());
			

			retVal = ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
		
	}

}
