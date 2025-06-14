package logica;

import datos.Usuario;
import datos.UsuarioDAO;

public class LoginService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticar(String correo, String contrasena) {
        if (correo == null || correo.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            return null;
        }
        return usuarioDAO.validarLogin(correo, contrasena);
    }
}
