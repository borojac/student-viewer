package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.ElektrijadaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;

public class MySQLDisciplinaDAO implements DisciplinaDAO {

	@Override
	public List<DisciplinaDTO> getDiscipline(int idElektrijade) {
		List<DisciplinaDTO> retVal = new ArrayList<DisciplinaDTO>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT Naziv, ElektrijadaId FROM disciplina WHERE ElektrijadaId=?";

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(query);
			ps.setInt(1, idElektrijade);
			rs = ps.executeQuery();
			while (rs.next()) {
				retVal.add(new DisciplinaDTO(rs.getString(1),rs.getInt(2)));
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
	public DisciplinaDTO getDisciplina(int idNaloga, int idElektrijade) {
		DisciplinaDTO retVal = null;

		String getNalogQuery = "SELECT Naziv FROM zaduzen_za WHERE NalogId=? AND ElektrijadaId=?";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DBUtility.open();
			ps = conn.prepareStatement(getNalogQuery);
			ps.setInt(1, idNaloga);
			ps.setInt(2, idElektrijade);
			rs = ps.executeQuery();

			if (rs.next()) {
				retVal = new DisciplinaDTO(rs.getString(1), idElektrijade);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtility.close(conn, rs, ps);
		}

		return retVal;
	}

}
