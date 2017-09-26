package org.unibl.etf.ps.studentviewer.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.NalogDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ZahtjevDTO;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.ZahtjevDisciplinaDTO;

public class MySQLZahtjevDisciplinaDAO implements ZahtjevDisciplinaDAO {

	@Override
	public boolean addZahtjev(ZahtjevDisciplinaDTO zahtjevDTO) {
		boolean retVal = false;

		String query = "INSERT INTO zahtjev_disciplina VALUE (?, ?, ?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);
			

			ps.setInt(1, zahtjevDTO.getNalogId());
			ps.setInt(2, zahtjevDTO.getElektrijadaId());
			ps.setString(3, zahtjevDTO.getNaziv());
			ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			ps.setObject(5, null, java.sql.Types.DATE);
			ps.setObject(6, null, java.sql.Types.INTEGER);

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
	public ArrayList<ZahtjevDisciplinaDTO> getAllZahtjev() {
		ArrayList<ZahtjevDisciplinaDTO> retVals = new ArrayList<>();

		String query = "SELECT * FROM zahtjev_disciplina WHERE DatumOdobrenja IS NULL";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				retVals.add(new ZahtjevDisciplinaDTO(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4),
						rs.getDate(5), rs.getInt(6)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}

		return retVals;
	}

	@Override
	public boolean updateZahtjev(ZahtjevDisciplinaDTO zahtjevDTO) {
		boolean retVal = false;

		String query = "UPDATE zahtjev_disciplina SET NalogId = ?, ElektrijadaId = ?, Naziv = ?, DatumZahtjeva=?, DatumOdobrenja=?, AdminId=? WHERE NalogId = ? AND ElektrijadaId = ? AND Naziv=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			ps.setInt(1, zahtjevDTO.getNalogId());
			ps.setInt(2, zahtjevDTO.getElektrijadaId());
			ps.setString(3, zahtjevDTO.getNaziv());
			ps.setDate(4, new java.sql.Date(zahtjevDTO.getDatumZahtjeva().getTime()));
			ps.setDate(5, new java.sql.Date(System.currentTimeMillis()));
			ps.setInt(6, zahtjevDTO.getAdminId());

			ps.setInt(7, zahtjevDTO.getNalogId());
			ps.setInt(8, zahtjevDTO.getElektrijadaId());
			ps.setString(9, zahtjevDTO.getNaziv());

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
	public boolean deleteZahtjev(ZahtjevDisciplinaDTO zahtjevDTO) {
		boolean retVal = false;

		String query = "DELETE FROM zahtjev_disciplina WHERE NalogId = ? AND ElektrijadaId = ? AND Naziv=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);

			ps.setInt(1, zahtjevDTO.getNalogId());
			ps.setInt(2, zahtjevDTO.getElektrijadaId());
			ps.setString(3, zahtjevDTO.getNaziv());
			
			retVal = ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

	@Override
	public boolean deleteZahtjevPoElektrijadi(int id) {
		boolean retVal = false;

		String query = "DELETE FROM zahtjev_disciplina WHERE  ElektrijadaId=?";

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
	public boolean deleteZahtjevPoDisciplini(DisciplinaDTO disciplinaDTO) {
		boolean retVal = false;

		String query = "DELETE FROM zahtjev_disciplina WHERE Naziv=? AND ElektrijadaId=?";

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

	@Override
	public boolean deleteZahtjevPoNaloguIDisciplini(NalogDTO nalogDTO, DisciplinaDTO disciplinaDTO) {
		boolean retVal = false;

		String query = "DELETE FROM zahtjev_disciplina WHERE NalogId=? AND  Naziv=? AND ElektrijadaId=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, nalogDTO.getNalogId());
			ps.setString(2, disciplinaDTO.getNaziv());
			ps.setInt(3, disciplinaDTO.getElektrijadaId());
			
			retVal = ps.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, ps);
		}

		return retVal;
	}

}
