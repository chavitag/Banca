package banca;

import Utils.Menu;
import Utils.Utilidades;
import java.util.Collection;

/**
 *
 * @author xavi
 */
public class MenuContaCorrente extends Menu {
    ContaBancariaCorrente cc;
    
    public MenuContaCorrente(ContaBancariaCorrente cc) {
        super(new String[]{ "Ver Domiciliaciones","Eliminar Domiciliación","Eliminar Recibo","Engadir Recibo","Saír"});
        this.cc=cc;
    }

    @Override
    public boolean menu(int opc) throws Exception {
        Collection <Entidad> autorizados;
        Entidad entidad;
        Domiciliacion dom;
        char choose;
        String codigo;
        String nomentidad;
        String concepto;
        double max_autorizado;

        switch(opc) {
            case 1:
                System.out.println("DOMICILIACIÓNS\n----------------");
                autorizados=cc.getAutorizados().values();
                if (autorizados.isEmpty()) System.out.println("Sen Domiciliacións");
                else                       {
                    for(Entidad e: autorizados) {
                        System.out.println(e.details());
                    }
                }
                break;
            case 2:
                codigo=Utilidades.getString("Código de Entidade: ");
                entidad=cc.getAutorizados().get(codigo);
                if (entidad==null) System.out.println("Entidade non rexistrada");
                else {
                    System.out.println(entidad.details());
                    choose=Utilidades.choose("Se eliminarán todos os recibos. (C)ontinuar? ", "Cc");
                    if (Character.toLowerCase(choose)=='c') {
                        cc.getAutorizados().remove(codigo);
                        AplicacionBanca.contas.update(cc);
                    }
                }
                break;
            case 3:
                codigo=Utilidades.getString("Código de Entidade: ");
                entidad=cc.getAutorizados().get(codigo);
                if (entidad==null) System.out.println("Entidade non rexistrada");
                else {
                    codigo=Utilidades.getString("Código de Domiciliación: ");
                    dom=entidad.getDomiciliaciones().get(codigo);
                    if (dom==null) System.out.println("A domiciliacion non existe");
                    else {
                        entidad.getDomiciliaciones().remove(codigo);
                        AplicacionBanca.contas.update(cc);
                    }
                }
                break;
            case 4:
                codigo=Utilidades.getString("Código de Entidade: ");
                entidad=cc.getAutorizados().get(codigo);
                if (entidad==null) {
                    nomentidad=Utilidades.getString("Nome Entidade: ");
                    max_autorizado=Utilidades.getDouble("Máximo Autorizado: ");
                    entidad=new Entidad(codigo,nomentidad,max_autorizado);
                    cc.getAutorizados().put(codigo,entidad);
                } else {
                    System.out.println("Engadindo Domiciliación de  "+entidad.details());
                }
                codigo=Utilidades.getString("Codigo Domiciliacion :");
                concepto=Utilidades.getString("Concepto: ");
                if (entidad.getDomiciliaciones().get(codigo)!=null) {
                    System.out.println("Ya domiciliado");
                } else {
                    dom=new Domiciliacion(codigo,concepto);
                    entidad.getDomiciliaciones().put(codigo,dom);
                    AplicacionBanca.contas.update(cc);
                }
                break;
            case 5:
                return true;
        }
        return false;
    }
    
}
