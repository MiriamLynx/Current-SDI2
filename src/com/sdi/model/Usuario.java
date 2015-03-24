package com.sdi.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String login;
	private String passwd;
	private String rol;
	private boolean activo;

	private List<Correo> correos;

	private List<Contacto> contactos;

	// Esta información corresponde a infousuarios
	// Debido a que es una relación 1 a 1
	private int id;
	private String nombre;
	private String apellidos;
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<Correo> getCorreos() {
		return correos;
	}

	public void setCorreos(List<Correo> correos) {
		this.correos = correos;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	public List<Correo> getBorradores() {
		List<Correo> borradores = new ArrayList<Correo>();
		for (Correo c : correos) {
			if (c.getCarpeta() == 2) {
				borradores.add(c);
			}
		}
		return borradores;
	}

	public List<Correo> getEnviados() {
		List<Correo> enviados = new ArrayList<Correo>();
		for (Correo c : correos) {
			if (c.getCarpeta() == 1) {
				enviados.add(c);
			}
		}
		return enviados;
	}

	public List<Correo> getEliminados() {
		List<Correo> eliminados = new ArrayList<Correo>();
		for (Correo c : correos) {
			if (c.getCarpeta() == 3) {
				eliminados.add(c);
			}
		}
		return eliminados;
	}

}
