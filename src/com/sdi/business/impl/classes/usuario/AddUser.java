package com.sdi.business.impl.classes.usuario;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;
import com.sdi.persistence.UsuarioDao;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public class AddUser {

	public void add(Usuario user) {
		UsuarioDao dao = Factories.persistence.createUsuarioDao();
		try {
			dao.save(user);
		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (AlreadyPersistedException e) {
			e.printStackTrace();
		}
	}

}
