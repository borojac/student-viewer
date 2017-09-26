package org.unibl.etf.ps.studentviewer.persistence.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.persistence.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.persistence.model.dto.DisciplinaDTO;

public class MySQLDisciplinaDAO implements DisciplinaDAO {

	@Override
	public List<DisciplinaDTO> getDiscipline(int idElektrijade, int idNaloga) {
		List<DisciplinaDTO> retVal = new ArrayList<DisciplinaDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT Naziv, ElektrijadaId FROM zaduzen_za WHERE ElektrijadaId=? AND NalogId=?";

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, idElektrijade);
			ps.setInt(2, idNaloga);
			rs = ps.executeQuery();
			while (rs.next()) {
				retVal.add(new DisciplinaDTO(rs.getString(1), rs.getInt(2)));
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
	public List<DisciplinaDTO> getDisciplinePoElektrijadi(int idElektrijade) {
		List<DisciplinaDTO> retVal = new ArrayList<DisciplinaDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT Naziv, ElektrijadaId FROM disciplina WHERE ElektrijadaId=? ";

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, idElektrijade);
			
			rs = ps.executeQuery();
			while (rs.next()) {
				retVal.add(new DisciplinaDTO(rs.getString(1), rs.getInt(2)));
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
	public boolean addDisciplinu(DisciplinaDTO disciplinaDTO) {
		boolean retVal = false;

		String query = "INSERT INTO disciplina VALUE (?, ?)";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			
			ps.setString(1, disciplinaDTO.getNaziv());
			ps.setInt(2, disciplinaDTO.getElektrijadaId());

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
	public boolean deleteDisciplinu(DisciplinaDTO disciplinaDTO) {
		boolean retVal = false;

		String query = "DELETE FROM disciplina WHERE  Naziv=? AND ElektrijadaId=?";

		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = DBUtility.open();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(query);

			
			ps.setString(1, disciplinaDTO.getNaziv());
			ps.setInt(2, disciplinaDTO.getElektrijadaId());

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
	public List<DisciplinaDTO> getDisciplinePoZahtjevima(int idElektrijade, int idNaloga) {
		List<DisciplinaDTO> retVal = new ArrayList<DisciplinaDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT Naziv, ElektrijadaId FROM zahtjev_disciplina WHERE ElektrijadaId=? AND NalogId=?";

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, idElektrijade);
			ps.setInt(2, idNaloga);
			rs = ps.executeQuery();
			while (rs.next()) {
				retVal.add(new DisciplinaDTO(rs.getString(1), rs.getInt(2)));
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
