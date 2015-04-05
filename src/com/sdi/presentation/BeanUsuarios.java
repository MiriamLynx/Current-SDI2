package com.sdi.presentation;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.UsuarioService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;

@ManagedBean(name = "users")
@SessionScoped
public class BeanUsuarios implements Serializable {

	private static final long serialVersionUID = 1319356059817952187L;

	private static List<Usuario> activated;
	private static List<Usuario> deactivated;
	
	private String[] logins;

	public static void init() {
		UsuarioService us = Factories.services.createUsuarioService();
		activated = us.getAllActivated();
		deactivated = us.getAllDeactivated();
	}

	public void activate() {

	}

	public void setUsers() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("PAGE", "users");
	}

	public String verUsers() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("PAGE", "users");
		return "users";
	}

	public List<Usuario> getActivated() {
		return activated;
	}

	public List<Usuario> getDeactivated() {
		return deactivated;
	}

	public String[] getLogins() {
		return logins;
	}

	public void setLogins(String[] logins) {
		this.logins = logins;
	}

}
