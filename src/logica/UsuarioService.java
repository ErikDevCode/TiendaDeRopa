package logica;

import datos.Usuario;
import datos.UsuarioDAO;

public class UsuarioService {
	public boolean registrarUsuario(Usuario usuario) {
	    return new UsuarioDAO().registrar(usuario);
	}
}
