package com.sdi.presentation;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "settings")
@SessionScoped
public class BeanSettings implements Serializable {

	private static final long serialVersionUID = -818367645190405771L;

	private static final Locale ENGLISH = new Locale("en");
	private static final Locale SPANISH = new Locale("es");

	private Locale locale = SPANISH;

	public Locale getLocale() {
		return locale;
	}

	public void setSpanish(ActionEvent event) {
		locale = SPANISH;
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	}

	public void setEnglish(ActionEvent event) {
		locale = ENGLISH;
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	}

}
