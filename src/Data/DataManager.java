package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
// Asumimos que la clase Asignatura existe en el paquete Data
// import Data.Asignatura; 

public class DataManager {

    public static List<Object[]> obtenerAsignaturasPorEstudiante(String usuarioLogueado) {
        ArrayList<Object[]> lista = new ArrayList<>();

        // El formato es: [Asignatura (con código), Créditos, Nota, Periodo,
        // Calificación]
        // Se debe normalizar la "Calificación" a un tipo de materia simple (ej. 'Fun.
        // Obligatoria' -> 'FO')

        try (BufferedReader br = new BufferedReader(new FileReader("historia.txt"))) {
            String linea;
            String userOwner = "", asigCompleta = "", cred = "", nota = "", per = "", cal = "";

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                if (linea.startsWith("Estudiante:")) {
                    userOwner = linea.substring(11).trim();
                } else if (userOwner.equals(usuarioLogueado)) {
                    // Si la línea pertenece al usuario logueado, extraemos el resto de los campos
                    if (linea.startsWith("Asignatura:"))
                        asigCompleta = linea.substring(11).trim();
                    else if (linea.startsWith("Creditos:"))
                        cred = linea.substring(9).trim();
                    else if (linea.startsWith("Nota:"))
                        nota = linea.substring(5).trim();
                    else if (linea.startsWith("Periodo:"))
                        per = linea.substring(8).trim();
                    else if (linea.startsWith("Calificacion:"))
                        cal = linea.substring(13).trim();

                    if (linea.startsWith("=====")) {
                        if (!asigCompleta.isEmpty() && !cred.isEmpty()) {
                            // Orden exacto: Asignatura | Créditos | Nota | Periodo | Calificación
                            // Nota: Se asume que la nota y los créditos son parseables a números.
                            lista.add(new Object[] { asigCompleta, cred, nota, per, cal });
                        }
                        // Reiniciar variables para la siguiente asignatura (aunque el userOwner se
                        // mantiene)
                        asigCompleta = "";
                        cred = "";
                        nota = "";
                        per = "";
                        cal = "";
                    }
                } else if (linea.startsWith("=====")) {
                    // Si el estudiante no es el dueño, reiniciamos el dueño para buscar el
                    // siguiente
                    userOwner = "";
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo historia.txt: " + e.getMessage());
        }
        return lista;
    }
    
    

    /**
     * Método que convierte la lista en una matriz para la JTable.
     */
    public static Object[][] cargarHistoria(String usuarioLogueado) {
        List<Object[]> lista = obtenerAsignaturasPorEstudiante(usuarioLogueado);
        if (lista.isEmpty()) {
            return new Object[][] { { "No se encontraron datos", "", "", "", "" } };
        }
        return lista.toArray(new Object[0][0]);
    }

    // --- NUEVO: LISTA DE OBJETOS EN MEMORIA ---
    public static ArrayList<Asignatura> listaAsignaturas = new ArrayList<>();

    // =======================================================
    // --- METODOS DE AUTENTICACION Y HORARIO (TU CODIGO) ---
    // =======================================================

    // --- LECTURA DE USUARIOS ---
    public static boolean validarUsuario(String userIngresado, String passIngresado) {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linea;
            String userActual = "";
            String passActual = "";
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("Usuario:"))
                    userActual = linea.substring(8).trim();
                else if (linea.startsWith("Clave:"))
                    passActual = linea.substring(6).trim();
                else if (linea.startsWith("=====")) {
                    if (userActual.equals(userIngresado) && passActual.equals(passIngresado))
                        return true;
                    userActual = "";
                    passActual = "";
                }
            }
        } catch (Exception e) {
            System.out.println("Error usuarios: " + e.getMessage());
        }
        return false;
    }

    // --- LECTURA DE HORARIO ---
    public static Object[][] cargarHorario(String usuarioLogueado) {
        ArrayList<Object[]> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("horario.txt"))) {
            String linea;
            String userOwner = "", cod = "", asig = "", grp = "", doc = "", dia = "", hora = "", sal = "";
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("Estudiante:"))
                    userOwner = linea.substring(11).trim();
                else if (linea.startsWith("Codigo:"))
                    cod = linea.substring(7).trim();
                else if (linea.startsWith("Asignatura:"))
                    asig = linea.substring(11).trim();
                else if (linea.startsWith("Grupo:"))
                    grp = linea.substring(6).trim();
                else if (linea.startsWith("Docente:"))
                    doc = linea.substring(8).trim();
                else if (linea.startsWith("Dia:"))
                    dia = linea.substring(4).trim();
                else if (linea.startsWith("Hora:"))
                    hora = linea.substring(5).trim();
                else if (linea.startsWith("Salon:"))
                    sal = linea.substring(6).trim();
                else if (linea.startsWith("=====")) {
                    if (userOwner.equals(usuarioLogueado) && !cod.isEmpty()) {
                        lista.add(new Object[] { cod, asig, grp, doc, dia, hora, sal });
                    }
                    userOwner = "";
                    cod = "";
                    asig = "";
                    grp = "";
                    doc = "";
                    dia = "";
                    hora = "";
                    sal = "";
                }
            }
        } catch (Exception e) {
            return new Object[][] { { "Error", "", "", "", "", "", "" } };
        }
        return lista.toArray(new Object[0][0]);
    }

    // --- LECTURA DE CATALOGO ---
    public static Object[][] cargarCatalogo() {
        listaAsignaturas.clear();
        ArrayList<Object[]> datosParaTabla = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("catalogo.txt"))) {
            String linea;
            String cod = "", asig = "", fac = "", cred = "", cupos = "", tipo = "";

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("Codigo:"))
                    cod = linea.substring(7).trim();
                else if (linea.startsWith("Asignatura:"))
                    asig = linea.substring(11).trim();
                else if (linea.startsWith("Facultad:"))
                    fac = linea.substring(9).trim();
                else if (linea.startsWith("Creditos:"))
                    cred = linea.substring(9).trim();
                else if (linea.startsWith("Cupos:"))
                    cupos = linea.substring(6).trim();
                else if (linea.startsWith("Tipologia:"))
                    tipo = linea.substring(10).trim();
                else if (linea.startsWith("=====")) {
                    if (!cod.isEmpty()) {
                        int creditosInt = Integer.parseInt(cred);
                        int cuposInt = Integer.parseInt(cupos);
                        // Asignatura nuevaAsignatura = new Asignatura(cod, asig, fac, creditosInt,
                        // cuposInt, tipo);
                        // listaAsignaturas.add(nuevaAsignatura); // Descomentar si la clase Asignatura
                        // existe
                        datosParaTabla.add(new Object[] { cod, asig, fac, cred, cupos, tipo });
                    }
                    cod = "";
                    asig = "";
                    fac = "";
                    cred = "";
                    cupos = "";
                    tipo = "";
                }
            }
        } catch (Exception e) {
            return new Object[][] { { "Error", "Error leyendo catalogo.txt", "", "", "" } };
        }
        return datosParaTabla.toArray(new Object[0][0]);
    }

    // =======================================================
    // --- NUEVOS MÉTODOS DE RESUMEN DE CRÉDITOS ---
    // =======================================================

    /**
     * Lee los datos de la primera tabla del resumen (Exigidos, Aprobados,
     * Pendientes, etc.)
     * del archivo 'resumen.txt'.
     * 
     * @return Matriz de Objetos [Filas (tipos), Columnas (categorías de créditos)]
     */
    public static Object[][] cargarDatosResumen() {
        ArrayList<Object[]> lista = new ArrayList<>();
        boolean leyendoTabla1 = true;

        try (BufferedReader br = new BufferedReader(new FileReader("resumen.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                if (linea.startsWith("#"))
                    continue; // Ignorar comentarios

                if (linea.equals("|")) { // Separador entre tablas
                    leyendoTabla1 = false;
                    continue;
                }

                if (leyendoTabla1 && !linea.isEmpty()) {
                    // La primera tabla usa el punto y coma (;) como delimitador
                    String[] partes = linea.split(";");
                    if (partes.length == 10) {
                        // Creamos una fila de 10 elementos (Texto + 9 números)
                        lista.add(partes);
                    }
                } else if (!leyendoTabla1) {
                    // Ya hemos pasado a la segunda tabla, detenemos la lectura
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar resumen de créditos: " + e.getMessage());
            return new Object[][] { { "Error", "No se lee resumen.txt", "", "", "", "", "", "", "", "" } };
        }
        return lista.toArray(new Object[0][0]);
    }

    /**
     * Lee los datos de la segunda tabla del resumen (Créditos adicionales, cupo,
     * etc.)
     * del archivo 'resumen.txt'.
     * 
     * @return Matriz de Objetos [Métrica, Valor]
     */
    public static Object[][] cargarResumenFinal() {
        ArrayList<Object[]> lista = new ArrayList<>();
        boolean leyendoTabla2 = false;

        try (BufferedReader br = new BufferedReader(new FileReader("resumen.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                if (linea.startsWith("#"))
                    continue; // Ignorar comentarios

                if (linea.equals("|")) { // Si encontramos el separador, empezamos a leer la Tabla 2
                    leyendoTabla2 = true;
                    continue;
                }

                if (leyendoTabla2 && !linea.isEmpty()) {
                    // La segunda tabla usa el punto y coma (;) como delimitador
                    String[] partes = linea.split(";");
                    if (partes.length == 2) {
                        // Creamos una fila de 2 elementos (Texto + Número)
                        lista.add(partes);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar resumen final: " + e.getMessage());
            return new Object[][] { { "Error", "No se lee resumen.txt" } };
        }
        return lista.toArray(new Object[0][0]);
    }
}