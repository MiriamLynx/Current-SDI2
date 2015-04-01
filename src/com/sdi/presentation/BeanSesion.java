package com.sdi.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.ContactoService;
import com.sdi.business.CorreoService;
import com.sdi.business.UsuarioService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Contacto;
import com.sdi.model.Correo;
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

	// visualizando
	private String title;
	private List<Correo> mail;
	private String current;

	// perfil
	private String nombre;
	private String apellidos;
	private String repeatPassword;
	private boolean success;
	private boolean fail;

	// criterio de busqueda
	private String entry;

	public String validate() {

		UsuarioService us = Factories.services.createUsuarioService();
		Usuario user = us.find(login);

		if (check(user)) {
			return init(user);
		} else {
			return "fail";
		}
	}

	public void updateProfile() {
		UsuarioService us = Factories.services.createUsuarioService();
		success = false;
		fail = false;
		if (check(password, repeatPassword)) {
			us.updateProfile(user, nombre, apellidos, password);
			success = true;
		} else {
			fail = true;
		}
		password = null;
		repeatPassword = null;
	}

	private String init(Usuario user) {
		CorreoService cs = Factories.services.createCorreoService();
		ContactoService cos = Factories.services.createContactoService();
		setNombre(user.getNombre());
		setApellidos(user.getApellidos());
		setSession(user);
		password = null;
		if (user.getRol().equals("Administrador")) {
			user.setContactos(cos.findAdmin());
			BeanUsuarios.init();
			return setUsers();
		} else {
			user.setCorreos(cs.findByLogin(login));
			user.setContactos(cos.findByLogin(login));
			user.addContacts(cos.findAdmin());
			setEnviados();
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

	public void refreshMail() {
		setPage();
		List<Correo> filter = new ArrayList<Correo>();
		for (int i = 0; i < mail.size(); i++) {
			if (!(mail.get(i).getAsunto().contains(entry))
					&& !(mail.get(i).getCuerpo().contains(entry))
					&& !(mail.get(i).getLogin_Usuario().contains(entry))) {
				filter.add(mail.get(i));
			}
		}
		for (Correo c : filter) {
			mail.remove(c);
		}
	}

	public void refreshContacts() {
		init(user);
		List<Contacto> filter = new ArrayList<Contacto>();
		for (int i = 0; i < user.getContactos().size(); i++) {
			if (!(user.getContactos().get(i).getNombre().contains(entry))
					&& !(user.getContactos().get(i).getApellidos()
							.contains(entry))
					&& !(user.getContactos().get(i).getEmail().contains(entry))
					&& !(user.getContactos().get(i).getAgenda_Usuario()
							.contains(entry))) {
				filter.add(user.getContactos().get(i));
			}
		}
		for (Contacto c : filter) {
			user.getContactos().remove(c);
		}
	}

	public void setPage() {
		if (current != null) {
			if (this.current.equals("sent")) {
				setEnviados();
			} else if (this.current.equals("drafts")) {
				setBorradores();
			} else if (this.current.equals("deleted")) {
				setEliminados();
			} else if (this.current.equals("contacts")) {
				setContactos();
			} else if (this.current.equals("users")) {
				setUsers();
			}
		}
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

	public void orderBySubject() {
		int[] index = getIndexSubject();
		java.util.Arrays.sort(index);
		getMailBackSubject(index);
	}

	public void getMailBackSubject(int[] index) {
		int evaluated = 0;
		List<Correo> aux = new ArrayList<Correo>();
		for (int i = 0; i < index.length; i++) {
			for (int j = 0; j < index.length; j++) {
				if (index[i] == mail.get(j).getAsunto().hashCode()
						&& evaluated != index.length) {
					aux.add(mail.get(j));
					evaluated++;
				}
			}
		}
		mail = aux;
	}

	public int[] getIndexSubject() {
		int[] index = new int[mail.size()];
		for (int i = 0; i < mail.size(); i++) {
			index[i] = mail.get(i).getAsunto().hashCode();
		}
		return index;
	}

	public void getMailBackDate(int[] index) {
		int evaluated = 0;
		List<Correo> aux = new ArrayList<Correo>();
		for (int i = 0; i < index.length; i++) {
			for (int j = 0; j < index.length; j++) {
				if (index[i] == mail.get(j).getFormatedDate().hashCode()
						&& evaluated != index.length) {
					aux.add(mail.get(j));
					evaluated++;
				}
			}
		}
		mail = aux;
	}

	public int[] getIndexDate() {
		int[] index = new int[mail.size()];
		for (int i = 0; i < mail.size(); i++) {
			index[i] = mail.get(i).getFormatedDate().hashCode();
		}
		return index;
	}

	public void orderByDate() {
		int[] index = getIndexDate();
		java.util.Arrays.sort(index);
		getMailBackDate(index);
	}

	public void clear() {
		this.user = null;
		this.login = null;
		this.password = null;
		this.entry = null;
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

	public void setContactos() {
		this.title = getLocaleString("contacts");
		current = "contacts";
	}

	public String setUsers() {
		this.title = getLocaleString("users");
		current = "users";
		return "users";
	}

	public String verContactos() {
		this.title = getLocaleString("contacts");
		current = "contacts";
		return "contacts";
	}

	public void setEnviados() {
		this.title = getLocaleString("sent");
		this.current = "sent";
		this.mail = user.getEnviados();
	}

	public void setEliminados() {
		this.title = getLocaleString("deleted");
		this.current = "deleted";
		this.mail = user.getEliminados();
	}

	public void setBorradores() {
		this.title = getLocaleString("drafts");
		this.current = "drafts";
		this.mail = user.getBorradores();
	}

	public String verEnviados() {
		this.title = getLocaleString("sent");
		this.current = "sent";
		this.mail = user.getEnviados();
		return "mail";
	}

	public String verEliminados() {
		this.title = getLocaleString("deleted");
		this.current = "deleted";
		this.mail = user.getEliminados();
		return "mail";
	}

	public String verBorradores() {
		this.title = getLocaleString("drafts");
		this.current = "drafts";
		this.mail = user.getBorradores();
		return "mail";
	}

	public List<Contacto> getContactos() {
		this.title = getLocaleString("contacts");
		return user.getContactos();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Correo> getMail() {
		return mail;
	}

	public void setMail(List<Correo> mail) {
		this.mail = mail;
	}

	private String getLocaleString(String string) {
		ResourceBundle bundle = FacesContext.getCurrentInstance()
				.getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "msgs");
		FacesMessage msg = new FacesMessage(bundle.getString(string));
		return msg.getSummary();
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
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

	public String getPage() {
		if (current.equals("sent") || current.equals("drafts")
				|| current.equals("deleted")) {
			return "mail";
		} else if (current.equals("users")) {
			return "users";
		} else if (current.equals("contacts")) {
			return "contacts";
		}
		return "";
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
