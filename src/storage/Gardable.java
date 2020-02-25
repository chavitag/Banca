package storage;

import java.io.Serializable;

/**
 * Este interfaz define que un obxecto "Gardable" dispón dunha chave principal
 * que identificará de modo único ao obxecto (como o DNI ou o CCC dunha conta),
 * esta chave se utilizará para recuperar obxectos do almacenamento.
 * 
 * Tamén define o xeito de transformar o obxecto a un array de bytes (serialización)
 * e volver a obter o obxecto a partir dun array de bytes. Aproveitamos para heredar de Serializable
 * de xeito que todos os Gardable sexan tamén Serializable.
 * 
 *  Un obxecto que implemente a interfaz Serializable dispoñerá dos métodos 
 *  writeObject, readObject e readObjectNoData. Estes métodos escriben e leen 
 *  respectivamente os bytes do obxecto a un OutputStream (que aínda non estudiamos),
 *  o que podemos aproveitar para gardar os bytes nun array. 
 *  Para que un obxecto sexa "Serializable" bastará indicar que a clase 
 *  "implements Serializable" xa que estes 3 métodos teñen implementacións por defecto.
 *  So será necesario sobrepoñelos si queremos un comportamento "especial" (ver API).
 * 
 * @param <K> Clase á que pertence a chave única (por exemplo, para un Cliente o DNI é String)
 * @param <V> Clase á que pertence a chave única (por exemplo, para un Cliente o DNI é String)
 */
public interface Gardable <K,V> extends Serializable {
    /**
     * Devolve o identificador único do obxecto
     * @return 
     */
    public K getKey();
    
    /**
     * Transforma o Obxecto (que é da clase V) a un array de bytes
     * @return Array de byte representando o Obxecto
     */
    public byte[] serialize();

    /**
     * A partir do array bytes, crea de devolve un obxecto da clase V
     * @param bytes
     * @return Obxecto da clase V
     * @throws ClassNotFoundException - A clase V non está definida (é descoñecida).
     */
    public V unserialize(byte[] bytes) throws ClassNotFoundException;
}
