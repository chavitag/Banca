package banca.entities;

import java.util.Collection;
import java.util.HashMap;

/**
 * ContaBancariaCorrente é unha ContaBancaria.
 * E abstracta, xa que é demasiado xenérica para ser instanciada.
 * 
 * As contas correntes teñen unha lista de Entidades autorizadas
 */
public abstract class ContaBancariaCorrente extends ContaBancaria {
    private final HashMap <String,Autorizacion> listaAutorizados=new HashMap<>();
    
    /**
     * Constructor.
     * @param cliente - Cliente ao que pertence a conta
     * @param ccc  - Número de conta corrente
     */
    public ContaBancariaCorrente(Cliente cliente,String ccc) {
        super(cliente,ccc);
    }

    /**
     * Autoriza unha Entidade ao cobro de recibos.
     * @param entidad - Entidade autorizada
     * @param max - Máximo autorizado
     */
    public void autoriza(Entidad entidad,double max) {
        listaAutorizados.put(entidad.getCodigo(),new Autorizacion(entidad,max));
    }
    
    /**
     * Domicilia un recibo na conta corrente.A entidade debe estar autorizada.
     * @param codigoentidad - Código de entidade
     * @param codigodomiciliacion - Código de domiciliacion
     * @param concepto - Concepto de domiciliacion
     */
    public void domicilia(String codigoentidad,String codigodomiciliacion,String concepto)  {
        Autorizacion aut=listaAutorizados.get(codigoentidad);
        if (aut==null) throw new IllegalArgumentException("A entidade non está autorizada");
        aut.domicilia(codigodomiciliacion, concepto);
    }
    
    /**
     * Devolve a lista de autorizacións para esta conta
     * @return HashMap de autorizacions
     */
    public HashMap <String,Autorizacion> getAutorizados() {
        return listaAutorizados;
    }
   
}
