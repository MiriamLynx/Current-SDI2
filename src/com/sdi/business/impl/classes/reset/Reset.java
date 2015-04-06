package com.sdi.business.impl.classes.reset;

import com.sdi.infrastructure.Factories;
import com.sdi.persistence.ResetDao;

public class Reset {

	public void reset() {
		ResetDao dao = Factories.persistence.createResetDao();
		dao.reset();
	}

}
