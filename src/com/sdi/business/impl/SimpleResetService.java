package com.sdi.business.impl;

import com.sdi.business.ResetService;
import com.sdi.business.impl.classes.reset.Reset;

public class SimpleResetService implements ResetService {

	@Override
	public void reset() {
		new Reset().reset();
	}

}
