package banca;


import banca.Cliente;
import banca.ClienteRandomAccessFileDataStore;
import banca.ContaBancaria;
import banca.ContaRandomAccessFileDataStore;
import banca.MenuBanca;
import storage.DataStore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author xavi
 */
public class AplicacionBanca {
    public final static String CONTAS_FILENAME="Contas-1.dat";
    public final static String CLIENTES_FILENAME="Clientes-1.dat";
    public final static String AUTORIZADOS_FILENAME="Autorizados-1.dat";
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
