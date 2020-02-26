package banca;

import banca.entities.Cliente;
import banca.entities.ContaBancaria;
import banca.entities.Entidad;
import storage.DataStore;
import banca.data.hashmap.randomaccessfile.ClienteRandomAccessFileHashMapDataStore;
import banca.data.hashmap.randomaccessfile.ContaRandomAccessFileHashMapDataStore;
import banca.data.hashmap.randomaccessfile.EntidadRandomAccessFileHashMapDataStore;
import storage.DataStoreException;

/**
 * Aplicación Banca con almacenamento dos HashMap en RandomAccessFile ao inicio e ao final da aplicacion.
 * Aproximación sen serialización nin modificar Cliente nin Entidade nin as ContaBancaria....
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
 *      - Simpleza: Non nos temos que preocupar do tamaño dos rexistros, xa que cada vez
 *      que saímos se gardan todos os datos de 0
 * 
 * INCONVINTES:
 *      - O número de contas manexables dependerá da RAM do sistema, xa que todo se garda en memoria
 *      - Si a aplicación non remata (porque se finaliza sin elexir "Saír") os datos se perden
 *      - Si temos moitos datos, tarda en cargar ao inicio e en gardar ao final.
 *      - Poden darse inconsistencias si falla a escritura de algún HashMap.
 * 
 * PROBLEMATICA:
 *  Necesitamos poder gardar os obxectos almacenados nos HasMap nos ficheiros. podemos tomar dúas 
 *  aproximacións:
 * 
 *  a)  "Serializar" os obxectos que queremos gardar. A "Serialización" consiste en transformar os obxectos en bytes
 *  de xeito que se poda reconstruir o obxecto con posterioridade a partir dos mesmos.
 * 
 *  b)  Gardar os atributos dos obxectos un a un, e logo ao recuperalos reconstruír o obxecto. Isto é posible 
 *  gracias a que RandomAccessFile ten métodos apropiados para gardar todos os tipos primitivos, e String.
 * 
 * Esta implementación utilizará os métodos de RandomAccessFile para gardar os atributos dos
 * obxectos e reconstruílos, pero se deseñará de modo que sexa moi simple cambiar e facelo mediante
 * serialización.
 * 
 * MODIFICACIONS NECESARIAS:
 * Non convén chamar a métodos da clase que se podan sobrepoñer (non finais) dende un constructor,
 * xa que potencialmente poden producirse problemas difíciles de atopar e solucionar. Isto se debe
 * a que si chamamos a un método sobreposto en unha subclase, o método se executará ANTES de ter 
 * construído o obxecto da subclase, e estaríamos executando un método dun obxecto sin construír.
 * 
 * Para solucionar ese problema, incorporamos a DataStore o método openDataStore, que nos dará a 
 * oportunidade de realizar tarefas de inicialización delegadas ás subclases, como pode ser a
 * carga de datos dende un ficheiro. E a mesma misión que ten realmente closeDataStore...
 * 
 * NINGUNHA destas modificacións afecta a funcionalidade da aplicación con outros DataStore
 * 
 * Respecto á propia carga dos obxectos podemos tomar dúas aproximacións: 
 * 
 *  1.- Os propios obxectos se encargarán de gardar e recuperar do RandomAccessFile os valores dos 
 *  seus atributos. Esto faría máis simple a implementación, pero exixe engadir a Gardable os métodos
 * writeObject(RandomAccessFile ras); e readObject(RandomAccessFile ras);  que leeran e escribirán un
 * obxecto respectivamente no RandomAccessFile a partir da posición actual. Probablemente sexa a 
 * mellor aproximación e require engadir eses métodos a Entidad, Cliente, ContaBancariaCorrenteEmpresa, 
 * ContaBancariaCorrentePersoal e ContaBancariaAforro pero permite escribir unha clase xenérica que se 
 * ocupe de gardar e recuperar calqueira HashMap.
 * 
 * 2.- Definir estes métodos no DataStore correspondente. A vantaxe de esta aproximación é que non precisa
 * modificación da aplicación, pero require crar un obxecto DataStore específico para cada clase.
 * 
 * 
 * ESTA IMPLEMENTACIÓN UTILIZA A APROXIMACIÓN 2
 * 
 *  --> Clases Implementadas
 *          banca.data.ObjectType
 *              --> Enum cos distintos tipos de Obxectos, para poder saber o que se está a ler
 *          storage.hashmap.RandomAccessFileHashMapDataStore
 *              --> HashMapDataStore con persistencia en RandomAccessFile
 *          banca.data.hashmap.randomaccessfile.ClienteRandomAccessFileHashMapDataStore
 *              --> HashMapDataStore con persistencia para Clientes
 *          banca.data.hashmap.randomaccessfile.ContaRandomAccessFileHashMapDataStore
 *              --> HashMapDataStore con persistencia para Contas
 *          banca.data.hashmap.randomaccessfile.EntidadRandomAccessFileHashMapDataStore
 *              --> HashMapDataStore con persistencia para Entidades
 * 
 *  --> Modificacións
 *      En AplicacionBanca 
 *          creación das constantes F_CLIENTES, F_CONTAS, F_ENTIDADES
 *          instanciación dos RandomAccessFileHashMapDataStore en lugar dos DataStore
 */
public class AplicacionBanca {
    // Nomes dos ficheiros. Necesarios si utilizamos almacenamento persistente en disco
    public static final String F_CLIENTES="clientes.dat";
    public static final String F_CONTAS="contas.dat";
    public static final String F_ENTIDADES="entidades.dat";
    
    // DataStores para Clientes e Contas Bancarias.
    //
    // Simplemente cambiando o tipo de DataStore cambia o xeito de almacenar os datos.
    public static DataStore <String,ContaBancaria> CONTAS=new ContaRandomAccessFileHashMapDataStore ();
    public static DataStore <String,Cliente> CLIENTS=new ClienteRandomAccessFileHashMapDataStore ();
    public static DataStore <String,Entidad> ENTIDADES=new EntidadRandomAccessFileHashMapDataStore ();
    
    /**
     * Programa Principal
     */
    public static void main(String[] args) {
        try {
            // Teñen que abrirse neste orden, xa que para crear as contas deben estar
            // cargados os HashMap de Clientes e Entidades
            CLIENTS.openDataStore();
            ENTIDADES.openDataStore();
            CONTAS.openDataStore();

           /* Co try ... finally nos aseguramos de que pase o que pase se chama
            * aos métodos closeDataStore dos DataStore utilizados
            */
            try {
                new MenuBanca().run();
            } finally {
                // Convén pechar neste orden, xa que nos aseguramos que quedan
                // gardados os HashMap de Clientes e Entidades que usan as contas
                CLIENTS.closeDataStore();
                ENTIDADES.closeDataStore();
                CONTAS.closeDataStore();                
            }
        }   catch (DataStoreException ex) {
            System.out.println("ERROR: "+ex.getMessage());
        }
    }
}
