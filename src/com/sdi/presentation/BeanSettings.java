package com.sdi.presentation;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "settings")
@SessionScoped
public class BeanSettings implements Serializable {

	private static final long serialVersionUID = 1319356059817952187L;
	private Locale locale = FacesContext.getCurrentInstance().getViewRoot()
			.getLocale();

	private static Map<String, String> countries;

	static {
		countries = new LinkedHashMap<String, String>();
		Iterator<Locale> local = FacesContext.getCurrentInstance()
				.getApplication().getSupportedLocales();
		Locale defolt = FacesContext.getCurrentInstance().getApplication()
				.getDefaultLocale();
		String name = defolt.getDisplayName(defolt);
		name = name.replaceFirst(String.valueOf(name.charAt(0)),
				String.valueOf(Character.toUpperCase(name.charAt(0))));
		System.out.println(name);
		countries.put(name, defolt.getLanguage());
		while (local.hasNext()) {
			Locale lol = local.next();
			name = lol.getDisplayName(lol);
			name = name.replaceFirst(String.valueOf(name.charAt(0)),
					String.valueOf(Character.toUpperCase(name.charAt(0))));
			countries.put(name, lol.getLanguage());
		}
	}

	public Map<String, String> getCountries() {
		return countries;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getLanguage() {
		return locale.getLanguage();
	}

	public void setLanguage(String language) {
		this.locale = new Locale(language);
	}

}
