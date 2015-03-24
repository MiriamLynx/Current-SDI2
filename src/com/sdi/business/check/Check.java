package com.sdi.business.check;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.sdi.business.exception.BusinessException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;

public class Check {

	public static Usuario getUsuarioByLogin(String login) throws SQLException {
		return Factories.persistence.createUsuarioDao().findByLogin(login);
	}

	public static void throwError(HttpServletRequest request, String error)
			throws BusinessException {
		request.setAttribute("error", error);
		throw new BusinessException(error);
	}

	public static int checkSession(Usuario user, String password) {
		if (!user.getPasswd().equals(password)) {
			return -1;
		} else {
			if (!user.isActivo()) {
				return -2;
			}
		}
		return 0;
	}

	public static boolean checkPassword(String password, String repeatpassword) {
		if (password.length() > 0 && repeatpassword.length() > 0) {
			if (password.equals(repeatpassword)) {
				return true;
			}
		}
		return false;
	}

	public static boolean checkCurrentPassword(Usuario user,
			String currentpassword) {
		if (currentpassword.length() > 0) {
			if (currentpassword.equals(user.getPasswd())) {
				return true;
			}
		}
		return false;
	}

	public static String date(double d) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date now = new Date((long) d);
		String strDate = sdfDate.format(now);
		return strDate;
	}

}
