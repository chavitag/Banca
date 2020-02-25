package Utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Menu
 * Crea un menú. E unha clase abstracta. As clases que hereden de esta
 * deberán implementar o método menu, que se encargará de levara a cabo
 * as distintas opcións. 
 * 
 * Esta clase implementa as tarefas comúns e repetitivas cando deseñamos un menú
 * 
 * @author xavitag.es
 * @version 1.0
 * @since 1.0
*/
public abstract class Menu {
    /**
     * Título por defecto
     */
    private String title="M E N U";
    
    /**
     * Lista de Opcións do menú
     */
    public ArrayList<String> opciones=new ArrayList <>();
    
    /**
     * Constructor con título por defecto
     * @param ops - Opcións do menú
     */
    public Menu(String[] ops) {
        setMenu(ops);
    }
    
    /**
     * Constructor con título
     * @param title - Título do menú
     * @param ops - Opcións do menú
     */
    public Menu(String title,String[] ops) {
        setMenu(ops);
        this.title=title;
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
                end=menu(opc); // Chamamos ao método menu para levar a cabo a opción. Devolverá true para finalizar
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
    
    /**
     * Debuxa unha liña da lonxitude de txt
     */
    private void line(String txt) {
        int len=txt.length();
        while(len>0) {
            System.out.print("-");
            len--;
        }
        System.out.println();
    }
    
    /**
     * Pon as opcións de este menú
     */
    private void setMenu(String[] ops) {
        opciones.addAll(Arrays.asList(ops));
    }
}
