package com.sdi.business;

import java.util.List;

import com.sdi.model.Contacto;

/**
 * Este es el interfaz que ofrecer�� cualquier implementaci��n de la clase
 * fachada.
 * 
 * Al separar la implementaci��n de la fachada de su interfaz se permite cambiar
 * las implementaciones reales de la fachada. Esto es muy ��til cuando se
 * necesita a��adir funcionalidad extra como acceso remoto, web services,
 * control de acceso, etc. Al hacerlo de esta forma esos cambios solo afectan a
 * las factorias y no al contenido de las capas. Las factor��as, en un
 * desarrollo profesional, se configuran declarativamente (properties, xml, etc)
 * 
 * @author alb
 * 
 */
public interface ContactoService {

	List<Contacto> findByLogin(String login);

	List<Contacto> findAdmin();

	void addContact(Contacto c);

}