package com.sdi.business.impl.classes.correo;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Correo;
import com.sdi.persistence.CorreoDao;

public class FindByLogin {

	public List<Correo> find(String login) {
		CorreoDao dao = Factories.persistence.createCorreoDao();
		return dao.getLoginCorreos(login);
	}

}
