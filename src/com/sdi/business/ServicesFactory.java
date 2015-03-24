package com.sdi.business;

public interface ServicesFactory {
	
	AlumnosService createAlumnosService();
	
	LoginService createLoginService();

}
