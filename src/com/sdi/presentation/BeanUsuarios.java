package com.sdi.presentation;

import java.io.Serializable;
import java.util.ArrayList;
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
	private String password;
	private String repeatPassword;
	private boolean success;
	private boolean fail;

	// creiterio de busqueda
	private String entry;

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

	public void updateUser() {
		UsuarioService us = Factories.services.createUsuarioService();
		Usuario editando = us.find(getLogin(email));
		success = false;
		fail = false;
		if (check(password, repeatPassword)) {
			editando.setNombre(nombre);
			editando.setApellidos(apellidos);
			if (!password.equals("")) {
				editando.setPasswd(password);
			}
			editando.setActivo(activo);
			success = true;
			us.updateProfile(editando);
		} else {
			fail = true;
		}
		init();
		password = null;
		repeatPassword = null;
	}

	private boolean check(String password, String repeatPassword) {
		if ((!password.equals("") && repeatPassword.equals(""))
				|| (password.equals("") && !repeatPassword.equals(""))) {
			return false;
		}
		if (!password.equals("") && !repeatPassword.equals("")) {
			return password.equals(repeatPassword);
		}
		return true;
	}

	public void refreshUsers() {
		init();
		List<Usuario> filter = new ArrayList<Usuario>();
		for (int i = 0; i < activated.size(); i++) {
			if (!(activated.get(i).getNombre().contains(entry))
					&& !(activated.get(i).getApellidos().contains(entry))
					&& !(activated.get(i).getEmail().contains(entry))) {
				filter.add(activated.get(i));
			}
		}
		for (Usuario u : filter) {
			activated.remove(u);
		}
		for (int i = 0; i < deactivated.size(); i++) {
			if (!(deactivated.get(i).getNombre().contains(entry))
					&& !(deactivated.get(i).getApellidos().contains(entry))
					&& !(deactivated.get(i).getEmail().contains(entry))) {
				filter.add(deactivated.get(i));
			}
		}
		for (Usuario u : filter) {
			deactivated.remove(u);
		}
	}

	private String getLogin(String mail) {
		String[] split = mail.split("@");
		return split[0];
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

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

}
