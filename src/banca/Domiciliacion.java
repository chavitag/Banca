package banca;

/**
 *
 * @author xavi
 */
public class Domiciliacion {
    private final String codigo;    // 8 chars
    private final String concepto;  // 30 chars

    Domiciliacion(String codigo, String concepto) {
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
