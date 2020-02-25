package banca;

import banca.entities.Entidad;
import banca.entities.ContaBancariaCorrente;
import banca.entities.ContaBancariaAforro;
import banca.entities.ContaBancaria;
import banca.entities.Domiciliacion;
import Utils.Menu;
import Utils.Utilidades;
import banca.entities.Autorizacion;
import java.util.Collection;

/**
 * Menú de Xestión de Contas Correntes.
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
        Collection <Autorizacion> autorizados;
        Autorizacion autorizacion;
        Entidad entidad;
        Domiciliacion dom;
        char choose;
        String codigo;
        String nomentidad;
        String concepto;
        double num;

        switch(opc) {
            case 1: // Información
                System.out.println(cc.details());
                Utilidades.getString("Pulsa Enter para continuar...");
                break;
                
            case 2: // Ingreso
                num=Utilidades.getDouble("Cantidade a Ingresar: ");
                num=cc.ingreso(num);
                AplicacionBanca.CONTAS.update(cc); // Actualiza no DataStore
                System.out.println("O novo saldo é de "+num);
                Utilidades.getString("Pulsa Enter para continuar...");
                break;
                
            case 3: // Reintegro
                num=Utilidades.getDouble("Cantidade a Retirar: ");
                num=cc.reintegro(num);
                AplicacionBanca.CONTAS.update(cc); // Actualiza no DataStore
                System.out.println("O novo saldo é de "+num);
                Utilidades.getString("Pulsa Enter para continuar...");
                break;
                
            case 4: // Ver Domiciliaciones
                if (cbc==null) return true; // E unha conta de aforro: SAIR
                System.out.println("DOMICILIACIÓNS\n----------------");
                autorizados=cbc.getAutorizados().values();
                if (autorizados.isEmpty()) System.out.println("Sen Domiciliacións");
                else                       {
                    for(Autorizacion e: autorizados) {
                        System.out.println(e.details());
                    }
                }
                Utilidades.getString("Pulsa Enter para continuar...");
                break;
                
            case 5: // Eliminiar Domiciliacion
                if (cbc==null) return false; // E unha conta de Aforro.... ignorar
                
                codigo=Utilidades.getString("Código de Entidade: ");
                autorizacion=cbc.getAutorizados().get(codigo);
                if (autorizacion==null) System.out.println("Entidade non rexistrada");
                else {
                    System.out.println(autorizacion.details());
                    choose=Utilidades.choose("Se eliminarán todos os recibos. (C)ontinuar? ", "Cc");
                    if (Character.toLowerCase(choose)=='c') {
                        cbc.getAutorizados().remove(codigo);
                        AplicacionBanca.CONTAS.update(cc); // Actualiza no DataStore
                    }
                }
                break;
                
            case 6: // Eliminar recibo
                if (cbc==null) return false; // E unha conta de aforro... ignorar
                
                codigo=Utilidades.getString("Código de Entidade: ");
                autorizacion=cbc.getAutorizados().get(codigo);
                if (autorizacion==null) System.out.println("Entidade non autorizada");
                else {
                    codigo=Utilidades.getString("Código de Domiciliación: ");
                    dom=autorizacion.getDomiciliaciones().get(codigo);
                    if (dom==null) System.out.println("A domiciliacion "+codigo+" non existe");
                    else {
                        autorizacion.getDomiciliaciones().remove(codigo);
                        AplicacionBanca.CONTAS.update(cc); // Actualiza no DataStore
                    }
                }
                break;
                
            case 7: // Engadir recibo
                if (cbc==null) return false; // E unha conta de aforro, ignorar.
                
                codigo=Utilidades.getString("Código de Entidade: ");
                autorizacion=cbc.getAutorizados().get(codigo);
                if (autorizacion==null) {   // A entidade non está autorizada. A autorizamos.
                    nomentidad=Utilidades.getString("Nome Entidade: ");
                    num=Utilidades.getDouble("Máximo Autorizado: ");
                    entidad=new Entidad(codigo,nomentidad);
                    // Gardamos a entidad no DataStore. En caso de fallo, lanza unha Exception...
                    AplicacionBanca.ENTIDADES.save(entidad); 
                    autorizacion=new Autorizacion(entidad,num);
                    cbc.getAutorizados().put(codigo,autorizacion);
                } else {
                    System.out.println("Engadindo Domiciliación de  "+autorizacion.details());
                }
                codigo=Utilidades.getString("Codigo Domiciliacion :");
                concepto=Utilidades.getString("Concepto: ");
                if (autorizacion.getDomiciliaciones().get(codigo)!=null) {
                    System.out.println("O recibo xa estaba domiciliado");
                } else {
                    dom=new Domiciliacion(codigo,concepto);
                    autorizacion.getDomiciliaciones().put(codigo,dom);
                    AplicacionBanca.CONTAS.update(cc); // Actualizamos no DataStore
                }
                break;
            case 8:
                if (cbc==null) return false; // E unha conta de aforro. Ignorar.
                return true;
        }
        return false;
    }
}
