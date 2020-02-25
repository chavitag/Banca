package banca;

import banca.entities.Cliente;
import banca.entities.ContaBancaria;
import banca.entities.Entidad;
import storage.DataStore;
import banca.data.hashmap.ClienteHashMapDataStore;
import banca.data.hashmap.ContaHashMapDataStore;
import banca.data.hashmap.EntidadHashMapDataStore;
import java.util.logging.Level;
import java.util.logging.Logger;
import storage.DataStoreException;

/**
 * Aplicación Banca con almacenamento dos HashMap en RandomAccessFile ao inicio e ao final da aplicacion.
 * Aproximación sen modificar Cliente nin Entidade nin as ContaBancaria....
 * 
 * FUNCIONAMENTO:
 * 
 *  Partimos da implementación con HashMap.
 * 
 *  Almacenamos a información en dous DataStore implementados mediante HashMap, un para Clientes
 *  outro para Contas Bancarias e outro para as Entidades.
 * 
 *  Cando se crean os DataStore se cargará a información existente nos ficheiros F_CLIENTE, F_CONTAS e 
 *  F_ENTIDADES nos HashMap.
 * 
 *  Cando o programa remate, gardaremos o contido dos HashMap nos ficheiros F_CLIENT, F_CONTAS e 
 *  F_ENTIDADES
 * 
 *  Deste xeito a información xa non será volátil.
 *  
 * VENTAXAS:
 *      - Simpleza
 * INCONVINTES:
 *      - Si a aplicación non remata (porque se finaliza sin elexir "Saír") os datos se perden
 *      - Si temos moitos datos, tarda en cargar ao inicio e en gardar ao final.
 *      - Poden darse inconsistencias si falla a escritura de algún HashMap.
 * 
 * PROBLEMATICA:
 *  Necesitamos poder gardar os obxectos almacenados nos HasMap nos ficheiros. podemos tomar dúas 
 *  aproximacións:
 *  a)  "Serializar" os obxectos que queremos gardar. A "Serialización" consiste en transformar os obxectos en bytes
 *  de xeito que se poda reconstruir o obxecto con posterioridade a partir dos mesmos.
 * 
 *  b)  Gardar os atributos dos obxectos un a un, e logo ao recuperalos reconstruír o obxecto. Isto é posible 
 *  gracias a que RandomAccessFile ten métodos apropiados para gardar todos os tipos primitivos, e String.
 * 
 * 
 * Esta implementación utilizará os métodos de RandomAccessFile para gardar os atributos dos
 * obxectos e reconstruílos, pero se deseñará de modo que sexa moi simple cambiar e facelo mediante
 * serialización.
 * 
 * MODIFICACIONS NECESARIAS:
 *  Non convén chamar a métodos da clase que se podan sobrepoñer (non finais) dende un constructor,
 *  xa que potencialmente poden producirse problemas difíciles de atopar e solucionar. Isto se debe
 *  a que si chamamos a un método sobreposto en unha subclase, o método se executará ANTES de ter 
 *  construído o obxecto da subclase, e estaríamos executando un método dun obxecto sin construír.
 * 
 * Para solucionar ese problema, incorporamos a DataStore o método openDataStore, que nos dará a 
 * oportunidade de realizar tarefas de inicialización delegadas ás subclases, como pode ser a
 * carga de datos dende un ficheiro. E a mesma misión que ten realmente closeDataStore...
 * 
 * Respecto á propia carga dos obxectos podemos tomar dúas aproximacións: 
 *  1.- Os propios obxectos se encargarán de gardar e recuperar do RandomAccessFile os valores dos 
 *  seus atributos. Esto faría máis simple a implementación, pero exixe engadir a Gardable os métodos
 * writeObject(RandomAccessFile ras); e readObject(RandomAccessFile ras);  que leeran e escribirán un
 * obxecto respectivamente no RandomAccessFile a partir da posición actual. Probablemente sexa a 
 * mellor aproximación, pero require engadir eses métodos a Entidad, Cliente, ContaBancariaCorrenteEmpresa, 
 * ContaBancariaCorrentePersoal e ContaBancariaAforro.
 * 
 * 2.- Definir estes métodos no DataStore correspondente. A vantaxe de esta aproximación é que non precisa
 * modificación da aplicación.
 * 
 */
public class AplicacionBanca {
    public static final String F_CLIENTES="clientes.dat";
    public static final String F_CONTAS="contas.dat";
    public static final String F_ENTIDADES="entidades.dat";
    
    // DataStores para Clientes e Contas Bancarias. 
    public static DataStore <String,ContaBancaria> CONTAS=new ContaHashMapDataStore ();
    public static DataStore <String,Cliente> CLIENTS=new ClienteHashMapDataStore ();
    public static DataStore <String,Entidad> ENTIDADES=new EntidadHashMapDataStore ();
    
    /**
     * Programa Principal
     * @param args 
     */
    public static void main(String[] args) {
        try {
            CONTAS.openDataStore();
            CLIENTS.openDataStore();
            ENTIDADES.openDataStore();
           /* Co try ... finally nos aseguramos de que pase o que pase se chama
            * aos métodos closeDataStore dos DataStore utilizados
            */
            try {
                new MenuBanca().run();
            } finally {
                CONTAS.closeDataStore();
                CLIENTS.closeDataStore();
                ENTIDADES.closeDataStore();
            }
        }   catch (DataStoreException ex) {
            System.out.println("ERROR: "+ex.getMessage());
        }
    }
}
