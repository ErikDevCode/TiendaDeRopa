package datos;

public class Usuario {
    private int id;
    private String correo;
    private String contrasena;
    private String nombreCompleto;
    private int rolId;
    private String rolNombre;
	
 // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(int id, String correo, String contrasena, String nombreCompleto, int rolId, String rolNombre) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.rolId = rolId;
        this.rolNombre = rolNombre;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public int getRolId() { return rolId; }
    public void setRolId(int rolId) { this.rolId = rolId; }

	public String getRolNombre() {
		return rolNombre;
	}

	public void setRolNombre(String rolNombre) {
		this.rolNombre = rolNombre;
	}
}
