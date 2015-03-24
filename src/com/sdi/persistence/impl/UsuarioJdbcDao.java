package com.sdi.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sdi.model.Usuario;
import com.sdi.persistence.UsuarioDao;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;
import com.sdi.persistence.exception.PersistenceException;
import com.sdi.util.Conf;
import com.sdi.util.Jdbc;

public class UsuarioJdbcDao implements UsuarioDao {

	private static Connection con = null;

	public List<Usuario> getUsuarios() throws PersistenceException {

		String GET_USER = Conf.get("GET_USER");
		String GET_INFOUSER_BY_ID = Conf.get("GET_INFOUSER_BY_ID");

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(GET_USER);
			rs = ps.executeQuery();

			while (rs.next()) {

				Usuario usuario = new Usuario();
				usuario.setLogin(rs.getString("LOGIN"));
				usuario.setPasswd(rs.getString("PASSWD"));
				usuario.setRol(rs.getString("ROL"));
				boolean act = rs.getBoolean("ACTIVO");
				usuario.setActivo(act);
				int ID = rs.getInt("ID_INFOUSUARIO");
				usuario.setId(ID);

				PreparedStatement ps2 = null;
				ResultSet rs2 = null;

				ps2 = con.prepareStatement(GET_INFOUSER_BY_ID);
				ps2.setInt(1, ID);
				rs2 = ps2.executeQuery();

				while (rs2.next()) {
					usuario.setNombre(rs2.getString("NOMBRE"));
					usuario.setApellidos(rs2.getString("APELLIDOS"));
					usuario.setEmail(rs2.getString("EMAIL"));
				}

				rs2.close();
				ps2.close();

				usuarios.add(usuario);
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

		return usuarios;
	}

	public List<Usuario> getUsuariosInactivos() throws PersistenceException {

		String GET_DESACTIVATED_USERS = Conf.get("GET_DESACTIVATED_USERS");
		String GET_INFOUSER_BY_ID = Conf.get("GET_INFOUSER_BY_ID");

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(GET_DESACTIVATED_USERS);
			rs = ps.executeQuery();

			while (rs.next()) {

				Usuario usuario = new Usuario();
				usuario.setLogin(rs.getString("LOGIN"));
				usuario.setPasswd(rs.getString("PASSWD"));
				usuario.setRol(rs.getString("ROL"));
				boolean act = rs.getBoolean("ACTIVO");
				usuario.setActivo(act);
				int ID = rs.getInt("ID_INFOUSUARIO");
				usuario.setId(ID);

				PreparedStatement ps2 = null;
				ResultSet rs2 = null;

				ps2 = con.prepareStatement(GET_INFOUSER_BY_ID);
				ps2.setInt(1, ID);
				rs2 = ps2.executeQuery();

				while (rs2.next()) {
					usuario.setNombre(rs2.getString("NOMBRE"));
					usuario.setApellidos(rs2.getString("APELLIDOS"));
					usuario.setEmail(rs2.getString("EMAIL"));
				}

				rs2.close();
				ps2.close();

				usuarios.add(usuario);
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

		return usuarios;
	}

	public List<Usuario> getUsuariosActivos() throws PersistenceException {

		String GET_ACTIVATED_USERS = Conf.get("GET_ACTIVATED_USERS");
		String GET_INFOUSER_BY_ID = Conf.get("GET_INFOUSER_BY_ID");

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(GET_ACTIVATED_USERS);
			rs = ps.executeQuery();

			while (rs.next()) {

				Usuario usuario = new Usuario();
				usuario.setLogin(rs.getString("LOGIN"));
				usuario.setPasswd(rs.getString("PASSWD"));
				usuario.setRol(rs.getString("ROL"));
				boolean act = rs.getBoolean("ACTIVO");
				usuario.setActivo(act);
				int ID = rs.getInt("ID_INFOUSUARIO");
				usuario.setId(ID);

				PreparedStatement ps2 = null;
				ResultSet rs2 = null;

				ps2 = con.prepareStatement(GET_INFOUSER_BY_ID);
				ps2.setInt(1, ID);
				rs2 = ps2.executeQuery();

				while (rs2.next()) {
					usuario.setNombre(rs2.getString("NOMBRE"));
					usuario.setApellidos(rs2.getString("APELLIDOS"));
					usuario.setEmail(rs2.getString("EMAIL"));
				}

				rs2.close();
				ps2.close();

				usuarios.add(usuario);
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

		return usuarios;
	}

	@Override
	public void save(Usuario a) throws AlreadyPersistedException,
			PersistenceException {

		String INSERT_USER = Conf.get("INSERT_USER");
		String GET_ID_BY_MAIL = Conf.get("GET_ID_BY_MAIL");
		String INSERT_INFOUSER = Conf.get("INSERT_INFOUSER");

		PreparedStatement ps = null;
		ResultSet rs = null;
		int rows = 0;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(INSERT_INFOUSER);

			ps.setString(1, a.getNombre());
			ps.setString(2, a.getApellidos());
			ps.setString(3, a.getLogin() + "@micorreo.com");

			rows = ps.executeUpdate();
			if (rows != 1) {
				throw new AlreadyPersistedException("InfoUser " + a
						+ " already persisted");
			}

			ps.close();

			ps = con.prepareStatement(GET_ID_BY_MAIL);
			ps.setString(1, a.getLogin() + "@micorreo.com");
			rs = ps.executeQuery();
			int id = 0;
			while (rs.next()) {
				id = rs.getInt("ID");
			}

			rs.close();
			ps.close();

			ps = con.prepareStatement(INSERT_USER);

			ps.setString(1, a.getLogin());
			ps.setString(2, a.getPasswd());
			ps.setString(3, a.getRol());
			ps.setString(4, "0");
			ps.setString(5, id + "");

			rows = ps.executeUpdate();
			if (rows != 1) {
				throw new AlreadyPersistedException("User " + a
						+ " already persisted");
			}

			ps.close();
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
	public void update(Usuario a) throws NotPersistedException,
			PersistenceException {

		String UPDATE_USER = Conf.get("UPDATE_USER");
		String UPDATE_INFOUSER = Conf.get("UPDATE_INFOUSER");

		PreparedStatement ps = null;

		int rows = 0;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(UPDATE_USER);

			ps.setString(1, a.getPasswd());
			ps.setBoolean(2, a.isActivo());
			ps.setString(3, a.getLogin());

			rows = ps.executeUpdate();
			if (rows != 1) {
				throw new NotPersistedException("User " + a + " not found");
			}

			PreparedStatement ps2 = null;
			ps2 = con.prepareStatement(UPDATE_INFOUSER);

			ps2.setString(1, a.getNombre());
			ps2.setString(2, a.getApellidos());
			ps2.setString(3, a.getEmail());
			ps2.setInt(4, a.getId());

			rows = ps2.executeUpdate();
			if (rows != 1) {
				throw new NotPersistedException("infoUser " + a + " not found");
			}
			ps2.close();
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
	public void delete(String login) throws NotPersistedException,
			PersistenceException {

		String DELETE_USER = Conf.get("DELETE_USER");

		PreparedStatement ps = null;
		int rows = 0;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(DELETE_USER);
			ps.setString(1, login);
			rows = ps.executeUpdate();

			if (rows != 1) {
				throw new NotPersistedException("User " + login + " not found");
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
	public Usuario findByLogin(String login) throws PersistenceException {

		String GET_USER_BY_LOGIN = Conf.get("GET_USER_BY_LOGIN");
		String GET_INFOUSER_BY_ID = Conf.get("GET_INFOUSER_BY_ID");

		PreparedStatement ps = null;
		ResultSet rs = null;
		Usuario usuario = null;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(GET_USER_BY_LOGIN);
			ps.setString(1, login);
			rs = ps.executeQuery();

			if (rs.next()) {
				usuario = new Usuario();
				usuario.setLogin(rs.getString("LOGIN"));
				usuario.setPasswd(rs.getString("PASSWD"));
				usuario.setRol(rs.getString("ROL"));
				usuario.setActivo(rs.getBoolean("ACTIVO"));
				usuario.setId(rs.getInt("ID_INFOUSUARIO"));
			}

			PreparedStatement ps2 = null;
			ResultSet rs2 = null;

			ps2 = con.prepareStatement(GET_INFOUSER_BY_ID);

			if (usuario == null) {
				return null;
			}

			ps2.setInt(1, usuario.getId());

			rs2 = ps2.executeQuery();

			if (rs2.next()) {

				usuario.setNombre(rs2.getString("NOMBRE"));
				usuario.setApellidos(rs2.getString("APELLIDOS"));
				usuario.setEmail(rs2.getString("EMAIL"));
			}

			ps2.close();
			rs2.close();

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

		return usuario;
	}

	public void deleteUsuarios() {

		String DELETE_INFOUSUARIO = Conf.get("DELETE_INFOUSUARIO");

		PreparedStatement ps = null;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(DELETE_INFOUSUARIO);
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

		String RESET_ID = Conf.get("RESET_ID");

		PreparedStatement ps = null;

		try {

			con = Jdbc.getConnection();

			ps = con.prepareStatement(RESET_ID);
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
