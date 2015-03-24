package com.sdi.business.impl.classes.usuario;

import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;
import com.sdi.persistence.UsuarioDao;

public class Find {

	public void find(String login) throws EntityNotFoundException {
		UsuarioDao dao = Factories.persistence.createUsuarioDao();
		check(dao.findByLogin(login));
	}

	private void check(Usuario usuario) throws EntityNotFoundException {
		if (usuario == null) {
			throw new EntityNotFoundException(
					"El usuario introducido no existe");
		}
	}
}
