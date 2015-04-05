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

	// listas de usuarios
	private static List<Usuario> activated;
	private static List<Usuario> deactivated;

	// datos de usuario a editar
	private String nombre;
	private String apellidos;
	private boolean activo;
	private String email;
	private String currentPassword;
	private String password;
	private String repeatPassword;
	private boolean success;
	private boolean fail;

	public static void init() {
		UsuarioService us = Factories.services.createUsuarioService();
		activated = us.getAllActivated();
		deactivated = us.getAllDeactivated();
	}

	public String load() {
		if (FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("user") != null) {
			Integer id = Integer.parseInt(FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap().get("user"));
			Usuario usuario = find(id);
			this.nombre = usuario.getNombre();
			this.apellidos = usuario.getApellidos();
			this.activo = usuario.isActivo();
			this.email = usuario.getEmail();
			return "editUser";
		}
		return "";
	}

	private Usuario find(Integer id) {
		for (Usuario u : activated) {
			if (u.getId() == id) {
				return u;
			}
		}
		for (Usuario u : deactivated) {
			if (u.getId() == id) {
				return u;
			}
		}
		return null;
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess() {
		this.success = false;
	}

	public boolean isFail() {
		return fail;
	}

	public void setFail() {
		this.fail = false;
	}

}
