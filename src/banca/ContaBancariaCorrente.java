package banca;

import java.util.HashMap;

/**
 *
 * @author xavi
 */
public abstract class ContaBancariaCorrente extends ContaBancaria {
    private final HashMap <String,Entidad> listaAutorizados=new HashMap<>();
    
    public ContaBancariaCorrente(Cliente cliente,String ccc) {
        super(cliente,ccc);
    }

    public void autoriza(Entidad entidad) {
        if (entidad!=null) listaAutorizados.put(entidad.getCodigo(),entidad);
    }
    
    public void domicilia(String codigoentidad,String codigodomiciliacion,String concepto) throws Exception {
        Entidad entidad=listaAutorizados.get(codigoentidad);
        if (entidad==null) throw new Exception("A entidade non está autorizada");
        entidad.domicilia(codigodomiciliacion, concepto);
    }
    
    public HashMap <String,Entidad> getAutorizados() {
        return listaAutorizados;
    }
   
}
