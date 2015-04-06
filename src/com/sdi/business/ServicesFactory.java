package com.sdi.business;

public interface ServicesFactory {

	UsuarioService createUsuarioService();

	CorreoService createCorreoService();

	ContactoService createContactoService();

	ResetService createResetService();

}
