package org.unibl.etf.ps.studentviewer.dbutility.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Ovu klasu koristite za dobijanje konekcija za rad sa bazom podataka
 * Ne koristite ConnectionPool
 */
public final class DBUtility {

	public static Connection open() throws SQLException {
		return ConnectionPool.getInstance().checkOut();
	}
	
	public static void close(Object... objects) {
		Connection conn = null;
		for (Object obj : objects) {
			if (obj instanceof Connection) {
				conn = (Connection) obj;
			} else if (obj instanceof Statement || 
					   obj instanceof PreparedStatement ||
					   obj instanceof CallableStatement) {
				close((Statement) obj);
			} else if (obj instanceof ResultSet) {
				close((ResultSet) obj);
			}
		}

		close(conn);
	}













	private DBUtility() {}

	private static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {}
		}
	}

	private static void close(Statement s) {
		if (s != null) {
			try {
				s.close();
			} catch (SQLException ex) {}
		}
	}

	private static void close(Connection conn) {
		ConnectionPool.getInstance().checkIn(conn);
	}

}
