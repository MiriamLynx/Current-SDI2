package com.sdi.business.impl.classes.usuario;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;
import com.sdi.persistence.UsuarioDao;

public class FindAllActivated {

	public List<Usuario> findActivated() {
		UsuarioDao dao = Factories.persistence.createUsuarioDao();
		return dao.getUsuariosActivos();
	}

}
