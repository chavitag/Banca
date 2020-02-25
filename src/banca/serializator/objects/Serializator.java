package banca.serializator.objects;

import Utils.TypesSerializator;
import banca.Cliente;
import banca.ContaBancaria;
import banca.ContaBancariaAforro;
import banca.ContaBancariaCorrente;
import banca.ContaBancariaCorrenteEmpresa;
import banca.ContaBancariaCorrentePersoal;
import banca.Domiciliacion;
import banca.Entidad;
import banca.ObjectType;
import banca.data.randomaccessfileserialized.ClienteRandomAccessFileSerializeDataStore;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author xavi
 * @param <T>
 */
public class Serializator <T> extends Utils.Serializator <T> {
    private static final int SZ_DNI=9;
    private static final int SZ_NOME=20;
    private static final int SZ_APELIDOS=50;
    private static final int SZ_CCC=20;
    private static final int SZ_CODE=8;
    private static final int SZ_CONCEPTO=30;
    
    private byte[] autorizados;
   
    
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
     */
    @Override
    protected byte[] doSerialize() {
        if (object instanceof Cliente) return this.serialize((Cliente)object);
        else if (object instanceof ContaBancariaAforro) 
                return this.serialize((ContaBancariaAforro)object);
        else if (object instanceof ContaBancariaCorrentePersoal)
                return this.serialize((ContaBancariaCorrentePersoal)object);
        else if (object instanceof ContaBancariaCorrenteEmpresa)
                return this.serialize((ContaBancariaCorrenteEmpresa)object);
        throw new IllegalArgumentException();
    }
  
    protected Object doUnserialize() throws ClassNotFoundException {
        ObjectType t=ObjectType.values()[bobject[0]];
        switch(t) {
            case CLIENTE:
                return unserializeCliente();
            case EMPRESA:
                return unserializeConta(ContaBancariaCorrenteEmpresa.class);
            case PERSOAL:
                return unserializeConta(ContaBancariaCorrentePersoal.class);
            case AFORRO:
                return unserializeConta(ContaBancariaAforro.class);
            case ENTIDAD:
                return unserializeEntidad();
            case DOMICILIACION:
                return unserializeDomiciliacion();
        }
        throw new ClassNotFoundException("Tipo non soportado");
    }
    
    private byte[] serialize(Cliente cliente) {
        ObjectType tc=ObjectType.CLIENTE;
        Collection <Byte> data=new ArrayList <>();
        
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(tc.ordinal()));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(cliente.getDni(),SZ_DNI));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(cliente.getNome(),SZ_NOME));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(cliente.getApelidos(),SZ_APELIDOS));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(cliente.getData_nacemento()));
        return TypesSerializator.getByteArray(data); 
    }
    
    private Cliente unserializeCliente() throws ClassNotFoundException {
        String dni;
        String nome;
        String apelidos;
        Calendar dnacemento;
        byte[] buffer=new byte[bobject.length];
        int idx=1;
        
        System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
        dni=Utils.TypesSerializator.unserialize(buffer);
        idx+=Utils.TypesSerializator.next(buffer);
        
        System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
        nome=Utils.TypesSerializator.unserialize(buffer);
        idx+=Utils.TypesSerializator.next(buffer);
        
        System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
        apelidos=Utils.TypesSerializator.unserialize(buffer);
        idx+=Utils.TypesSerializator.next(buffer);
        
        System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
        dnacemento=Utils.TypesSerializator.unserialize(buffer);
        return new Cliente(dni,nome,apelidos,dnacemento);
    }
    
    private byte[] serialize(ContaBancariaAforro conta) {
        ObjectType tc=ObjectType.AFORRO;
        Collection <Byte> data=new ArrayList <>();
        
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(tc.ordinal()));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getCliente().getDni(),SZ_DNI));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getCcc(),SZ_CCC));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getSaldo()));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getTipo()));
        return TypesSerializator.getByteArray(data); 
    }
    
    public byte[][] serialize(ContaBancariaCorrentePersoal conta) {
        ObjectType tc=ObjectType.PERSOAL;
        Collection <Byte> data=new ArrayList <>();
        byte[][] sdata=new byte[2][];

        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(tc.ordinal()));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getCliente().getDni(),SZ_DNI));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getCcc(),SZ_CCC));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getSaldo()));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getComision()));
        //data.addAll(serializeAutorizados(conta.getAutorizados()));
        //TypesSerializator.addAll(data,serializeCollection(conta.getAutorizados().values()));
        sdata[0]=TypesSerializator.getByteArray(data); 
        sdata[1]=serializeCollection(conta.getAutorizados().values());
        return sdata;
    }
    
    public byte[][] serialize(ContaBancariaCorrenteEmpresa conta) {
        ObjectType tc=ObjectType.EMPRESA;
        Collection <Byte> data=new ArrayList <>();
        byte[][] sdata=new byte[2][];

        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(tc.ordinal()));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getCliente().getDni(),SZ_DNI));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getCcc(),SZ_CCC));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getSaldo()));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getDescubierto()));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getTipod()));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(conta.getComision()));
        //data.addAll(serializeAutorizados(conta.getAutorizados()));
        //TypesSerializator.addAll(data,serializeCollection(conta.getAutorizados().values()));

        //return TypesSerializator.getByteArray(data);  
        sdata[0]=TypesSerializator.getByteArray(data); 
        sdata[1]=serializeCollection(conta.getAutorizados().values());
        return sdata;
    }
    
    private Object unserializeConta(Class type) 
            throws ClassNotFoundException {
        ClienteRandomAccessFileSerializeDataStore cl_ras=new ClienteRandomAccessFileSerializeDataStore();
        ContaBancaria cb=null;
        Cliente cl;
        String dni;
        String ccc;
        double saldo;
        double tipo;
        double descuberto;
        double comision;
                
        byte[] buffer=new byte[bobject.length];
        int idx=1;
        
        System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
        dni=Utils.TypesSerializator.unserialize(String.class,buffer);
        cl=cl_ras.load(dni);
        if (cl==null) throw new ClassNotFoundException("ERROR: Client not found");
        idx+=Utils.TypesSerializator.next(buffer);
        
        System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
        ccc=Utils.TypesSerializator.unserialize(String.class,buffer);
        idx+=Utils.TypesSerializator.next(buffer);
        
        System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
        saldo=Utils.TypesSerializator.unserialize(Double.class,buffer);
        idx+=Utils.TypesSerializator.next(buffer);
        
        if (type == ContaBancariaAforro.class) {
        
            System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
            tipo=Utils.TypesSerializator.unserialize(Double.class,buffer);
            cb=new ContaBancariaAforro(cl,ccc,tipo);
            cb.setSaldo(saldo);
            return cb;
        
        } else if (type == ContaBancariaCorrentePersoal.class) {
            
            System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
            comision=Utils.TypesSerializator.unserialize(Double.class,buffer);
            idx+=Utils.TypesSerializator.next(buffer);
            cb=new ContaBancariaCorrentePersoal(cl,ccc,comision);
        
        } else if (type == ContaBancariaCorrenteEmpresa.class) {
        
            System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
            descuberto=Utils.TypesSerializator.unserialize(Double.class,buffer);
            idx+=Utils.TypesSerializator.next(buffer);
            System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
            tipo=Utils.TypesSerializator.unserialize(Double.class,buffer);
            idx+=Utils.TypesSerializator.next(buffer);
            System.arraycopy(bobject,idx,buffer,0,bobject.length-idx);
            comision=Utils.TypesSerializator.unserialize(Double.class,buffer);
            idx+=Utils.TypesSerializator.next(buffer);
            cb=new ContaBancariaCorrenteEmpresa(cl,ccc,descuberto,tipo,comision);
            
        }
        unserializeAutorizados((ContaBancariaCorrente)cb,bobject,idx);
        return cb;
    }
        
    private byte[] serialize(Entidad e) {
        ObjectType tc=ObjectType.ENTIDAD;
        Collection <Byte> data=new ArrayList <>();

        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(tc.ordinal()));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(e.getCodigo(),SZ_CODE));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(e.getNome(),SZ_NOME));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(e.getMax_autorizado()));
        TypesSerializator.addAll(data,serializeCollection(e.getDomiciliaciones().values()));
        return TypesSerializator.getByteArray(data);
    }
    
    private Entidad unserializeEntidad(byte[] data) {
        
    }
    
    private byte[] serialize(Domiciliacion d) {
        ObjectType tc=ObjectType.DOMICILIACION;
        Collection <Byte> data=new ArrayList <>();

        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(tc.ordinal()));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(d.getCodigo(),SZ_CODE));
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(d.getConcepto(),SZ_CONCEPTO));
        return TypesSerializator.getByteArray(data);
    }
    
    private byte[] serializeCollection(Collection info) {
        Collection <Byte> data=new ArrayList <>();
        int size=info.size();
        byte[] s;
        
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(size));
        for(Object e: info) {
            if (e instanceof Entidad) s=serialize((Entidad)e);
            else if (e instanceof Domiciliacion) s=serialize((Domiciliacion) e);
            else throw new IllegalArgumentException("Object unknown");
            TypesSerializator.addAll(data,s);
        }
        return TypesSerializator.getByteArray(data);
    }
    
    private Collection unserializeCollection(byte[] buffer) {
        Collection c=new ArrayList();
        
        
    }
        
    /*private Collection <Byte> serializeAutorizados(HashMap <String,Entidad> au) {
        Collection <Byte> data=new ArrayList <>();
        int size=au.size();
        Collection <Entidad> lista=au.values();
        
        TypesSerializator.addAll(data,Utils.TypesSerializator.serialize(size));
        for(Entidad e: lista) {
            TypesSerializator.addAll(data,serialize(e));
        }
        return data;
    }*/
    
 
    
}
