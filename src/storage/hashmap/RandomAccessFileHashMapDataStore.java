package storage.hashmap;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import storage.DataStoreException;
import storage.Gardable;

/**
 * RandomAccessFileHashMapDataStore é un HashMapDataStore con persistencia en RandomAccessFile.
 * Esta clase é abstracta, porque o modo de ler/escribir os Obxectos cambia segundo a Clase do obxecto
 * xa que usamos os métodos de RandomAccessFile para ler e gardar os atributos un a un.
 * 
 * @param <K> - Clase das chaves dos obxectos a ser gardados
 * @param <T> - Clase dos obxectos a ser gardados
 */
public abstract class RandomAccessFileHashMapDataStore <K,T extends Gardable<K>> extends HashMapDataStore <K,T> {
    protected final String filename;    // Nome do ficheiro
    protected RandomAccessFile ras;     // RandomAccessFile

    /**
     * Constructor. 
     * @param filename - Nome do ficheiro
     */
    RandomAccessFileHashMapDataStore(String filename)  {
        // Non é necesario chamar a super(), porquee HashMapDataStore ten constructor por defecto
        this.filename=filename;
    }
    
    /**
     * "openDataStore", aproveitamos para cargar os datos do ficheiro no HashMap.
     * @throws DataStoreException 
     */
    public void openDataStore() throws DataStoreException {
        T object;
                
        open();
        try {
            do {
                object=readObject();
                lista.put(object.getKey(),object);
            } while(true);
        } catch(EOFException e) {               
        } catch(IOException e) { 
            throw new DataStoreException(e.getMessage());
        } finally {
            close();
        }
    }

    /** 
     * "Pecha" o DataStore, aproveitamos para gardar os datos do HashMap.
     * @throws DataStoreException 
     */
    @Override
    public void closeDataStore() throws DataStoreException {
        Collection <T> data=lista.values();
        try {
            open();
            ras.setLength(0); // Borramos o contido.
            for(T obx: data) {
                writeObject(obx);
            }
        } catch(IOException e) {
            throw new DataStoreException("Warning: Close Failed."+e.getMessage());
        } finally {
            close();
        }
    }
   
    /**
     * Abre o RandomAccessFile en modo lectura/escritura.
     * @throws DataStoreException 
     */
    private void open() throws DataStoreException {
        try {
            if (ras==null) {
                ras=new RandomAccessFile(filename,"rw");
            }
        } catch(FileNotFoundException e) {
            throw new DataStoreException("Ficheiro non Atopado");
        }
    }
    
    /**
     * Pecha o RandomAccessFile
     * @throws DataStoreException 
     */
    private void close() throws DataStoreException {
        try {
            if (ras!=null) ras.close();
            ras=null;
        } catch(IOException e) {
            throw new DataStoreException(e.getMessage());
        }
    }
        
    protected abstract T readObject() throws IOException;
    protected abstract void writeObject(T object) throws IOException;
    
}
