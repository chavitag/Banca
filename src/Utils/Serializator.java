package Utils;

import java.util.Base64;

/**
 * Clase abstracta para serializar/deserializar Obxectos complexos. Non precisan
 * ser Serializable. E capaz de facilitarnos un Obxecto en tres formatos:
 *      Serializado a Array de Bytes
 *      Serializado a String
 *      Obxecto deserializado
 * 
 * O xeito de facer a serialización e deserialización dependerá da implementación
 * particular de doSerialize e doUnserialize
 *
 * @param <T> Clase a serializar ou deserializar
 */
public abstract class Serializator <T> {
    protected T object=null;
    protected byte[] bobject=null;
    private String strobject=null;
    
    public Serializator(T object) {
        if (object==null) throw new IllegalArgumentException();
        this.object=object;
    }
    
    public Serializator(byte[] bobject) {
        if (object==null) throw new IllegalArgumentException();
        this.bobject=bobject;
        this.strobject=Base64.getEncoder().encodeToString(this.bobject); 
    }
    
    public Serializator(String strobject) {
        if (object==null) throw new IllegalArgumentException();
        this.strobject=strobject;
        this.bobject=Base64.getDecoder().decode(this.strobject);
    }
    
    public T getObject() throws ClassNotFoundException {
        return unserialize();
    }
    
    public byte[] getBytes() {
        return serialize();
    }
    
    public String getString() {
        serialize();
        return strobject;
    }

    protected T unserialize() throws ClassNotFoundException {
        if (object==null)  {
            object=(T)doUnserialize();
        }
        return object;
    }
    
    protected byte[] serialize() {
        if (bobject==null) {
            bobject=doSerialize();
            strobject=Base64.getEncoder().encodeToString(bobject);
        }
        return bobject;
    }
    
    protected abstract Object doUnserialize() throws ClassNotFoundException;
    protected abstract byte[] doSerialize();
}
