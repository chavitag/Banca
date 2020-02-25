package storage.randomaccessfile;

import java.io.IOException;
import java.io.Serializable;
// Cambiando o Serializator, cambia o xeito de almacenar....
import Utils.serializators.objectstream.Serializator;
import java.util.logging.Level;
import java.util.logging.Logger;
import storage.Gardable;

/**
 *
 * @author xavi
 * @param <K>
 * @param <T>
 */
public abstract class RandomAccessFileSerializeDataStore <K,T extends Serializable & Gardable<K,T>> 
            extends RandomAccessFileDataStore <K,T> {
        
    public RandomAccessFileSerializeDataStore(String filename) {
        super(filename);
    }
        
    @Override
    protected void writeObject(T object) throws IOException {
        Serializator <T> serialize=new Serializator<>(object);
        ras.writeUTF(serialize.getString());
    }
    
    @Override
    protected T readObject() throws IOException {
        Serializator <T> serialize = new Serializator<>(ras.readUTF());
        try {
            return serialize.getObject();
        } catch (ClassNotFoundException ex) {
            throw new IOException("Error Class reading Object");
        }
    }
}
