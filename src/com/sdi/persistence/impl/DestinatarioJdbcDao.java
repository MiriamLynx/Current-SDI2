package com.sdi.persistence.impl;

import java.sql.*;

import com.sdi.util.Jdbc;

import com.sdi.model.Destinatario;
import com.sdi.persistence.DestinatarioDao;
import com.sdi.persistence.exception.*;
import com.sdi.util.Conf;

public class DestinatarioJdbcDao implements DestinatarioDao {

	private static Connection con = null;

	@Override
	public void save(Destinatario a) throws AlreadyPersistedException {

		String INSERT_RECIPIENT = Conf.get("INSERT_RECIPIENT");

		PreparedStatement ps = null;

		int rows = 0;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(INSERT_RECIPIENT);

			ps.setInt(1, a.getId_Correo());
			ps.setInt(2, a.getId_Contacto());

			rows = ps.executeUpdate();

			if (rows != 1) {
				throw new AlreadyPersistedException("Recipient " + a
						+ " already persisted");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema"
					+ ps.toString(), e);
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

	@Override
	public void delete(int id_Correo) throws NotPersistedException {

		String DELETE_RECIPIENT = Conf.get("DELETE_RECIPIENT");

		PreparedStatement ps = null;

		int rows = 0;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(DELETE_RECIPIENT);
			ps.setLong(1, id_Correo);
			rows = ps.executeUpdate();

			if (rows != 1) {
				throw new NotPersistedException("Correo " + id_Correo
						+ " not found");
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

	@Override
	public void reiniciaID() {

		String RESET_RECIPIENT_ID = Conf.get("RESET_RECIPIENT_ID");

		PreparedStatement ps = null;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(RESET_RECIPIENT_ID);
			ps.executeUpdate();

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
