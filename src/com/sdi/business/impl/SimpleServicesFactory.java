package com.sdi.business.impl;

import com.sdi.business.ContactoService;
import com.sdi.business.CorreoService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.UsuarioService;

public class SimpleServicesFactory implements ServicesFactory {

	@Override
	public UsuarioService createUsuarioService() {
		return new SimpleUsuarioService();
	}

	@Override
	public CorreoService createCorreoService() {
		return new SimpleCorreoService();
	}

	@Override
	public ContactoService createContactoService() {
		return new SimpleContactoService();
	}

}
