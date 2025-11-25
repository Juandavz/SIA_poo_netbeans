/*
package sia;

import java.util.Scanner; 

public class Estudiante {
    String nombre;
    double matematicas;
    double historia;
    double programacion;
    


    double calcularPromedio() {
        return (matematicas + historia + programacion) / 3;
    }

    void mostrarCalificaciones() {
        System.out.println("\n--- Calificaciones del Alumno ---");
        System.out.println("Alumno: " + nombre);
        System.out.println("Matemáticas: " + matematicas);
        System.out.println("Historia: " + historia);
        System.out.println("Programación: " + programacion);
        System.out.println("P.A.P.I: " + this.calcularPromedio());
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        Estudiante estudiante1 = new Estudiante();

        System.out.print("Ingrese el nombre del estudiante: ");
        estudiante1.nombre = scanner.nextLine();


        // Pedimos y asignamos las calificaciones
        System.out.print("Ingrese la calificación de Matemáticas: ");
        estudiante1.matematicas = scanner.nextDouble();

        System.out.print("Ingrese la calificación de Historia: ");
        estudiante1.historia = scanner.nextDouble();

        System.out.print("Ingrese la calificación de Programación: ");
        estudiante1.programacion = scanner.nextDouble();

        // Mostramos resultados
        estudiante1.mostrarCalificaciones();
        
        // Cerramos el scanner para liberar recursos y evitar fugas de memoria
        scanner.close();
    }
}
*/