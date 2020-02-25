package banca.data.hashmap;

import banca.entities.Entidad;
import java.util.Collection;
import storage.By;
import storage.hashmap.HashMapDataStore;

/**
 *
 * @author xavi
 */
public class EntidadHashMapDataStore extends HashMapDataStore <String,Entidad> {
    
    @Override
    public Entidad loadBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Entidad> loadAllBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
