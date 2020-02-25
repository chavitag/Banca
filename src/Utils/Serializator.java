package Utils;

import java.util.Base64;

/**
 *
 * @author xavi
 * @param <T>
 */
public abstract class Serializator <T> {
    protected T object=null;
    protected byte[] bobject=null;
    private String strobject=null;
    
    public Serializator(T object) {
        if (object==null) throw new IllegalArgumentException();
        this.object=object;
    }
    
    public Serializator(byte[] object) {
        if (object==null) throw new IllegalArgumentException();
        this.bobject=object;
        this.strobject=Base64.getEncoder().encodeToString(bobject); 
    }
    
    public Serializator(String object) {
        if (object==null) throw new IllegalArgumentException();
        this.strobject=object;
        this.bobject=Base64.getDecoder().decode(object);
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
