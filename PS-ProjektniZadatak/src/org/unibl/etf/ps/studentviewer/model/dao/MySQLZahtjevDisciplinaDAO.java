package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ZahtjevDisciplinaDTO;
import org.unibl.etf.ps.studentviewer.persistence.dbutility.mysql.DBUtility;

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
			System.out.println(zahtjevDTO);

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

}
