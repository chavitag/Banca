package storage;

/**
 * Erros relacionados co DataStore
 */
public class DataStoreException extends Exception {
    public DataStoreException() {}
    
    public DataStoreException(String msg) {
        super(msg);
    }
}
