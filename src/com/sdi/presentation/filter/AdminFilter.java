package com.sdi.presentation.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdi.model.Usuario;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter(urlPatterns = { "/users.xhtml", "/editUser.xhtml" }, initParams = {
		@WebInitParam(name = "Denied", value = "/denied.xhtml"),
		@WebInitParam(name = "Index", value = "/index.xhtml") })
public class AdminFilter implements Filter {

	FilterConfig config = null;

	/**
	 * Default constructor.
	 */
	public AdminFilter() {

	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// Si no es petición HTTP nada que hacer
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}
		// En el resto de casos se verifica que se haya hecho login previamente
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		Usuario user = (Usuario) session.getAttribute("USER");
		if (user == null) {
			String index = config.getInitParameter("Index");
			// Si no hay login, redirección al formulario de login
			res.sendRedirect(req.getContextPath() + index);
			return;
		} else if (!user.getRol().equals("Administrador")) {
			String denied = config.getInitParameter("Denied");
			// Si no hay login, redirección al formulario de login
			res.sendRedirect(req.getContextPath() + denied);
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		config = fConfig;
	}

}
