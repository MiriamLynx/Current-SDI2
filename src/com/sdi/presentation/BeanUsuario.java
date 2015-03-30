package com.sdi.presentation;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.UsuarioService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;

@ManagedBean(name = "user")
@SessionScoped
public class BeanUsuario implements Serializable {

	private static final long serialVersionUID = -6661030588868331188L;

	private String login;
	private String password;
	private boolean notFound;
	private boolean wrongPassword;
	private boolean deactivated;

	public String validate() {

		UsuarioService service = Factories.services.createUsuarioService();
		Usuario user = service.find(login);

		if (check(user)) {
			setSession(user);
			return "exit";
		} else {
			return "fail";
		}
	}

	private boolean check(Usuario user) {
		notFound = false;
		wrongPassword = false;
		deactivated = false;
		if (user == null) {
			notFound = true;
			return false;
		} else if (!(user.getPasswd().equals(password))) {
			wrongPassword = true;
			return false;
		} else if (!(user.isActivo())) {
			deactivated = true;
			return false;
		} else {
			return true;
		}
	}

	private void setSession(Usuario usuario) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("USER", usuario);
	}

	private void endSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.clear();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isNotFound() {
		return notFound;
	}

	public void setNotFound(boolean notFound) {
		this.notFound = notFound;
	}

	public boolean isWrongPassword() {
		return wrongPassword;
	}

	public void setWrongPassword(boolean wrongPassword) {
		this.wrongPassword = wrongPassword;
	}

	public boolean isDeactivated() {
		return deactivated;
	}

	public void setDeactivated(boolean deactivated) {
		this.deactivated = deactivated;
	}

}
