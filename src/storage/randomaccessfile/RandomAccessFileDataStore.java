package storage.randomaccessfile;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import storage.By;
import storage.DataStore;
import storage.DataStoreException;
import storage.Gardable;

public abstract class RandomAccessFileDataStore <K,T extends Gardable<K,T>> implements DataStore <K, T>  {    
    protected final String filename;
    protected RandomAccessFile ras;
    protected long position;
    
    public RandomAccessFileDataStore(String filename) {
        this.filename=filename;
    }
    
    @Override
    public void save(T object) throws DataStoreException {
        if (load(object.getKey()) != null) throw new DataStoreException("Error Save Already Exists");
        write(object);
    }

    @Override
    public void update(T object) throws DataStoreException {
        if (load(object.getKey())==null) throw new DataStoreException("Error Update Not Exists");
        write(object);
    }
    
    @Override
    public T load(K info) {
        String data;
        T object=null;
        
        try {
            open();
            do {
                position=ras.getFilePointer();  // Gardo a posición de lectura
                object=readObject();
            } while(!info.equals(object.getKey()));
        } catch (IOException e) {
            return null;
        } finally {
            close();
        }
        return object;
    }

    @Override
    public Collection<T> loadAll() {
        ArrayList <T> list=new ArrayList <>();
        String data;
        T object;

        try {
            open();
            do {
                object=readObject();
                list.add(object);
            } while(true);
        } catch (EOFException e) {
        } catch (IOException ex) {
            System.out.println("ERROR: "+ex.getMessage());
            list=null;
        } finally {
            close();
        }
        return list;
    }
        
    protected void open() throws FileNotFoundException {
        if (ras==null) {
            ras=new RandomAccessFile(filename,"rw");
        }
    }
    
    protected void close() {
        try {
            if (ras!=null) ras.close();
            ras=null;
        } catch(IOException e) {
            System.out.println("Warning: Close Failed."+e.getMessage());
        }
    }
    
    private void write(T object) throws DataStoreException {
      try {
            open();
            ras.seek(position);
            writeObject(object);        // Non confundir con writeObject de Serializable, non ten que ver
        } catch (IOException ex) {
            throw new DataStoreException(ex.getMessage());
        } finally {
            close();
        }  
    }
    
    @Override
    public T loadBy(By c, Object info) {
        T data;
       
        try {
            open();
            do {
               data=readObject();
               if (filter(c,info,data)) return data;
            } while(true);
        } catch (IOException e) { 
        } finally {
            close();
        }
        return null;
    }
    
    @Override
    public Collection<T> loadAllBy(By c, Object info)  {
        ArrayList <T> result=new ArrayList <>();
        T data;
        
        try {
            open();
            do {
               data=readObject();
               if (filter(c,info,data)) result.add(data);
            } while(true);
        } catch (EOFException e) {
        } catch (IOException e) {
            result=null;
        } finally {
            close();
        }
        return result;
    }
       
    @Override
    public void closeDataStore() {}
    
    protected abstract boolean filter(By c,Object info,T data);
    protected abstract void writeObject(T object) throws IOException;
    protected abstract T readObject() throws IOException;
}
