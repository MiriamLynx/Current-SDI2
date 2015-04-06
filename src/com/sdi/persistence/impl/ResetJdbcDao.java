package com.sdi.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sdi.persistence.ResetDao;
import com.sdi.persistence.exception.PersistenceException;
import com.sdi.util.Conf;
import com.sdi.util.Jdbc;

public class ResetJdbcDao implements ResetDao {

	private static Connection con = null;

	@Override
	public void reset() {

		PreparedStatement ps = null;

		try {

			con = Jdbc.getConnection();

			for (int i = 1; i <= 62; i++) {
				String CON = Conf.get("CON_" + i);
				ps = con.prepareStatement(CON);
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception ex) {
				}
			}
			;
			if (con != null) {
				try {
					con.close();
				} catch (Exception ex) {
				}
			}
			;
		}
	}

}
