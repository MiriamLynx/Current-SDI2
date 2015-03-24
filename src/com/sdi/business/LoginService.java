package com.sdi.business;

import com.sdi.model.User;

public interface LoginService {
	
	User verify(String login, String password);
	
}
