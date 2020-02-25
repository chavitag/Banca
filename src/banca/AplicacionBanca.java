package banca;

import storage.DataStore;

/**
 * Aplicación Banca con almacenamento en RandomAccessFile sinxelo.
 *  Recuperamos a información cando creamos os obxectos DataStore e a gardamos cando os "Pechamos"
 *  eliminando os datos anteriores.
 * 
 *  Para non duplicar a información dos clientes (que está en cada ContaBancaria), se almacenará so
 *  o DNI coa conta bancaria, almacenando os datos do Cliente so no ficheiro de Clientes. 
 *  Deste xeito evitamos posibles incongruencias como que a modificación dos datos dun Cliente 
 *  so no ficheiro de Clientes, deixa os datos vellos coa información da Conta Bancaria.
 * 
 *  Para poder gardar a información no RandomAccessFile, podemos tomar dúas aproximacións:
 *  a)  "Serializar" os obxectos que queremos gardar. A "Serialización" consiste en transformar os obxectos en bytes
 *  de xeito que se poda reconstruir o obxecto con posterioridade a partir dos mesmos.
 * 
 *  b)  Gardar os atributos dos obxectos un a un, e logo ao recuperalos reconstruír o obxecto. Isto é posible 
 *  gracias a que RandomAccessFile ten métodos apropiados para gardar todos os tipos primitivos, e String.
 * 
 *  Construiremos dúas versións de DataStore con serialización (a) e sen serialización. (b)
 * 
 *  SEN SERIALIZACIÓN
 *  Para almacenar sen serialización, debemos gardar e recuperar os atributos de cada obxecto un a un
 *  para reconstruílos con posterioridade utilizando os métodos de RandomAccessFile.
 * 
 * 
 *  CON SERIALIZACION
 *  Para serializar podemos utilizar a interface Serializable en Cliente e en ContaBancaria. 
 *  Un obxecto que implemente a interfaz Serializable dispoñerá dos métodos 
 *  writeObject, readObject e readObjectNoData. Estes métodos escriben e leen 
 *  respectivamente os bytes do obxecto a un OutputStream (que aínda non estudiamos), o que podemos aproveitar
 *  para gardar os bytes nun array. Para que un obxecto sexa "Serializable" bastará indicar que a clase 
 *  "implements Serializable" xa que estes 3 métodos teñen implementacións por defecto. So será necesario 
 *  sobrepoñelos si queremos un comportamento "especial" (ver API).
 * 
 *  Outra posibilidade é serializar os obxectos "a man", transformando todos os seus atributos a un array 
 *  de bytes e viceversa. Esto podemos facelo cunha clase especial para facer este traballo, ou facendo que 
 *  cada clase teña un método para serializar e deserializar, que podemos incorporar a interface Gardable.
 * 
 *  Faremos que Gardable extenda Serializable, de xeito que tanto Cliente como ContaCorrente sexan Gardable e 
 *  Serializable. Incorporaremos tamén a interface Gardable os métodos para serializar e deserializar os 
 *  obxectos da clase. (Serializable so proporciona métodos para ler e escribir os obxectos a un Stream).
 * 
 * Cambios necesarios no código:
 *      - O DataStore debe ter unha operación de "Peche", que nos aproveitamos para gardar os datos
 *      - A Aplicación debe "Pechar" o DataStore na súa finalización.
 *      - Modificaremos ContaBancaria para que so garde o DNI do cliente, non o obxecto cliente completo.
 *      - Gardable extenderá Serializable
 *      - Incorporamos a Gardable os métodos serialize e unserialize. Estes métodos so se utilizarán na
 *      versión CON serialización.
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
    
    // DataStores para Clientes e Contas Bancarias. 
    public static DataStore <String,ContaBancaria> CONTAS=new ContaHashMapDataStore ();
    public static DataStore <String,Cliente> CLIENTS=new ClienteHashMapDataStore ();
    
    public static void main(String[] args) {
        try {
            new MenuBanca().run();
        } finally {
            // Pechado. Podemos aproveitar para gardar os datos.
            CONTAS.closeDataStore();
            CLIENTS.closeDataStore();
        }
    }
}
