package com.sdi.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sdi.util.Jdbc;

import com.sdi.model.Contacto;
import com.sdi.model.Correo;
import com.sdi.persistence.CorreoDao;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;
import com.sdi.persistence.exception.PersistenceException;
import com.sdi.util.Conf;

public class CorreoJdbcDao implements CorreoDao {

	private Connection con = null;

	@Override
	public List<Correo> getCorreos() {

		String GET_MAIL = Conf.get("GET_MAIL");

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Correo> correos = new ArrayList<Correo>();

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(GET_MAIL);
			rs = ps.executeQuery();

			while (rs.next()) {
				Correo correo = new Correo();
				correo.setId(rs.getInt("ID"));
				correo.setFechahora(rs.getLong("FECHAHORA"));
				correo.setAsunto(rs.getString("ASUNTO"));
				correo.setCuerpo(rs.getString("CUERPO"));
				correo.setCarpeta(rs.getInt("CARPETA"));
				correo.setLogin_Usuario(rs.getString("LOGIN_USUARIO"));

				correos.add(correo);
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

		return correos;
	}

	public List<Contacto> getDestinatariosCorreo(int idCorreo) {

		String GET_RECIPIENTS_MAIL = Conf.get("GET_RECIPIENTS_MAIL");

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Contacto> contactos = new ArrayList<Contacto>();

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(GET_RECIPIENTS_MAIL);
			ps.setInt(1, idCorreo);
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
	public List<Correo> getLoginCorreos(String login) {

		String GET_MAILS_BY_LOGIN = Conf.get("GET_MAILS_BY_LOGIN");

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Correo> correos = new ArrayList<Correo>();

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(GET_MAILS_BY_LOGIN);
			ps.setString(1, login);
			rs = ps.executeQuery();

			while (rs.next()) {
				Correo correo = new Correo();
				correo.setId(rs.getInt("ID"));
				correo.setFechahora(rs.getLong("FECHAHORA"));
				correo.setAsunto(rs.getString("ASUNTO"));
				correo.setCuerpo(rs.getString("CUERPO"));
				correo.setCarpeta(rs.getInt("CARPETA"));
				correo.setLogin_Usuario(rs.getString("LOGIN_USUARIO"));
				correos.add(correo);
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

		return correos;
	}

	@Override
	public List<Correo> getLoginCarpetaCorreos(String login, int carpeta) {

		String GET_MAILS_BY_LOGIN_AND_FOLDER = Conf
				.get("GET_MAILS_BY_LOGIN_AND_FOLDER");

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Correo> correos = new ArrayList<Correo>();

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(GET_MAILS_BY_LOGIN_AND_FOLDER);
			ps.setString(1, login);
			ps.setInt(2, carpeta);
			rs = ps.executeQuery();

			while (rs.next()) {
				Correo correo = new Correo();
				correo.setId(rs.getInt("ID"));
				correo.setFechahora(rs.getLong("FECHAHORA"));
				correo.setAsunto(rs.getString("ASUNTO"));
				correo.setCuerpo(rs.getString("CUERPO"));
				correo.setCarpeta(rs.getInt("CARPETA"));
				correo.setLogin_Usuario(rs.getString("LOGIN_USUARIO"));
				correos.add(correo);
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

		return correos;
	}

	@Override
	public int save(Correo a) throws AlreadyPersistedException {

		String INSERT_MAIL = Conf.get("INSERT_MAIL");

		PreparedStatement ps = null;

		int id_insertado = 0;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(INSERT_MAIL,
					Statement.RETURN_GENERATED_KEYS);

			ps.setDouble(1, a.getFechahora());
			ps.setString(2, a.getAsunto());
			ps.setString(3, a.getCuerpo());
			ps.setInt(4, a.getCarpeta());
			ps.setString(5, a.getLogin_Usuario());

			int updated = ps.executeUpdate();
			if (updated != 1) {
				throw new AlreadyPersistedException("Mail " + a
						+ " already persisted.");
			}

			if (updated == 1) {
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					id_insertado = generatedKeys.getInt(1);
				}
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
		return id_insertado;

	}

	@Override
	public void update(Correo a) throws NotPersistedException {

		String UPDATE_MAIL = Conf.get("UPDATE_MAIL");

		PreparedStatement ps = null;

		int rows = 0;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(UPDATE_MAIL);

			ps.setDouble(1, a.getFechahora());
			ps.setString(2, a.getAsunto());
			ps.setString(3, a.getCuerpo());
			ps.setInt(4, a.getCarpeta());
			ps.setString(5, a.getLogin_Usuario());
			ps.setLong(6, a.getId());

			rows = ps.executeUpdate();

			if (rows != 1) {
				throw new NotPersistedException("Correo " + a + " not found");
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

		String DELETE_MAIL = Conf.get("DELETE_MAIL");

		PreparedStatement ps = null;

		int rows = 0;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(DELETE_MAIL);
			ps.setLong(1, id);

			rows = ps.executeUpdate();
			if (rows != 1) {
				throw new NotPersistedException("Mail " + id + " not found");
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
	public void deleteCorreosLogin(String login) {

		String DELETE_MAIL_BY_LOGIN = Conf.get("DELETE_MAIL_BY_LOGIN");

		PreparedStatement ps = null;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(DELETE_MAIL_BY_LOGIN);
			ps.setString(1, login);
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
	public void deleteCorreos() {

		String DELETE_ALL_MAILS = Conf.get("DELETE_ALL_MAILS");

		PreparedStatement ps = null;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(DELETE_ALL_MAILS);

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

		String RESET_MAIL_ID = Conf.get("RESET_MAIL_ID");

		PreparedStatement ps = null;

		try {
			con = Jdbc.getConnection();

			ps = con.prepareStatement(RESET_MAIL_ID);

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
