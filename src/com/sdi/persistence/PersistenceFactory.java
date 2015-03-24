package com.sdi.persistence;

import com.sdi.persistence.UsuarioDao;

/**
 * Interfaz de la factoria que suministra implementaciones reales de la fachada 
 * de persistencia para cada Entidad del Modelo (en este caso solo hay 
 * una: Alumno; pero en futuras versiones habr�� m��s)
 *  
 * @author alb
 *
 */
public interface PersistenceFactory {
	
	UsuarioDao createUsuarioDao();
	CorreoDao createCorreoDao();
	ContactoDao createContactoDao();
	DestinatarioDao createDestinatarioDao();
	
	// ... otros m��todos factoria para Daos de otras entidades del modelo ...
}

