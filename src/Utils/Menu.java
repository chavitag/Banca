package Utils;

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
    private String title="M E N U";
    
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
    
    public Menu(String title,String[] ops) {
        setMenu(ops);
        this.title=title;
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
     * @return true indica que o menú debe finalizar, false se continúa no menú.
     * @throws java.lang.Exception
     */
    public abstract boolean menu(int opc) throws Exception;   
    
    /**
     * Visualiza o menú en pantalla
     * @return numero de opcións que ten o menú
     */
    private int showMenu() {
        int n=1;
        
        Utilidades.clearConsole();
        System.out.println(title);
        line(title);
        for(String s: opciones) {
            System.out.println(n+".- "+s);
            n++;
        }
        System.out.println();
        return n-1;
    }
    
    private void line(String txt) {
        int len=txt.length();
        while(len>0) {
            System.out.print("-");
            len--;
        }
        System.out.println();
    }
}
