package com.sdi.business.impl;

import java.util.List;

import com.sdi.business.ContactoService;
import com.sdi.business.impl.classes.contacto.AddContact;
import com.sdi.business.impl.classes.contacto.FindAdmin;
import com.sdi.business.impl.classes.contacto.FindByLogin;
import com.sdi.model.Contacto;

public class SimpleContactoService implements ContactoService {

	@Override
	public List<Contacto> findByLogin(String login) {
		return new FindByLogin().find(login);
	}

	@Override
	public List<Contacto> findAdmin() {
		return new FindAdmin().find();
	}

	@Override
	public void addContact(Contacto c) {
		new AddContact().addContact(c);
	}
}
