package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Usuario;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;
import com.sdi.persistence.exception.PersistenceException;

/**
 * Interfaz de la fachada a servicios de persistencia para la entidad Alumno.
 * 
 * En esta versi��n aparecen los otros m��todos b��sicos de un servicio de
 * persistencia
 * 
 * @author alb
 * 
 */
public interface UsuarioDao {

	List<Usuario> getUsuariosActivos() throws PersistenceException;

	List<Usuario> getUsuariosInactivos() throws PersistenceException;

	List<Usuario> getUsuarios() throws PersistenceException;

	void save(Usuario a) throws AlreadyPersistedException, PersistenceException;

	void update(Usuario a) throws NotPersistedException, PersistenceException;

	void delete(String login) throws NotPersistedException,
			PersistenceException;

	Usuario findByLogin(String login) throws PersistenceException;

	void deleteUsuarios();

	void reiniciaID();

}