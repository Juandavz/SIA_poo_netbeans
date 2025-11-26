public class Asignatura {
    // Atributos (Encapsulamiento)
    private String codigo;
    private String nombre;
    private String facultad;
    private int creditos;
    private int cuposDisponibles;
    private String tipologia;

    // Constructor
    public Asignatura(String codigo, String nombre, String facultad, int creditos, int cupos, String tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.facultad = facultad;
        this.creditos = creditos;
        this.cuposDisponibles = cupos;
        this.tipologia = tipologia;
    }

    // Getters para acceder a la info
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getFacultad() { return facultad; }
    public int getCreditos() { return creditos; }
    public int getCuposDisponibles() { return cuposDisponibles; }
    public String getTipologia() {return tipologia;}

    // Método para mostrar resumen (útil para debug)
    @Override
    public String toString() {
        return nombre + " (" + creditos + " créditos)";
    }
}