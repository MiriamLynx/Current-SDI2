package com.sdi.business.impl.classes.usuario;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;
import com.sdi.persistence.UsuarioDao;

public class FindByLogin {

	public Usuario find(String login) {
		UsuarioDao dao = Factories.persistence.createUsuarioDao();
		return dao.findByLogin(login);
	}
}
