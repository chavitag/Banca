package storage;

/**
 *
 * @author xavi
 */
public interface Gardable <K,V> {
    public K getKey();
    public byte[] serialize();
    public V unserialize(byte[] bytes) throws ClassNotFoundException;
}
