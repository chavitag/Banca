package storage;

import java.io.Serializable;

/**
 *
 * @author xavi
 * @param <K>
 * @param <V>
 */
public interface Gardable <K,V> extends Serializable {
    public K getKey();
    public byte[] serialize();
    public V unserialize(byte[] bytes) throws ClassNotFoundException;
}
