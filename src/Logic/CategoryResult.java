package Logic;

/**
 * Clase contenedora para almacenar los créditos cursados y aprobados
 * por categoría académica.
 *
 * Usada por CalculadoraAcademica.calcularPorCategorias()
 */
public class CategoryResult {

    // ====== FUNDAMENTAL / OBLIGATORIA ======
    public int cursadosFund = 0;
    public int aprobadosFund = 0;

    // ====== OPTATIVA ======
    public int cursadosOpt = 0;
    public int aprobadosOpt = 0;

    // ====== LIBRE ELECCIÓN ======
    public int cursadosLibre = 0;
    public int aprobadosLibre = 0;

    // ============================================================
    // Métodos auxiliares
    // ============================================================

    public int getTotalCursados() {
        return cursadosFund + cursadosOpt + cursadosLibre;
    }

    public int getTotalAprobados() {
        return aprobadosFund + aprobadosOpt + aprobadosLibre;
    }

    @Override
    public String toString() {
        return "CategoryResult{" +
                "cursadosFund=" + cursadosFund +
                ", aprobadosFund=" + aprobadosFund +
                ", cursadosOpt=" + cursadosOpt +
                ", aprobadosOpt=" + aprobadosOpt +
                ", cursadosLibre=" + cursadosLibre +
                ", aprobadosLibre=" + aprobadosLibre +
                '}';
    }
}
