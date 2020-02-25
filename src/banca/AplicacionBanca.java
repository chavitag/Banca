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
    //public static DataStore <String,ContaBancaria> CONTAS=new ContaHashMapDataStore ();
    //public static DataStore <String,Cliente> CLIENTS=new ClienteHashMapDataStore ();
    
    //public static DataStore <String,ContaBancaria> CONTAS=new ContaRandomAccessFileSerializeDataStore();
    //public static DataStore <String,Cliente> CLIENTS=new ClienteRandomAccessFileSerializeDataStore();
    
    public final static DataStore <String,ContaBancaria> CONTAS=new ContaRandomAccessFileDataStore();
    public final static DataStore <String,Cliente> CLIENTS=new ClienteRandomAccessFileDataStore();

    public static void main(String[] args) {
        try {
            new MenuBanca().run();
        } finally {
            CONTAS.closeDataStore();
            CLIENTS.closeDataStore();
        }
    }
}
