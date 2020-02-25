package storage.randomaccessfile;

import java.io.IOException;
// Cambiando o StreamSerializator, cambia o xeito de almacenar....
import Utils.serializators.objectstream.StreamSerializator;
import storage.Gardable;

/**
 *
 * @author xavi
 * @param <K>
 * @param <T>
 */
public abstract class RandomAccessFileSerializeDataStore <K,T extends Gardable<K,T>> 
            extends RandomAccessFileDataStore <K,T> {
        
    public RandomAccessFileSerializeDataStore(String filename) {
        super(filename);
    }
        
    @Override
    protected void writeObject(T object) throws IOException {
        StreamSerializator <T> serialize=new StreamSerializator<>(object);
        ras.writeUTF(serialize.getString());
    }
    
    @Override
    protected T readObject() throws IOException {
        StreamSerializator <T> serialize = new StreamSerializator<>(ras.readUTF());
        try {
            return serialize.getObject();
        } catch (ClassNotFoundException ex) {
            throw new IOException("Error Class reading Object");
        }
    }
}
