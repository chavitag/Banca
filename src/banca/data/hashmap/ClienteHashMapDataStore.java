package banca.data.hashmap;

import banca.Cliente;
import java.util.Collection;
import storage.By;
import storage.hashmap.HashMapDataStore;

/**
 *
 * @author xavi
 */
public class ClienteHashMapDataStore extends HashMapDataStore <String,Cliente> {
    @Override
    public Cliente loadBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Cliente> loadAllBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
