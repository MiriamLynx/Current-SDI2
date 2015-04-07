package com.sdi.presentation;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.ContactoService;
import com.sdi.business.CorreoService;
import com.sdi.business.ResetService;
import com.sdi.business.UsuarioService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;

@ManagedBean(name = "sesion")
@SessionScoped
public class BeanSesion implements Serializable {

	private static final long serialVersionUID = -6661030588868331188L;

	// parametros para entrar en sesion
	private String login;
	private String password;
	private boolean notFound;
	private boolean wrongPassword;
	private boolean deactivated;

	// usuario en sesion
	private Usuario user;

	// perfil
	private String nombre;
	private String apellidos;
	private String currentPassword;
	private String repeatPassword;
	private boolean success;
	private boolean fail;
	private boolean newusersuccess;
	private boolean newuserfail;

	public String validate() {
		UsuarioService us = Factories.services.createUsuarioService();
		Usuario user = us.find(login);
		if (check(user)) {
			return init(user);
		} else {
			return "fail";
		}
	}

	public void setFalse() {
		this.deactivated = false;
		this.fail = false;
		this.newuserfail = false;
		this.newusersuccess = false;
		this.wrongPassword = false;
	}

	public void updateProfile() {
		UsuarioService us = Factories.services.createUsuarioService();
		success = false;
		fail = false;
		if (check(currentPassword, password, repeatPassword, user)) {
			user.setNombre(nombre);
			user.setApellidos(apellidos);
			if (!password.equals("")) {
				user.setPasswd(password);
			}
			us.updateProfile(user);
			success = true;
		} else {
			fail = true;
		}
		currentPassword = null;
		password = null;
		repeatPassword = null;
	}

	public void newProfile() {
		UsuarioService us = Factories.services.createUsuarioService();
		setNewusersuccess(false);
		setNewuserfail(false);
		if (checkNew(password, repeatPassword)) {
			if (checkDidNotExist()) {
				Usuario user = new Usuario();
				user.setLogin(login);
				user.setNombre(nombre);
				user.setApellidos(apellidos);
				user.setPasswd(password);
				user.setRol("Cliente");
				us.newProfile(user);
				setNewusersuccess(true);
			} else {
				newuserfail = true;
			}
		} else {
			wrongPassword = true;
		}
		login = null;
		password = null;
		repeatPassword = null;
	}

	private boolean check(String currentPassword, String password,
			String repeatPassword, Usuario editando) {
		if (password.equals("") && repeatPassword.equals("")
				&& currentPassword.equals("")) {
			return true;
		} else if (password.equals("") || repeatPassword.equals("")
				|| currentPassword.equals("")) {
			return false;
		} else if (password.equals(repeatPassword)
				&& currentPassword.equals(editando.getPasswd())) {
			return true;
		}
		return false;
	}

	private boolean checkDidNotExist() {
		UsuarioService us = Factories.services.createUsuarioService();
		Usuario user = us.find(login);
		return user == null;
	}

	private String init(Usuario user) {
		CorreoService cs = Factories.services.createCorreoService();
		ContactoService cos = Factories.services.createContactoService();
		setNombre(user.getNombre());
		setApellidos(user.getApellidos());
		setSession(user);
		password = null;
		if (user.getRol().equals("Administrador")) {
			setPage("users");
			user.setContactos(cos.findAdmin());
			BeanUsuarios.init();
			return "users";
		} else {
			setPage("sent");
			user.setCorreos(cs.findByLogin(login));
			user.setContactos(cos.findByLogin(login));
			user.addContacts(cos.findAdmin());
			BeanCorreos.init();
			return "mail";
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

	private boolean checkNew(String password, String repeatPassword) {
		if (password.equals("") || repeatPassword.equals("")) {
			return false;
		}
		if (!password.equals("") && !repeatPassword.equals("")) {
			return password.equals(repeatPassword);
		}
		return false;
	}

	public String getPage() {
		String page = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("PAGE");
		if (page.equals("sent") || page.equals("drafts")
				|| page.equals("deleted")) {
			return "mail";
		} else if (page.equals("users")) {
			return "users";
		} else if (page.equals("contacts")) {
			return "contacts";
		}
		return "";
	}

	public String getMailPage() {
		String page = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("PAGE");
		return page;
	}

	public String getTitle() {
		String page = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("PAGE");
		if (page != null) {
			switch (page) {
			case "sent":
				return getLocaleString("sent");
			case "drafts":
				return getLocaleString("drafts");
			case "deleted":
				return getLocaleString("deleted");
			case "users":
				return getLocaleString("users");
			case "contacts":
				return getLocaleString("contacts");
			default:
				return "";
			}
		}
		return "";
	}

	public void setPage(String page) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("PAGE", page);
	}

	private void setSession(Usuario usuario) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("USER", usuario);
		this.user = usuario;
	}

	public String end() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.clear();
		clear();
		return "home";
	}

	public void clear() {
		this.user = null;
		this.login = null;
		this.password = null;
	}

	public String reset() {
		ResetService rs = Factories.services.createResetService();
		rs.reset();
		BeanUsuarios.init();
		init(user);
		return "users";
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

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	private String getLocaleString(String string) {
		ResourceBundle bundle = FacesContext.getCurrentInstance()
				.getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msgs");
		FacesMessage msg = new FacesMessage(bundle.getString(string));
		return msg.getSummary();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public boolean isNewusersuccess() {
		return newusersuccess;
	}

	public void setNewusersuccess(boolean newusersuccess) {
		this.newusersuccess = newusersuccess;
	}

	public boolean isNewuserfail() {
		return newuserfail;
	}

	public void setNewuserfail(boolean newuserfail) {
		this.newuserfail = newuserfail;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

}
