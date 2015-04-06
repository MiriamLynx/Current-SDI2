package com.sdi.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.CorreoService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Contacto;
import com.sdi.model.Correo;
import com.sdi.model.Usuario;

@ManagedBean(name = "mail")
@SessionScoped
public class BeanCorreos implements Serializable {

	private static final long serialVersionUID = -6661030588868331188L;

	// visualizando
	private static List<Correo> mail;

	// criterio de busqueda
	private String entry;

	// enviar correo
	private List<Contacto> recipients;
	private String subject;
	private String body;

	public static void init() {
		mail = ((Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("USER"))
				.getEnviados();
	}

	public String load() {
		if (FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("draft") != null) {
			Integer id = Integer
					.parseInt(FacesContext.getCurrentInstance()
							.getExternalContext().getRequestParameterMap()
							.get("draft"));
			Correo correo = ((Usuario) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("USER"))
					.getBorrador(id);
			this.recipients = correo.getDestinatarios();
			this.subject = correo.getAsunto();
			this.body = correo.getCuerpo();
			return "editDraft";
		}
		return "";
	}

	public void store() {
		this.recipients = null;
		this.subject = null;
		this.body = null;
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

	public String saveDraftAction() {
		saveDraft();
		setPage();
		return "mail";
	}

	public String sendAction() {
		send();
		setPage();
		return "mail";
	}

	public void saveDraft() {
		CorreoService cs = Factories.services.createCorreoService();
		Correo correo = new Correo();
		correo.setAsunto(subject);
		correo.setCuerpo(body);
		correo.setDestinatarios(recipients);
		correo.setCarpeta(2);
		correo.setLogin_Usuario(((Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("USER")).getLogin());
		correo.setFechahora(System.currentTimeMillis());
		cs.save(correo);
		((Usuario) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("USER")).getCorreos().add(correo);
		clear();
	}

	public void send() {
		CorreoService cs = Factories.services.createCorreoService();
		Correo correo = new Correo();
		correo.setAsunto(subject);
		correo.setCuerpo(body);
		correo.setDestinatarios(recipients);
		correo.setCarpeta(1);
		correo.setLogin_Usuario(((Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("USER")).getLogin());
		correo.setFechahora(System.currentTimeMillis());
		cs.save(correo);
		((Usuario) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("USER")).getCorreos().add(correo);
		clear();
	}

	private void clear() {
		subject = null;
		recipients = null;
		body = null;
	}

	public void setPage() {
		String page = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("PAGE");
		if (page.equals("sent")) {
			setEnviados();
		} else if (page.equals("drafts")) {
			setBorradores();
		} else if (page.equals("deleted")) {
			setEliminados();
		}
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

	public void setEnviados() {
		if (FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("USER") != null) {
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("PAGE", "sent");
			mail = ((Usuario) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("USER"))
					.getEnviados();
		}
	}

	public void setEliminados() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("PAGE", "deleted");
		mail = ((Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("USER"))
				.getEliminados();
	}

	public void setBorradores() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("PAGE", "drafts");
		mail = ((Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("USER"))
				.getBorradores();
	}

	public String verEnviados() {
		setEnviados();
		return "mail";
	}

	public String verEliminados() {
		setEliminados();
		return "mail";
	}

	public String verBorradores() {
		setBorradores();
		return "mail";
	}

	public List<Correo> getMail() {
		return mail;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public List<Contacto> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<Contacto> recipients) {
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
