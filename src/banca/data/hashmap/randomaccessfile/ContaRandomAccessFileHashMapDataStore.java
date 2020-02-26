package banca.data.hashmap.randomaccessfile;

import banca.AplicacionBanca;
import banca.entities.ContaBancaria;
import banca.data.BancaBy;
import banca.data.ObjectType;
import banca.entities.Autorizacion;
import banca.entities.Cliente;
import banca.entities.ContaBancariaAforro;
import banca.entities.ContaBancariaCorrente;
import banca.entities.ContaBancariaCorrenteEmpresa;
import banca.entities.ContaBancariaCorrentePersoal;
import banca.entities.Domiciliacion;
import banca.entities.Entidad;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import storage.By;
import storage.hashmap.RandomAccessFileHashMapDataStore;

/**
 *
 * @author xavi
 */
public class ContaRandomAccessFileHashMapDataStore extends RandomAccessFileHashMapDataStore <String,ContaBancaria> {
    
    public ContaRandomAccessFileHashMapDataStore() {
        super(AplicacionBanca.F_CONTAS);
    }
    
    @Override
    public ContaBancaria loadBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<ContaBancaria> loadAllBy(By c, Object info) {
        Collection <ContaBancaria> result=null;
        BancaBy by=(BancaBy) c;
        switch(by) {
            case DNI:
                if (!(info instanceof String)) return null;
                result=new ArrayList <>();
                for(ContaBancaria cb: lista.values()) {
                    if (cb.getCliente().getDni().equals((String)info)) result.add(cb);
                }
                break;
        }
        return result;
    }

    @Override
    protected void writeObject(ContaBancaria object) throws IOException {
        ContaBancariaAforro af;
        ContaBancariaCorrenteEmpresa em;
        ContaBancariaCorrentePersoal per;
        Cliente cl=object.getCliente();
        ObjectType tc=getType(object);
        
        ras.writeByte(tc.ordinal());    // Tipo de Conta
        ras.writeUTF(cl.getDni());      // DNI do Cliente
        ras.writeUTF(object.getCcc());  // CCC
        ras.writeDouble(object.getSaldo()); // Saldo
        switch(tc) {
            case AFORRO: 
                af=(ContaBancariaAforro) object;
                ras.writeDouble(af.getTipo());  // Tipo de interés
                break;
 
            case EMPRESA:
                em=(ContaBancariaCorrenteEmpresa) object;
                ras.writeDouble(em.getComision());      // Comision descuberto
                ras.writeDouble(em.getDescubierto());   // Máximo descuberto
                ras.writeDouble(em.getTipo());          // Tipo descuberto
                writeAutorizados("AUT_"+em.getCcc()+".dat",em.getAutorizados().values()); // Autorizacións
                break;
                
            case PERSOAL:
                per=(ContaBancariaCorrentePersoal) object;
                ras.writeDouble(per.getComision()); // Comisión conta
                writeAutorizados("AUT_"+per.getCcc()+".dat",per.getAutorizados().values()); // Autorizacions
                break;
        }
    }
    
    @Override
    protected ContaBancaria readObject() throws IOException {
        ContaBancariaAforro af;
        ContaBancariaCorrenteEmpresa em;
        ContaBancariaCorrentePersoal per;
        Cliente cl;
        String ccc;
        double saldo;
        double tipo;
        double comision;
        double descuberto;
        ObjectType tc;
        
        tc=ObjectType.values()[ras.readByte()]; // Tipo de Conta Bancaria
        cl=AplicacionBanca.CLIENTS.load(ras.readUTF()); // DNI do propietario
        if (cl==null) throw new IllegalArgumentException("Client Not Found");
        ccc=ras.readUTF();
        saldo=ras.readDouble(); // Saldo
        
        switch(tc) {
            case AFORRO: 
                tipo=ras.readDouble(); // Tipo de interés
                af=new ContaBancariaAforro(cl,ccc,tipo);
                af.setSaldo(saldo);
                return af;

            case EMPRESA:
                comision=ras.readDouble();  // Comision
                descuberto=ras.readDouble();// Descuberto
                tipo=ras.readDouble();      // Tipo
                em=new ContaBancariaCorrenteEmpresa(cl,ccc,descuberto,tipo,comision);
                em.setSaldo(saldo);
                readAutorizados("AUT_"+em.getCcc()+".dat",em); // Carga Autorizacións
                return em;

            case PERSOAL:
                comision=ras.readDouble();  // Comisión mantemento
                per=new ContaBancariaCorrentePersoal(cl,ccc,comision);
                per.setSaldo(saldo);
                readAutorizados("AUT_"+per.getCcc()+".dat",per); // Carga Autorizacións
                return per;
        }
        return null;
    }
    
    private void readAutorizados(String filename,ContaBancariaCorrente conta) throws FileNotFoundException, IOException {
        RandomAccessFile afile=new RandomAccessFile(filename,"rw");
        
        try {
            do {
                readAutorizacion(afile,conta);
            } while(true);
        } catch (EOFException e) {
        } finally {
            afile.close();
        }
    }
    
    private void readAutorizacion(RandomAccessFile afile,ContaBancariaCorrente conta) throws IOException {
        Entidad entidad;
        double max;
        int ndoms;
        
        entidad=AplicacionBanca.ENTIDADES.load(afile.readUTF());    // Entidad
        if (entidad==null) throw new IllegalArgumentException("Entidade descoñecida");
        max=afile.readDouble(); // Maximo autorizado
        conta.autoriza(entidad, max);

        ndoms=afile.readInt();  // Cargamos Domiciliacions (ndoms domiciliacions)
        while(ndoms > 0) {
            String codigo=afile.readUTF();
            String concepto=afile.readUTF();
            
            conta.domicilia(entidad.getCodigo(), codigo, concepto);
            ndoms--;
        }
    }
    
    private void writeAutorizados(String filename,Collection <Autorizacion> lst) throws FileNotFoundException, IOException {
        RandomAccessFile afile=new RandomAccessFile(filename,"rw");
        
        try {
            afile.setLength(0); // Borramos as autorizacións antiguas
            for(Autorizacion d: lst) {
                writeAutorizacion(afile,d);
            }
        } finally {
            afile.close();
        }
    }
    
    private void writeAutorizacion(RandomAccessFile afile,Autorizacion aut) throws IOException {
        Entidad entidad=aut.getEntidad();
        Collection <Domiciliacion> lst=aut.getDomiciliaciones().values();
        
        afile.writeUTF(entidad.getCodigo()); // Codigo de Entidad
        afile.writeDouble(aut.getMax_autorizado()); // Máximo Autorizado
        afile.writeInt(lst.size()); // Número de Domiciliaciones
        for (Domiciliacion dom: lst) {
            afile.writeUTF(dom.getCodigo()); // Codigo domiciliacion
            afile.writeUTF(dom.getConcepto()); // Concepto da domiciliacion
        }
    }
            
    private ObjectType getType(ContaBancaria object) {
        if (object instanceof ContaBancariaCorrentePersoal) return ObjectType.PERSOAL;
        if (object instanceof ContaBancariaCorrenteEmpresa) return ObjectType.EMPRESA;
        if (object instanceof ContaBancariaAforro) return ObjectType.AFORRO;
        throw new IllegalArgumentException("Obxecto non soportado");
    }    
}
