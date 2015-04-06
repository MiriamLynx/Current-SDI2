package com.sdi.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.ContactoService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Contacto;
import com.sdi.model.Usuario;

@ManagedBean(name = "contacts")
@SessionScoped
public class BeanContactos implements Serializable {

	private static final long serialVersionUID = -6661030588868331188L;

	// visualizando
	private List<Contacto> contacts;

	// criterio de busqueda
	private String entry;

	// nuevo contacto
	private String name;
	private String surname;
	private String address;
	private String city;
	private String email;

	public void addContact() {
		ContactoService cs = Factories.services.createContactoService();
		Contacto c = new Contacto();
		c.setNombre(name);
		c.setApellidos(surname);
		c.setDireccion(address);
		c.setEmail(email);
		c.setCiudad(city);
		c.setAgenda_Usuario(((Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("USER")).getLogin());
		cs.addContact(c);
		((Usuario) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("USER")).addContact(c);
		contacts.add(c);
	}

	public void refreshContacts() {
		setContactos();
		List<Contacto> filter = new ArrayList<Contacto>();
		for (int i = 0; i < contacts.size(); i++) {
			if (!(contacts.get(i).getNombre().contains(entry))
					&& !(contacts.get(i).getApellidos().contains(entry))
					&& !(contacts.get(i).getEmail().contains(entry))
					&& !(contacts.get(i).getAgenda_Usuario().contains(entry))) {
				filter.add(contacts.get(i));
			}
		}
		for (Contacto c : filter) {
			contacts.remove(c);
		}
	}

	public void setContactos() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("PAGE", "contacts");
		this.contacts = ((Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("USER"))
				.getContactos();
	}

	public String verContactos() {
		setContactos();
		return "contacts";
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Contacto> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contacto> contacts) {
		this.contacts = contacts;
	}

}
