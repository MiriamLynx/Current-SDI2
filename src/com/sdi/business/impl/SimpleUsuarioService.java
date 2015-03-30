package com.sdi.business.impl;

import com.sdi.business.UsuarioService;
import com.sdi.business.impl.classes.usuario.Find;
import com.sdi.model.Usuario;

public class SimpleUsuarioService implements UsuarioService {

	@Override
	public Usuario find(String login) {
		return new Find().find(login);
	}
}
