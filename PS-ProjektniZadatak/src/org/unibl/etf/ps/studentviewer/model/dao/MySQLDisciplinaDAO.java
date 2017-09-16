package org.unibl.etf.ps.studentviewer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.unibl.etf.ps.studentviewer.dbutility.mysql.DBUtility;
import org.unibl.etf.ps.studentviewer.model.dto.DisciplinaDTO;
import org.unibl.etf.ps.studentviewer.model.dto.NalogDTO;

public class MySQLDisciplinaDAO implements DisciplinaDAO {

	@Override
	public List<String> getDiscipline(int idNaloga) {
		List<String> listaDisciplina = new ArrayList<String>();

		String getNalogQuery = "SELECT NalogId";
		//
		// Connection conn = null;
		// PreparedStatement ps = null;
		// ResultSet rs = null;
		//
		// try {
		//
		// conn = DBUtility.open();
		// ps = conn.prepareStatement(getNalogQuery);
		// ps.setInt(1, nalogId);
		// rs = ps.executeQuery();
		//
		// if (rs.next()) {
		// retVal = new NalogDTO(rs.getInt(1), rs.getString(2), rs.getString(3),
		// rs.getString(4), rs.getString(5),
		// (rs.getString(6)).charAt(0));
		// retVal.setPredmeti(getPredmeteNaNalogu(retVal.getNalogId()));
		// }
		//
		// } catch (SQLException e) {
		// e.printStackTrace();
		// } finally {
		// DBUtility.close(conn, rs, ps);
		// }

		return listaDisciplina;
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
