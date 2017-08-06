package org.unibl.etf.ps.studentviewer.utility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBUtility {
	private DBUtility() {}
	
	public static synchronized void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {}
		}
	}
	
	public static synchronized void close(Statement s) {
		if (s != null) {
			try {
				s.close();
			} catch (SQLException ex) {}
		}
	}
	
	public static synchronized void close(Connection conn) {
		
	}
	
	public static synchronized void close(Object... objects) {
		for (Object obj : objects) {
			if (obj instanceof Connection) {
				close((Connection) obj);
			} else if (obj instanceof Statement || 
					   obj instanceof PreparedStatement ||
					   obj instanceof CallableStatement) {
				close((Statement) obj);
			} else if (obj instanceof ResultSet) {
				close((ResultSet) obj);
			}
		}
	}

}
