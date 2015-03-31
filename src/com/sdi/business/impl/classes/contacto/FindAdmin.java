package com.sdi.business.impl.classes.contacto;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Contacto;
import com.sdi.persistence.ContactoDao;

public class FindAdmin {

	public List<Contacto> find() {
		ContactoDao dao = Factories.persistence.createContactoDao();
		return dao.getAdminContactos();
	}

}
