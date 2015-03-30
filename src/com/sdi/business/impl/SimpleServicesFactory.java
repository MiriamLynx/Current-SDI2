package com.sdi.business.impl;

import com.sdi.business.ServicesFactory;
import com.sdi.business.UsuarioService;

public class SimpleServicesFactory implements ServicesFactory {

	@Override
	public UsuarioService createUsuarioService() {
		return new SimpleUsuarioService();
	}

}
