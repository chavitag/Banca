package banca;

/**
 *
 * @author xavi
 */
public class Domiciliacion {
    private final String codigo;
    private final String concepto;

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
