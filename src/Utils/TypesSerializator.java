/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author xavi
 */
public class TypesSerializator {
    private enum Types {STRING,INTEGER,LONG,DOUBLE,CALENDAR};
    
    public static byte[] serialize(String str) {
        return serialize(str,str.length());
    }
     
    public static byte[] serialize(String str,int len) {
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
    
    public static byte[] serialize(int value) {
        byte[] bytes = ByteBuffer.allocate(5).putInt(1,value).array();
        bytes[0]=(byte)Types.INTEGER.ordinal();
        return bytes;
    }
    
    public static byte[] serialize(long value) {
        byte[] bytes = ByteBuffer.allocate(9).putLong(1,value).array();
        bytes[0]=(byte)Types.LONG.ordinal();
        return bytes;
    }
    
    public static byte[] serialize(double number) {
        byte[] bytes = ByteBuffer.allocate(9).putDouble(1,number).array();
        bytes[0]=(byte)Types.DOUBLE.ordinal();
        return bytes;
    }
    
    public static byte[] serialize(Calendar value) {
        byte[] bytes = ByteBuffer.allocate(9).putLong(1,value.getTimeInMillis()).array();
        bytes[0]=(byte)Types.CALENDAR.ordinal();
        return bytes;
    }
    
    public static <T> T unserialize(Class <T> clase,byte[] info) throws ClassNotFoundException {
        return (T)unserialize(info);
    }
    
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
        
    private static String unserializeString(byte[] info) {
        int len=ByteBuffer.wrap(info).getInt(5); 
        byte[] bstring=new byte[len];
        System.arraycopy(info,9,bstring,0,len);
        return new String(bstring);
    }
    
    private static Integer unserializeInteger(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt(1);
    }
       
    public static Long unserializeLong(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong(1);
    }  
        
    public static Double unserializeDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble(1);
    }
    
     public static Calendar unserializeCalendar(byte[] bytes) {
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis( ByteBuffer.wrap(bytes).getLong(1));
        return c;
    }
     
    public static void addAll(Collection <Byte> data,byte[] info) {
        for(Byte b: info) data.add(b);
    }
    
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
        
        List <Byte> buffer=new ArrayList <>();
        addAll(buffer,serialize(str1,4));
        addAll(buffer,serialize(str2,20));
        addAll(buffer,serialize(str3));
        addAll(buffer,serialize(c));
        addAll(buffer,serialize(str4,80));
        data=getByteArray(buffer);
        txt=new byte[data.length];
        int idx=0;
        
        System.arraycopy(data, 0, txt, 0, data.length);
        System.out.println(data.length+"--->"+unserialize(String.class,txt));
        idx+=next(txt);
        System.arraycopy(data, idx, txt, 0, data.length-idx-1);
        System.out.println(data.length+"--->"+unserialize(String.class,txt));
        
        idx+=next(txt);
        System.arraycopy(data, idx, txt, 0, data.length-idx-1);
        System.out.println(data.length+"--->"+unserialize(String.class,txt));
        
        idx+=next(txt);
        System.arraycopy(data, idx, txt, 0, data.length-idx-1);
        System.out.println(data.length+"--->"+unserialize(Calendar.class,txt));
        
        idx+=next(txt);
        System.arraycopy(data, idx, txt, 0, data.length-idx-1);
        System.out.println(data.length+"--->"+unserialize(String.class,txt));
    }
}
