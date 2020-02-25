package banca;

import banca.data.randomaccessfile.ClienteRandomAccessFileDataStore;
import banca.data.randomaccessfile.ContaRandomAccessFileDataStore;
import storage.DataStore;

/**
 * Aplicación Banca con almacenamento en RandomAccessFile sinxelo.
 *  Recuperamos a información cando creamos os obxectos DataStore e a gardamos cando os "Pechamos"
 *  eliminando os datos anteriores.
 *  Para non duplicar a información dos clientes (que está en cada ContaBancaria), se almacenará so
 *  no ficheiro de Clientes, almacenando coa conta bancaria so o DNI do cliente.
 * 
 *  Para poder gardar a información no RandomAccessFile, podemos tomar dúas aproximacións:
 *  a)  "Serializar" os obxectos que queremos gardar. A "Serialización" consiste en transformar os obxectos en bytes
 *  de xeito que se poda reconstruir o obxecto con posterioridade a partir dos mesmos.
 *  b)  Gardar os atributos dos obxectos un a un, e logo ao recuperalos reconstruír o obxecto. Isto é posible 
 *  gracias a que RandomAccessFile ten métodos apropiados para gardar todos os tipos primitivos, e String.
 * 
 *  Construiremos dúas versións de DataStore con e sen serialización. 
 * 
 *  Para serializar podemos utilizar a interface Serializable. Un obxecto que implemente a interfaz serializable
 *  dispoñerá dos métodos writeObject, readObject e readObjectNoData. Estes métodos escriben e leen 
 *  respectivamente os bytes do obxecto a un OutputStream (que aínda non estudiamos), o que podemos aproveitar
 *  para gardar os bytes nun array. Para que un obxecto sexa "Serializable" bastará indicar que a clase 
 *  "implements Serializable" xa que estes 3 métodos teñen implementacións por defecto. So será necesario 
 *  sobrepoñelos si queremos un comportamento "especial" (ver API).
 * 
 *  Outra posibilidade é serializar os obxectos "a man", transformando todos os seus atributos a un array 
 *  de bytes e viceversa. Esto podemos facelo cunha clase especial para facer este traballo, ou facendo que 
 *  cada clase teña un método para serializar e deserializar, que podemos incorporar a interface Gardable.
 * 
 *  Para almacenar sen serialización, debemos gardar e recuperar os atributos de cada obxecto un a un
 *  para reconstruílos con posterioridade utilizando os métodos de RandomAccessFile.
 * 
 * Cambios necesarios no código:
 *      - O DataStore debe ter unha operación de "Peche", que nos aproveitamos para gardar os datos
 *      - A Aplicación debe "Pechar" o DataStore na súa finalización.
 *      - Modificaremos ContaBancaria para que so garde o DNI do cliente, non o obxecto cliente completo.
 *
 * Vantaxes:
 *      - Sencillez
 * 
 * Inconvintes:
 *      - Si os datos son moitos, a carga e o gardado dos datos pode resultar lento
 *      - Si a aplicación falla antes de "Pechar" (porque se vai a luz, por exemplo) os
 *      datos se perden.
 * 
 * @author xavi
 */
public class AplicacionBanca {
    public final static String CONTAS_FILENAME="Contas.dat";
    public final static String CLIENTES_FILENAME="Clientes.dat";
    
    public static DataStore <String,ContaBancaria> CONTAS=new ContaHashMapDataStore ();
    public static DataStore <String,Cliente> CLIENTS=new ClienteHashMapDataStore ();
    
    public static void main(String[] args) {
        try {
            new MenuBanca().run();
        } finally {
            CONTAS.closeDataStore();
            CLIENTS.closeDataStore();
        }
    }
}
