package com.sdi.business.impl.classes.contacto;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Contacto;
import com.sdi.persistence.ContactoDao;
import com.sdi.persistence.exception.AlreadyPersistedException;

public class AddContact {

	public void addContact(Contacto c) {
		ContactoDao dao = Factories.persistence.createContactoDao();
		try {
			dao.save(c);
		} catch (AlreadyPersistedException e) {
			e.printStackTrace();
		}
	}

}
