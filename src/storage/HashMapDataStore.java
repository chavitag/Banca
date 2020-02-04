/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author xavi
 * @param <K>
 * @param <T>
 */
public abstract class HashMapDataStore <K,T extends Gardable<K>> implements DataStore <K, T> {
    protected final HashMap <K,T> lista=new HashMap <>();

    @Override
    public void save(T object) throws DataStoreException {
        if (lista.get(object.getKey())!=null) throw new DataStoreException("Error Save Already Exists");
        lista.put(object.getKey(), object);
    }
    
    @Override
    public void update(T object) throws DataStoreException {
        if (lista.get(object.getKey())==null) throw new DataStoreException("Error Update Not Exists");
        lista.put(object.getKey(), object);
    }

    @Override
    public abstract T loadBy(By c, Object info);

    @Override
    public abstract Collection<T> loadAllBy(By c, Object info);

    @Override
    public Collection<T> loadAll() {
        return lista.values();
    }
    
    @Override
    public T load(K info) {
        return lista.get(info);
    }
}
