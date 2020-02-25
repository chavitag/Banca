package Utils.serializators.objectstream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author xavi
 * @param <T>
 */
public class Serializator <T extends Serializable> extends Utils.Serializator <T> {

    public Serializator(T object) {
        super(object);
    }
    
    public Serializator(byte[] bobject) {
        super(bobject);
    }
    
    public Serializator(String strobject) {
        super(strobject);
    }
    
    /**
     *
     * @return
     * @throws java.lang.ClassNotFoundException
     */
    @Override
    protected Object doUnserialize() throws ClassNotFoundException {
        Object o;
        
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bobject));
            // Como o Object é Serializable, se pode ler e reconstruír
            o=ois.readObject();
            ois.close();
        } catch(IOException e) {
            throw new IllegalArgumentException();
        }
        return o;
   }

    @Override
    public byte[] doSerialize() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            // Como o obxecto é Serializable, pode escribirse
            oos.writeObject(object);
            oos.close();
        } catch(IOException e) {
            throw new IllegalArgumentException();
        }
        return baos.toByteArray();
    }
}
