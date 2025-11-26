import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class DataManager {

    // --- LECTURA DE USUARIOS ---
    public static boolean validarUsuario(String userIngresado, String passIngresado) {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linea;
            String userActual = "";
            String passActual = "";
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("Usuario:")) userActual = linea.substring(8).trim();
                else if (linea.startsWith("Clave:")) passActual = linea.substring(6).trim();
                else if (linea.startsWith("=====")) {
                    if (userActual.equals(userIngresado) && passActual.equals(passIngresado)) return true;
                    userActual = ""; passActual = "";
                }
            }
        } catch (Exception e) { System.out.println("Error usuarios: " + e.getMessage()); }
        return false;
    }
    
    // --- LECTURA DE HORARIO ---
    public static Object[][] cargarHorario(String usuarioLogueado) {
        ArrayList<Object[]> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("horario.txt"))) {
            String linea;
            String userOwner="", cod="", asig="", grp="", doc="", dia="", hora="", sal="";
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("Estudiante:")) userOwner = linea.substring(11).trim();
                else if (linea.startsWith("Codigo:")) cod = linea.substring(7).trim();
                else if (linea.startsWith("Asignatura:")) asig = linea.substring(11).trim();
                else if (linea.startsWith("Grupo:")) grp = linea.substring(6).trim();
                else if (linea.startsWith("Docente:")) doc = linea.substring(8).trim();
                else if (linea.startsWith("Dia:")) dia = linea.substring(4).trim();
                else if (linea.startsWith("Hora:")) hora = linea.substring(5).trim();
                else if (linea.startsWith("Salon:")) sal = linea.substring(6).trim();
                else if (linea.startsWith("=====")) {
                    if (userOwner.equals(usuarioLogueado) && !cod.isEmpty()) {
                        lista.add(new Object[]{cod, asig, grp, doc, dia, hora, sal});
                    }
                    userOwner=""; cod=""; asig=""; grp=""; doc=""; dia=""; hora=""; sal="";
                }
            }
        } catch (Exception e) { return new Object[][]{{"Error", "", "", "", "", "", ""}}; }
        return lista.toArray(new Object[0][0]);
    }

    // --- LECTURA DE HISTORIA (ACTUALIZADO A TU IMAGEN) ---
    public static Object[][] cargarHistoria(String usuarioLogueado) {
        ArrayList<Object[]> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("historia.txt"))) {
            String linea;
            String userOwner="", asig="", cred="", nota="", per="", cal="";

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("Estudiante:")) userOwner = linea.substring(11).trim();
                else if (linea.startsWith("Asignatura:")) asig = linea.substring(11).trim();
                else if (linea.startsWith("Creditos:")) cred = linea.substring(9).trim();
                else if (linea.startsWith("Nota:")) nota = linea.substring(5).trim();
                else if (linea.startsWith("Periodo:")) per = linea.substring(8).trim();
                else if (linea.startsWith("Calificacion:")) cal = linea.substring(13).trim();
                else if (linea.startsWith("=====")) {
                    if (userOwner.equals(usuarioLogueado) && !asig.isEmpty()) {
                        // Orden exacto de la imagen: Asignatura | Créditos | Nota | Periodo | Calificación
                        lista.add(new Object[]{asig, cred, nota, per, cal});
                    }
                    userOwner=""; asig=""; cred=""; nota=""; per=""; cal="";
                }
            }
        } catch (Exception e) { return new Object[][]{{"Error", "No se lee historia.txt", "", "", ""}}; }
        return lista.toArray(new Object[0][0]);
    }
    
    // --- LECTURA DE CATÁLOGO (Agregar al final de DataManager.java) ---
    public static Object[][] cargarCatalogo() {
        ArrayList<Object[]> lista = new ArrayList<>();
        // Intenta leer el archivo catalogo.txt
        try (BufferedReader br = new BufferedReader(new FileReader("catalogo.txt"))) {
            String linea;
            String cod="", asig="", fac="", cred="", cupos="";

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("Codigo:")) cod = linea.substring(7).trim();
                else if (linea.startsWith("Asignatura:")) asig = linea.substring(11).trim();
                else if (linea.startsWith("Facultad:")) fac = linea.substring(9).trim();
                else if (linea.startsWith("Creditos:")) cred = linea.substring(9).trim();
                else if (linea.startsWith("Cupos:")) cupos = linea.substring(6).trim();
                else if (linea.startsWith("=====")) {
                    if (!cod.isEmpty()) {
                        lista.add(new Object[]{cod, asig, fac, cred, cupos});
                    }
                    cod=""; asig=""; fac=""; cred=""; cupos="";
                }
            }
        } catch (Exception e) { 
            // Si falla, devuelve un error visible en la tabla
            return new Object[][]{{"Error", "Falta catalogo.txt", "", "", ""}}; 
        }
        return lista.toArray(new Object[0][0]);
    }
}