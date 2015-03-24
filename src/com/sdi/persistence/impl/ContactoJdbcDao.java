package com.sdi.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sdi.model.Contacto;
import com.sdi.persistence.ContactoDao;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;
import com.sdi.persistence.exception.PersistenceException;
import com.sdi.util.Conf;
import com.sdi.util.Jdbc;

public class ContactoJdbcDao implements ContactoDao {

	private Connection con = null;

	@Override
	public List<Contacto> getContactos() {

		String GET_CONTACTS = Conf.get("GET_CONTACTS");

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Contacto> contactos = new ArrayList<Contacto>();

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(GET_CONTACTS);
			rs = ps.executeQuery();

			while (rs.next()) {
				Contacto contacto = new Contacto();
				contacto.setId(rs.getInt("ID"));
				contacto.setEmail(rs.getString("EMAIL"));
				contacto.setNombre(rs.getString("NOMBRE"));
				contacto.setApellidos(rs.getString("APELLIDOS"));
				contacto.setDireccion(rs.getString("DIRECCION"));
				contacto.setCiudad(rs.getString("CIUDAD"));
				contacto.setAgenda_Usuario(rs.getString("AGENDA_USUARIO"));
				contactos.add(contacto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}
			;
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

		return contactos;
	}

	@Override
	public List<Contacto> getLoginContactos(String login) {

		String GET_CONTACTS_BY_AGENDA = Conf.get("GET_CONTACTS_BY_AGENDA");

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Contacto> contactos = new ArrayList<Contacto>();

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(GET_CONTACTS_BY_AGENDA);
			ps.setString(1, login);
			rs = ps.executeQuery();

			while (rs.next()) {
				Contacto contacto = new Contacto();
				contacto.setId(rs.getInt("ID"));
				contacto.setEmail(rs.getString("EMAIL"));
				contacto.setNombre(rs.getString("NOMBRE"));
				contacto.setApellidos(rs.getString("APELLIDOS"));
				contacto.setDireccion(rs.getString("DIRECCION"));
				contacto.setCiudad(rs.getString("CIUDAD"));
				contacto.setAgenda_Usuario(rs.getString("AGENDA_USUARIO"));
				contactos.add(contacto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}
			;
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

		return contactos;
	}

	@Override
	public void save(Contacto a) throws AlreadyPersistedException {

		String INSERT_CONTACT = Conf.get("INSERT_CONTACT");

		PreparedStatement ps = null;

		int rows = 0;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(INSERT_CONTACT);

			ps.setString(1, a.getEmail());
			ps.setString(2, a.getNombre());
			ps.setString(3, a.getApellidos());
			ps.setString(4, a.getDireccion());
			ps.setString(5, a.getCiudad());
			ps.setString(6, a.getAgenda_Usuario());

			rows = ps.executeUpdate();

			if (rows != 1) {
				throw new AlreadyPersistedException("Contact " + a
						+ " already persisted");
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
	public void update(Contacto a) throws NotPersistedException {

		String UPDATE_CONTACT = Conf.get("UPDATE_CONTACT ");

		PreparedStatement ps = null;

		int rows = 0;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(UPDATE_CONTACT);

			ps.setString(1, a.getEmail());
			ps.setString(2, a.getNombre());
			ps.setString(3, a.getApellidos());
			ps.setString(4, a.getDireccion());
			ps.setString(5, a.getCiudad());
			ps.setString(6, a.getAgenda_Usuario());
			ps.setLong(7, a.getId());

			rows = ps.executeUpdate();

			if (rows != 1) {
				throw new NotPersistedException("Contact " + a + " not found");
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
	public void delete(int id) throws NotPersistedException {

		String DELETE_CONTACT = Conf.get("DELETE_CONTACT");

		PreparedStatement ps = null;

		int rows = 0;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(DELETE_CONTACT);
			ps.setLong(1, id);

			rows = ps.executeUpdate();

			if (rows != 1) {
				throw new NotPersistedException("Contact " + id + " not found");
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
	public void deleteContactos() {

		String DELETE_ALL_CONTACTS = Conf.get("DELETE_ALL_CONTACTS");

		PreparedStatement ps = null;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(DELETE_ALL_CONTACTS);
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

	@Override
	public void reiniciaID() {

		String RESET_CONTACT_ID = Conf.get("RESET_CONTACT_ID");

		PreparedStatement ps = null;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(RESET_CONTACT_ID);

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

	@Override
	public void deleteContactosAdmin() {

		String DELETE_ADMIN_CONTACTS = Conf.get("DELETE_ADMIN_CONTACTS");

		PreparedStatement ps = null;

		try {
			con = Jdbc.getConnection();

			ps = con.prepareStatement(DELETE_ADMIN_CONTACTS);

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

	@Override
	public List<Contacto> getAdminContactos() {

		String GET_ADMIN_CONTACTS = Conf.get("GET_ADMIN_CONTACTS");

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Contacto> contactos = new ArrayList<Contacto>();

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(GET_ADMIN_CONTACTS);
			rs = ps.executeQuery();

			while (rs.next()) {
				Contacto contacto = new Contacto();
				contacto.setId(rs.getInt("ID"));
				contacto.setEmail(rs.getString("EMAIL"));
				contacto.setNombre(rs.getString("NOMBRE"));
				contacto.setApellidos(rs.getString("APELLIDOS"));
				contacto.setDireccion(rs.getString("DIRECCION"));
				contacto.setCiudad(rs.getString("CIUDAD"));
				contacto.setAgenda_Usuario(rs.getString("AGENDA_USUARIO"));

				contactos.add(contacto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}
			;
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

		return contactos;
	}
}
