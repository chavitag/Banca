package Utils;

import Utils.Utilidades;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Menu
 * E unha clase abstracta da que se hereda o menú concreto
 * @author Javier Taboada
 * @author xavitag.es
 * @version 1.0
 * @since 1.0
*/
public abstract class Menu {

    /**
     * Opcións do menú
     */
    public ArrayList<String> opciones=new ArrayList <>();
    
    /**
     * Constructor: Menú sin opcións
     */
    public Menu() {  }
    
    /**
     * Constructor: Menú con opcións
     * @param ops - Opcións do menú
     */
    public Menu(String[] ops) {
        setMenu(ops);
    }

    /**
     * Pon opcións a este menú
     * @param ops - Opcións do menú
     */
    public void setMenu(String[] ops) {
        opciones.addAll(Arrays.asList(ops));
    }
    
    /**
     * Visualiza o menú e da a elexir unha das opcións do menú  
     * @return opción elixida
     */
    public int run() {
        int nops;
        int opc=0;
        boolean end=false;
        
        do {
            try {
                nops=showMenu();
                opc=Utilidades.getInt("Elixe Opcion: ",1,nops);
                end=menu(opc);
            } catch (Exception ex) {
                System.out.println("\nERROR: \n"+ex.getMessage());
            }
        } while(!end);
        return opc;
    }
    
    /**
     * Método abstracto a implementar.Realizará as accións correspondentes a cada opción
     * @param opc - Opción a xestionar
     * @return 
     * @throws java.lang.Exception
     */
    public abstract boolean menu(int opc) throws Exception;   
    
    /**
     * Visualiza o menú en pantalla
     * @return numero de opcións que ten o menú
     */
    private int showMenu() {
        int n=1;
        System.out.println("\nM E N U");
        System.out.println("-------");
        for(String s: opciones) {
            System.out.println(n+".- "+s);
            n++;
        }
        System.out.println();
        return n-1;
    }
}
