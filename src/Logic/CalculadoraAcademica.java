package Logic;

import java.util.List;

/**
 * Calculadora académica que procesa la historia del estudiante
 * y permite obtener:
 *  - PAPA
 *  - Créditos aprobados
 *  - Créditos cursados
 *  - Estadísticas por categoría (Fundamental, Optativa, Libre elección)
 */
public class CalculadoraAcademica {

    private final List<Object[]> asignaturas;

    /**
     * Cada asignatura debe tener esta estructura:
     *  [0] Asignatura  (String)
     *  [1] Créditos    (int)
     *  [2] Nota        (double)
     *  [3] Periodo     (String)
     *  [4] Calificación (String: "Fun. Obligatoria", "optativa", "libre eleccion")
     */
    public CalculadoraAcademica(List<Object[]> asignaturas) {
        this.asignaturas = asignaturas;
    }

    // =======================================================
    // -------- CÁLCULO PRINCIPAL: PAPA y Créditos -----------
    // =======================================================

    /**
     * Calcula el PAPA, créditos aprobados y créditos cursados.
     * @return double[] {PAPA, CréditosAprobados, CréditosCursados}
     */
    public double[] calcularMetricas() {
        double sumaPonderada = 0.0;
        int totalCreditosAprobados = 0;
        int totalCreditosCursados = 0;

        for (Object[] asignatura : asignaturas) {
            try {
                int creditos = Integer.parseInt(asignatura[1].toString());
                double nota = Double.parseDouble(asignatura[2].toString());

                sumaPonderada += (nota * creditos);
                totalCreditosCursados += creditos;

                // Aprobada si nota >= 3.0
                if (nota >= 3.0) {
                    totalCreditosAprobados += creditos;
                }

            } catch (NumberFormatException e) {
                System.err.println("Error al procesar créditos o nota: " + e.getMessage());
            }
        }

        double papa = (totalCreditosCursados > 0)
                ? (sumaPonderada / totalCreditosCursados)
                : 0.0;

        return new double[]{papa, totalCreditosAprobados, totalCreditosCursados};
    }

    // =======================================================
    // --------- CÁLCULO POR CATEGORÍAS -----------------------
    // =======================================================

    /**
     * Devuelve los créditos cursados y aprobados por categoría.
     */
    public CategoryResult calcularPorCategorias() {
        CategoryResult result = new CategoryResult();

        for (Object[] a : asignaturas) {
            try {
                int creditos = Integer.parseInt(a[1].toString());
                double nota = Double.parseDouble(a[2].toString());
                String categoria = a[4].toString().toLowerCase();

                // Clasificación
                if (categoria.contains("fun") || categoria.contains("oblig")) {
                    result.cursadosFund += creditos;
                    if (nota >= 3.0) result.aprobadosFund += creditos;
                }
                else if (categoria.contains("opt")) {
                    result.cursadosOpt += creditos;
                    if (nota >= 3.0) result.aprobadosOpt += creditos;
                }
                else if (categoria.contains("libre")) {
                    result.cursadosLibre += creditos;
                    if (nota >= 3.0) result.aprobadosLibre += creditos;
                }

            } catch (Exception e) {
                System.err.println("Error procesando categoría: " + e.getMessage());
            }
        }

        return result;
    }

    // =======================================================
    // ---------------- AVANCE EN PORCENTAJE ------------------
    // =======================================================

    /**
     * Calcula el porcentaje de avance en créditos.
     */
    public double calcularPorcentajeAvance(double creditosAprobados, int creditosTotales) {
        if (creditosTotales <= 0) return 0.0;
        return (creditosAprobados / creditosTotales) * 100.0;
    }
}
