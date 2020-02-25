package banca.entities;

import banca.AplicacionBanca;
import java.util.Collection;
import java.util.HashMap;

/**
 * Clase Autorización. Almacena as domiciliacións e o máximo autorizado para a entidade
 * correspondente.
 */
public class Autorizacion {
    private final String entidade; // Código da entidade
    private double max_autorizado; // A entidade non pode cargar un recibo maior a este importe
    private final HashMap <String,Domiciliacion> domiciliaciones=new HashMap<>(); // Domiciliacións autorizadas da entidade
    
    /**
     * Constructor.
     * @param entidade Entidade a autorizar
     * @param max_autorizado Impore máximo a cargar pola entidad
     */
    public Autorizacion(Entidad entidade,double max_autorizado) {
        this.entidade=entidade.getKey();
        this.max_autorizado=max_autorizado;
    }
    
    /**
     * Domicilia un recibo da entidade
     * @param codigo - Código de domiciliación... se envía xunto cos cargos.
     * @param concepto - Concepto da domiciliación (Fenosa, Netflix, Amazon Prime... etc)
     */
    public void domicilia(String codigo,String concepto) {
        Domiciliacion d=new Domiciliacion(codigo,concepto);
        domiciliaciones.put(codigo,d);
    }

    /**
     * Devolve a Entidad autorizada
     * @return Entidad Entidad autorizada
     */
    public Entidad getEntidad() {
        return AplicacionBanca.ENTIDADES.load(entidade);
    }

    /**
     * Devolve o máximo autorizado para a entidad
     * @return máximo autorizado
     */
    public double getMax_autorizado() {
        return max_autorizado;
    }
    
    /**
     * Cambia o máximo autorizado para a entidade.
     * @param max_autorizado Importe máximo a autorizar.
     */
    public void setMax_autorizado(double max_autorizado) {
        this.max_autorizado = max_autorizado;
    }
    
    /**
     * Devolve un String cos detalles da autorización para poder ser visualizados.
     * @return String cos detalles
     */
    public String details() {
        String result=this+"\nLista de Recibos:\n";
        Collection <Domiciliacion> list=domiciliaciones.values();
        for(Domiciliacion d: list) {
            result+="\t"+d+"\n";
        }
        return result;
    }
    
    /**
     * Devolve a lista de domiciliacións
     * @return HashMap de Domiciliacions.
     */
    public HashMap <String,Domiciliacion> getDomiciliaciones() {
        return domiciliaciones;
    }
    
    /**
     * Sobreposición do método toString de Object
     * @return String representando esta Autorización
     */
    @Override
    public String toString() {
        Entidad e=getEntidad();
        return e+" (Max Autorizado "+max_autorizado+"€)";
    }    
}
