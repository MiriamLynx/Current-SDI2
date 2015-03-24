package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Contacto;
import com.sdi.model.Correo;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface CorreoDao {
	
	List<Correo> getCorreos();
	
	List<Correo> getLoginCorreos(String login);
	
	int save(Correo a) throws AlreadyPersistedException;
	
	void update(Correo a) throws NotPersistedException;
	
	void delete(int id) throws NotPersistedException;
	
	List<Correo> getLoginCarpetaCorreos(String login, int carpeta);
	
	List<Contacto> getDestinatariosCorreo(int idCorreo);
	
	void deleteCorreos();
	
	void deleteCorreosLogin(String login);
	
	void reiniciaID();

}
