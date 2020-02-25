package banca.serializator;

import storage.Gardable;

/**
 *
 * @author xavi
 * @param <T>
 */
public class Serializator <T extends Gardable> extends Utils.Serializator <T> {

    public Serializator(T object) {
        super(object);
    }
    
    public Serializator(byte[] bobject) {
        super(bobject);
    }
    
    public Serializator(String strobject) {
        super(strobject);
    }
    
    @Override
    protected Object doUnserialize() throws ClassNotFoundException {
        return object.unserialize(getBytes());
    }

    @Override
    protected byte[] doSerialize() {
        return object.serialize();
    }
}
