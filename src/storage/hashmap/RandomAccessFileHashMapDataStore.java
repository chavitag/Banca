package storage.hashmap;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import storage.DataStore;
import storage.Gardable;

/**
 *
 * @author xavi
 * @param <K>
 * @param <T>
 */
public abstract class RandomAccessFileHashMapDataStore <K,T extends Gardable<K,T>>
        extends HashMapDataStore <K,T> 
        implements DataStore <K, T> {
    
    protected final String filename;
    protected RandomAccessFile ras;

    RandomAccessFileHashMapDataStore(String filename) {
        this.filename=filename;

    }
    
    protected void open() throws FileNotFoundException,IOException {
        T object;
        if (ras==null) {
            ras=new RandomAccessFile(filename,"rw");
            try {
                do {
                    object=readObject();
                } while(true);
            } catch(EOFException e) {               
            }
        }
    }
    
    @Override
    public void closeDataStore() {
        Collection <T> data=lista.values();
        try {
            open();
            ras.setLength(0);
            for(T obx: data) {
                writeObject(obx);
            }
            if (ras!=null) ras.close();
            ras=null;
        } catch(IOException e) {
            System.out.println("Warning: Close Failed."+e.getMessage());
        }
    }
    
    protected abstract T readObject() throws IOException;
    protected abstract void writeObject(T object) throws IOException;
}
