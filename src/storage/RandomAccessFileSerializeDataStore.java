package storage;

import java.io.IOException;
import java.io.Serializable;
import serializator.Serializator;

/**
 *
 * @author xavi
 * @param <K>
 * @param <T>
 */
public abstract class RandomAccessFileSerializeDataStore <K,T extends Serializable & Gardable<K>> 
            extends RandomAccessFileDataStore <K,T> 
            implements DataStore <K, T>  {
    
    public RandomAccessFileSerializeDataStore(String filename) {
        super(filename);
    }
        
    @Override
    protected void writeObject(T object) throws IOException {
        ras.writeUTF(Serializator.serialize(object));
    }
    
    @Override
    protected T readObject() throws IOException {
        String data=ras.readUTF(); // Si e fin de ficheiro ou non lee un String lanza unha Exception
        try {
            return Serializator.unserialize(data);
        } catch (ClassNotFoundException ex) {
            throw new IOException("Unknown class reading data");
        }
    }
}
