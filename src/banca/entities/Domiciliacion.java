package banca.entities;

/**
 *  Representa un recibo domiciliado
 */
public class Domiciliacion {
    private final String codigo;    // Código de Domiciliación (se suministra nos cargos)
    private final String concepto;  // Concepto

    /**
     * Constructor
     * @param codigo
     * @param concepto 
     */
    public Domiciliacion(String codigo, String concepto) {
        this.codigo=codigo;
        this.concepto=concepto;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getConcepto() {
        return concepto;
    }
    
    @Override
    public String toString() {
        return codigo+": "+concepto;
    }
}
