package banca;

import storage.DataStore;

/**
 * Aplicación Banca con almacenamento en HashMap.
 * FUNCIONAMENTO:
 *  Almacenamos a información en dous DataStore implementados mediante HashMap, un para Clientes
 *  e outro para Contas Bancarias.
 * 
 *  Para non duplicar a información dos clientes (que está en cada ContaBancaria), se almacenará so
 *  o DNI coa conta bancaria, almacenando os datos do Cliente so no ficheiro de Clientes. 
 *  Deste xeito evitamos posibles incongruencias como que a modificación dos datos dun Cliente 
 *  so no ficheiro de Clientes, deixa os datos vellos coa información da Conta Bancaria.
 * 
 */
public class AplicacionBanca {
    // Nomes para os ficheiros
    public final static String CONTAS_FILENAME="Contas.dat";
    public final static String CLIENTES_FILENAME="Clientes.dat";
    
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
