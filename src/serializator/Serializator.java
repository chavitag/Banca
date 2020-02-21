package serializator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

/**
 *
 * @author xavi
 */
public class Serializator {

    /**
     *
     * @param <T>
     * @param s
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T unserialize(String s) throws IOException,ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        // Como o Object é Serializable, se pode ler e reconstruír
        T o=(T) ois.readObject();
        ois.close();
        return o;
   }

    /** Write the object to a Base64 string.
     * @param object
     * @return 
     * @throws java.io.IOException */
    public static String serialize(Serializable object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        // Como o obxecto é Serializable, pode escribirse
        oos.writeObject(object);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
    }
}
