public class Estudiante {
    private String usuario;
    private String clave;
    private String nombreCompleto;
    private String rol; // "estudiante" o "admin"

    public Estudiante(String usuario, String clave, String nombre, String rol) {
        this.usuario = usuario;
        this.clave = clave;
        this.nombreCompleto = nombre;
        this.rol = rol;
    }

    public String getUsuario() { return usuario; }
    public String getNombreCompleto() { return nombreCompleto; }
    public boolean esAdmin() { return "admin".equalsIgnoreCase(rol); }
}