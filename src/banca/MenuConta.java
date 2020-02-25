package banca;

import Utils.Menu;
import Utils.Utilidades;
import java.util.Collection;

/**
 *
 * @author xavi
 */
public class MenuConta extends Menu {
    ContaBancaria cc;
    ContaBancariaCorrente cbc;
    
    public MenuConta(ContaBancariaCorrente cc) {
        super("Xestión de "+cc.getCcc(),
              new String[]{ "Información",
                            "Realizar Ingreso",
                            "Realizar Reintegro",
                            "Ver Domiciliaciones",
                            "Eliminar Domiciliación",
                            "Eliminar Recibo",
                            "Engadir Recibo",
                            "Saír"});
        this.cc=cc;
        this.cbc=cc;
    }
    
    public MenuConta(ContaBancariaAforro cc) {
        super("Xestión de "+cc,
              new String[]{ "Información",
                            "Realizar Ingreso",
                            "Realizar Reintegro",
                            "Saír"});
        this.cc=cc;
        this.cbc=null;
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
        double num;

        switch(opc) {
            case 1:
                System.out.println(cc.details());
                Utilidades.getString("Pulsa Enter para continuar...");
                break;
            case 2:
                num=Utilidades.getDouble("Cantidade a Ingresar: ");
                num=cc.ingreso(num);
                AplicacionBanca.CONTAS.update(cc);
                System.out.println("O novo saldo é de "+num);
                Utilidades.getString("Pulsa Enter para continuar...");
                break;
            case 3:
                num=Utilidades.getDouble("Cantidade a Retirar: ");
                num=cc.reintegro(num);
                AplicacionBanca.CONTAS.update(cc);
                System.out.println("O novo saldo é de "+num);
                Utilidades.getString("Pulsa Enter para continuar...");
                break;
            case 4:
                if (cbc==null) return true;
                
                System.out.println("DOMICILIACIÓNS\n----------------");
                autorizados=cbc.getAutorizados().values();
                if (autorizados.isEmpty()) System.out.println("Sen Domiciliacións");
                else                       {
                    for(Entidad e: autorizados) {
                        System.out.println(e.details());
                    }
                }
                Utilidades.getString("Pulsa Enter para continuar...");
                break;
            case 5:
                if (cbc==null) return false;
                
                codigo=Utilidades.getString("Código de Entidade: ");
                entidad=cbc.getAutorizados().get(codigo);
                if (entidad==null) System.out.println("Entidade non rexistrada");
                else {
                    System.out.println(entidad.details());
                    choose=Utilidades.choose("Se eliminarán todos os recibos. (C)ontinuar? ", "Cc");
                    if (Character.toLowerCase(choose)=='c') {
                        cbc.getAutorizados().remove(codigo);
                        AplicacionBanca.CONTAS.update(cc);
                    }
                }
                break;
            case 6:
                if (cbc==null) return false;
                
                codigo=Utilidades.getString("Código de Entidade: ");
                entidad=cbc.getAutorizados().get(codigo);
                if (entidad==null) System.out.println("Entidade non rexistrada");
                else {
                    codigo=Utilidades.getString("Código de Domiciliación: ");
                    dom=entidad.getDomiciliaciones().get(codigo);
                    if (dom==null) System.out.println("A domiciliacion non existe");
                    else {
                        entidad.getDomiciliaciones().remove(codigo);
                        AplicacionBanca.CONTAS.update(cc);
                    }
                }
                break;
            case 7:
                if (cbc==null) return false;
                
                codigo=Utilidades.getString("Código de Entidade: ");
                entidad=cbc.getAutorizados().get(codigo);
                if (entidad==null) {
                    nomentidad=Utilidades.getString("Nome Entidade: ");
                    num=Utilidades.getDouble("Máximo Autorizado: ");
                    entidad=new Entidad(codigo,nomentidad,num);
                    cbc.getAutorizados().put(codigo,entidad);
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
                    AplicacionBanca.CONTAS.update(cc);
                }
                break;
            case 8:
                if (cbc==null) return false;
                
                return true;
        }
        return false;
    }
}
