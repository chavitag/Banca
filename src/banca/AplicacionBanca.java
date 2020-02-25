package banca;

import banca.entities.Cliente;
import banca.entities.ContaBancaria;
import banca.entities.Entidad;
import storage.DataStore;
import banca.data.hashmap.ClienteHashMapDataStore;
import banca.data.hashmap.ContaHashMapDataStore;
import banca.data.hashmap.EntidadHashMapDataStore;

/**
 * Aplicación Banca con almacenamento en HashMap.
 * FUNCIONAMENTO:
 *  Almacenamos a información en dous DataStore implementados mediante HashMap, un para Clientes
 *  outro para Contas Bancarias e outro para as Entidades.
 * 
 *  Para non duplicar a información dos clientes (que está en cada ContaBancaria), se almacenará so
 *  o DNI coa conta bancaria, almacenando os datos do Cliente so no ficheiro de Clientes. 
 *  Deste xeito evitamos posibles incongruencias como que a modificación dos datos dun Cliente 
 *  so no ficheiro de Clientes, deixa os datos vellos coa información da Conta Bancaria.
 * 
 *  Coas Autorizacións e as entidades se traballará de xeito similar.
 * 
 *  Como a información se almacena únicamente en HashMaps é volátil e se perde de unha execución
 *  da aplicación a outra. 
 * 
 *  Si nos fixamos na forma de traballar do HashMapDataStore, debemos ter en conta que se devolven os
 *  obxectos almacenados no HashMap, NON UNHA COPIA.
 * 
 */
public class AplicacionBanca {
    // DataStores para Clientes e Contas Bancarias. 
    public static DataStore <String,ContaBancaria> CONTAS=new ContaHashMapDataStore ();
    public static DataStore <String,Cliente> CLIENTS=new ClienteHashMapDataStore ();
    public static DataStore <String,Entidad> ENTIDADES=new EntidadHashMapDataStore ();
    
    /**
     * Programa Principal
     * @param args 
     */
    public static void main(String[] args) {
        /* Co try ... finally nos aseguramos de que pase o que pase se chama
         * aos métodos closeDataStore dos DataStore utilizados
         */
        try {
            new MenuBanca().run();
        } finally {
            CONTAS.closeDataStore();
            CLIENTS.closeDataStore();
        }
    }
}
