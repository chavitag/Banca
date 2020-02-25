package Utils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Esta clase serializa os tipos básicos de Java e alguns obxectos de uso común
 * de xeito que sexa posible posteriormente recoñecelos e deserializalos de modo
 * automático. E unha clase auxiliar que podemos empregar na serialización manual
 * (sen Serializable nin Streams) de obxectos de clases complexas.
 * 
 * @author xavi
 */
public class TypesSerializator {
    private enum Types {STRING,INTEGER,LONG,DOUBLE,CALENDAR};
    
    /**
     * Convirte un String a un array de bytes de lonxitude variable
     * 
     * @param str String a serializar
     * @return  Array de bytes representando o String. O tamaño do array
     * dependerá da lonxitude do String.
     */
    public static byte[] serialize(String str) {
        return serialize(str,str.length());
    }
     
    /**
     * Convirte un String a un array de bytes de lonxitude fixa
     * @param str String a serializar
     * @param len lonxitude máxima desexada para o String
     * @return Array de bytes representando o String de lonxitude len.
     * 
     * Formato:
     *      STRING(1 byte)|Lonxitude desexada (4 bytes)|Lonxitude real (4 bytes)|bytes do String
     */
    public static byte[] serialize(String str,int len) {
        // A lonxitude será o mínimo entre a lonxitude desexada e a lonxitude do String
        int r_len=Math.min(len, str.length());  
       
        byte[] datalen=ByteBuffer.allocate(4).putInt(len).array();
        byte[] stringsize=ByteBuffer.allocate(4).putInt(r_len).array();
        
        byte[] info=str.getBytes();
        byte[] data=new byte[9+len];
        data[0]=(byte)Types.STRING.ordinal();
        System.arraycopy(datalen,0,data,1,4);
        System.arraycopy(stringsize,0,data,5,4);
        System.arraycopy(info,0, data, 9, r_len);
        return data;
    }
    
    /**
     * Convierte un int en un array de bytes
     * @param value int a serializar
     * @return array de bytes representando o int
     * 
     * Formato:
     *  INTEGER(1 byte)|byte|byte|byte|byte
     */
    public static byte[] serialize(int value) {
        byte[] bytes = ByteBuffer.allocate(5).putInt(1,value).array();
        bytes[0]=(byte)Types.INTEGER.ordinal();
        return bytes;
    }
    
     /**
     * Convierte un long en un array de bytes
     * @param value long a serializar
     * @return array de bytes representando o long
     * 
     * Formato:
     *  LONG(1 byte)|byte|byte|byte|byte|bye|byte|byte|byte
     */
    public static byte[] serialize(long value) {
        byte[] bytes = ByteBuffer.allocate(9).putLong(1,value).array();
        bytes[0]=(byte)Types.LONG.ordinal();
        return bytes;
    }
    
    /**
     * Convierte un double en un array de bytes
     * @param value double a serializar
     * @return array de bytes representando o double
     * 
     * Formato:
     *  DOUBLE(1 byte)|byte|byte|byte|byte|bye|byte|byte|byte
     */
    public static byte[] serialize(double number) {
        byte[] bytes = ByteBuffer.allocate(9).putDouble(1,number).array();
        bytes[0]=(byte)Types.DOUBLE.ordinal();
        return bytes;
    }
    
    /**
     * Convierte un Calendar en un array de bytes
     * @param value Calendar a serializar
     * @return array de bytes representando o Calendar
     * 
     * O Calendar se serializa co tempo en milisegundos.
     * 
     * Formato:
     *  CALENDAR(1 byte)|byte|byte|byte|byte|bye|byte|byte|byte
     */
    public static byte[] serialize(Calendar value) {
        byte[] bytes = ByteBuffer.allocate(9).putLong(1,value.getTimeInMillis()).array();
        bytes[0]=(byte)Types.CALENDAR.ordinal();
        return bytes;
    }
     
    /**
     * Deserializa un obxecto dos tipos soportados.
     * @param <T> - Tipo de datos deserializado
     * @param info - Array de Bytes a deserializar
     * @return Obxecto de clase T
     * @throws ClassNotFoundException 
     */
    public static <T> T unserialize(byte[] info) throws ClassNotFoundException {
        Types tc=Types.values()[info[0]];
        T object=null;
        
        switch(tc) {
            case STRING:
                return (T)unserializeString(info);
            case INTEGER:
                return (T)unserializeInteger(info);
            case LONG:
                return (T)unserializeLong(info);
            case DOUBLE:
                return (T)unserializeDouble(info);
            case CALENDAR:
                return (T)unserializeCalendar(info);
        }
        throw new ClassNotFoundException();
    }
    
    /**
     * Si temos no array de bytes varios obxectos, indica a posición de comenzo
     * do seguinte obxecto.
     * @param info - Información serializada.
     * @return Posición do seguinte obxecto a partir do actual
     * @throws ClassNotFoundException 
     */
    public static int next(byte[] info) throws ClassNotFoundException {
         Types tc=Types.values()[info[0]];
        
        switch(tc) {
            case STRING:
                return ByteBuffer.wrap(info).getInt(1)+9;
            case INTEGER:
                return 5;
            case LONG:
                return 9;
            case DOUBLE:
                return 9;
            case CALENDAR:
                return 9;
        }
        throw new ClassNotFoundException();
         
    }
        
    /**
     * Os seguintes métodos deserializan os distintos tipos de ddatos
     * @param bytes Array de Bytes cun String...
     * @return Obxecto deserializado
     */
    
    
    private static String unserializeString(byte[] bytes) {
        if (bytes[0] != Types.STRING.ordinal()) throw new IllegalArgumentException("Not a String");
        int len=ByteBuffer.wrap(bytes).getInt(5); 
        byte[] bstring=new byte[len];
        System.arraycopy(bytes,9,bstring,0,len);
        return new String(bstring);
    }
    
    private static Integer unserializeInteger(byte[] bytes) {
        if (bytes[0] != Types.INTEGER.ordinal()) throw new IllegalArgumentException("Not a Integer");
        return ByteBuffer.wrap(bytes).getInt(1);
    }
       
    public static Long unserializeLong(byte[] bytes) {
        if (bytes[0] != Types.LONG.ordinal()) throw new IllegalArgumentException("Not a Long");
        return ByteBuffer.wrap(bytes).getLong(1);
    }  
        
    public static Double unserializeDouble(byte[] bytes) {
        if (bytes[0] != Types.DOUBLE.ordinal()) throw new IllegalArgumentException("Not a Double");
        return ByteBuffer.wrap(bytes).getDouble(1);
    }
    
     public static Calendar unserializeCalendar(byte[] bytes) {
        if (bytes[0] != Types.CALENDAR.ordinal()) throw new IllegalArgumentException("Not a Calendar");
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis( ByteBuffer.wrap(bytes).getLong(1));
        return c;
    }
     
    /**
     * Engade un Array de bytes a unha Collection de Bytes.
     * Útil para concatenar varios campos nun único array de bytes.
     * @param data Collection de Bytes onde se concatenarán os obxectos serializados
     * @param info Array de bytes a concatenar
     */
    public static void addAll(Collection <Byte> data,byte[] info) {
        for(Byte b: info) data.add(b);
    }
    
    /**
     * Devolve un array de byte almacenados nunha Collection de Bytes
     * @param c Collection de Obxectos Byte
     * @return Array de bytes cos bytes almacenados na Collection
     */
    public static byte[] getByteArray(Collection <Byte> c) {
        byte[] array=new byte[c.size()];
        int idx=0;
        for(Byte b: c) {
            array[idx]=b;
            idx++;
        }
        return array;
    }
     
    public static void main(String[] args) throws ClassNotFoundException {
        String str1="Esto es una prueba";
        String str2="hola";
        String str3="que tal";
        Calendar c=Calendar.getInstance();
        String str4="fin";
        
        byte[] data;
        byte[] txt;
        
        // Serializamos todos os datos nun array de byte[] (data)
        List <Byte> buffer=new ArrayList <>();
        addAll(buffer,serialize(str1,4));
        addAll(buffer,serialize(str2,20));
        addAll(buffer,serialize(str3));
        addAll(buffer,serialize(c));
        addAll(buffer,serialize(str4,80));
        data=getByteArray(buffer);
        
        
        // Deserializamos. Imos copiando ao inicio de txt o Obxecto seguinte a deserializar
        txt=new byte[data.length];
        int idx=0;
        
        // Primeiro Obxecto
        System.arraycopy(data, idx, txt, 0, data.length);
        System.out.println(data.length+"--->"+unserialize(txt));
        idx+=next(txt); // Posición do Seguinte obxecto
        
        // Copiamos a txt, a partir de idx
        System.arraycopy(data, idx, txt, 0, data.length-idx-1);
        System.out.println(data.length+"--->"+unserialize(txt));
        
        idx+=next(txt);
        System.arraycopy(data, idx, txt, 0, data.length-idx-1);
        System.out.println(data.length+"--->"+unserialize(txt));
        
        idx+=next(txt);
        System.arraycopy(data, idx, txt, 0, data.length-idx-1);
        System.out.println(data.length+"--->"+unserialize(txt));
        
        idx+=next(txt);
        System.arraycopy(data, idx, txt, 0, data.length-idx-1);
        System.out.println(data.length+"--->"+unserialize(txt));
    }
}
