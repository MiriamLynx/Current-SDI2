package com.sdi.business.impl;

import java.util.List;

import com.sdi.business.ContactoService;
import com.sdi.business.impl.classes.contacto.Find;
import com.sdi.business.impl.classes.contacto.FindAdmin;
import com.sdi.model.Contacto;

public class SimpleContactoService implements ContactoService {

	@Override
	public List<Contacto> findByLogin(String login) {
		return new Find().find(login);
	}

	@Override
	public List<Contacto> findAdmin() {
		return new FindAdmin().find();
	}
}
