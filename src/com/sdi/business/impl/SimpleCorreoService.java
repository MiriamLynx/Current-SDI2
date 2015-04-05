package com.sdi.business.impl;

import java.util.List;

import com.sdi.business.CorreoService;
import com.sdi.business.impl.classes.correo.FindByLogin;
import com.sdi.business.impl.classes.correo.Save;
import com.sdi.model.Correo;

public class SimpleCorreoService implements CorreoService {

	@Override
	public List<Correo> findByLogin(String login) {
		return new FindByLogin().find(login);
	}

	@Override
	public void save(Correo correo) {
		new Save().save(correo);
	}

}
