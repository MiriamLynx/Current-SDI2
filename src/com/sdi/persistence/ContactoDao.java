package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Contacto;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface ContactoDao {
	
	List<Contacto> getContactos();
	
	List<Contacto> getLoginContactos(String login);
	
	void save(Contacto a) throws AlreadyPersistedException;
	
	void update(Contacto a) throws NotPersistedException;
	
	void delete(int id) throws NotPersistedException;
	
	void deleteContactos();
	
	void deleteContactosAdmin();
	
	List<Contacto> getAdminContactos();
	
	void reiniciaID();
}
