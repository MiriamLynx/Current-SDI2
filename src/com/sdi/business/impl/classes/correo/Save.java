package com.sdi.business.impl.classes.correo;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Correo;
import com.sdi.persistence.CorreoDao;
import com.sdi.persistence.exception.AlreadyPersistedException;

public class Save {

	public void save(Correo correo) {
		CorreoDao dao = Factories.persistence.createCorreoDao();
		try {
			dao.save(correo);
		} catch (AlreadyPersistedException e) {
			e.printStackTrace();
		}
	}

}
