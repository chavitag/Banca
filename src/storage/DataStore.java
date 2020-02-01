package storage;

import java.util.Collection;

/**
 *
 * @author xavi
 * @param <K>
 * @param <T>
 */
public interface DataStore <K,T extends Gardable> {
        public void save(T object) throws DataStoreException;
        public void update(T object) throws DataStoreException;
        public T loadBy(By c,Object info); 
        public T load(K info);
        public Collection <T> loadAllBy(By c,Object info);
        public Collection <T> loadAll();
}
