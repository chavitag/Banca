package banca;

import banca.data.randomaccessfile.ClienteRandomAccessFileDataStore;
import banca.data.randomaccessfile.ContaRandomAccessFileDataStore;
import storage.DataStore;

/**
 *
 * @author xavi
 */
public class AplicacionBanca {
    public final static String CONTAS_FILENAME="Contas.dat";
    public final static String CLIENTES_FILENAME="Clientes.dat";
    //public static DataStore <String,ContaBancaria> contas=new ContaHashMapDataStore ();
    //public static DataStore <String,Cliente> clients=new ClienteHashMapDataStore ();
    
    //public static DataStore <String,ContaBancaria> contas=new ContaRandomAccessFileSerializeDataStore();
    //public static DataStore <String,Cliente> clients=new ClienteRandomAccessFileSerializeDataStore();
    
    public static DataStore <String,ContaBancaria> contas=new ContaRandomAccessFileDataStore();
    public static DataStore <String,Cliente> clients=new ClienteRandomAccessFileDataStore();
    
    public static void main(String[] args) {
        new MenuBanca().run();
    }
}
