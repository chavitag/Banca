/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import serializator.Serializator;

/**
 *
 * @author xavi
 * @param <K>
 * @param <T>
 */
public abstract class RandomAccessFileDataStore <K,T extends Serializable & Gardable<K>> implements DataStore <K, T>  {
    protected final String filename;
    protected RandomAccessFile ras;
    protected long position;
    
    public RandomAccessFileDataStore(String filename) {
        this.filename=filename;
    }
    
    @Override
    public void save(T object) throws DataStoreException {
        if (load(object.getKey()) != null)  throw new DataStoreException("Error Save Already Exists");
        try {
            open();
            ras.writeUTF(Serializator.serialize(object));
        } catch (IOException ex) {
            throw new DataStoreException(ex.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public void update(T object) throws DataStoreException {
        if (load(object.getKey())==null) throw new DataStoreException("Error Update Not Exists");
        try {
            open();
            ras.seek(position);
            ras.writeUTF(Serializator.serialize(object));
        } catch (IOException ex) {
            throw new DataStoreException(ex.getMessage());
        } finally {
            close();
        }
    }

    @Override
    public abstract T loadBy(By c, Object info);
    
    @Override
    public abstract Collection<T> loadAllBy(By c, Object info);

    @Override
    public T load(K info) {
        String data;
        T object=null;
        
        try {
            open();
            do {
                position=ras.getFilePointer();  // Gardo a posición de lectura
                data=ras.readUTF(); // Si e fin de ficheiro ou non lee un String lanza unha Exception
                object=Serializator.unserialize(data);
            } while(!info.equals(object.getKey()));
        } catch (Exception e) {
            return null;
        } finally {
            try { close(); } catch(Exception e) {};
        }
        return object;
    }

    @Override
    public Collection<T> loadAll() {
        ArrayList <T> list=new ArrayList <>();
        String data;
        T object=null;
        
        try {
            open();
            do {
                data=ras.readUTF(); // Si e fin de ficheiro ou non lee un String lanza unha Exception
                object=Serializator.unserialize(data);
                list.add(object);
            } while(true);
        } catch (EOFException e) {
        } catch (IOException | ClassNotFoundException e) {
            list=null;
        } finally {
            try { close(); } catch(Exception e) {};
        }
        return list;
    }
    
    
    protected void open() throws FileNotFoundException {
        ras=new RandomAccessFile(filename,"rw");
    }
    
    protected void close() throws DataStoreException {
        try {
            if (ras!=null) ras.close();
            ras=null;
        } catch(IOException e) {
            throw new DataStoreException(e.getMessage());
        }
    }
}
