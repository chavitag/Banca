/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banca;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collection;
import storage.By;
import storage.RandomAccessFileDataStore;

/**
 *
 * @author xavi
 */
public class ContaRandomAccessFileDataStore extends RandomAccessFileDataStore <String,ContaBancaria>{
    private enum TipoConta {AFORRO,EMPRESA,PERSOAL};
    RandomAccessFile autorizados=null;
    
    public ContaRandomAccessFileDataStore()  {
        super(AplicacionBanca.CONTAS_FILENAME);
    }
    
    @Override
    protected boolean filter(By c, Object info, ContaBancaria data) {
        BancaBy by=(BancaBy) c;
        switch(by) {
            case DNI:
                if ((info instanceof String) &&(data.getCliente().getDni().equals((String)info))) return true;
                break;
        }
        return false;
    }
    
    @Override
    protected void writeObject(ContaBancaria object) throws IOException {
        ContaBancariaAforro af;
        ContaBancariaCorrenteEmpresa em;
        ContaBancariaCorrentePersoal per;
        
        TipoConta tc=getType(object);
        ras.writeUTF(tc.name());
        ras.writeUTF(object.getCliente().getDni());
        ras.writeUTF(object.getCcc());
        ras.writeDouble(object.getSaldo());
        switch(tc) {
            case AFORRO: 
                af=(ContaBancariaAforro) object;
                ras.writeDouble(af.getTipo());
                break;
            case EMPRESA:
                em=(ContaBancariaCorrenteEmpresa) object;
                ras.writeDouble(em.getComision());
                ras.writeDouble(em.getDescubierto());
                ras.writeDouble(em.getTipod());
                writeAutorizados(em);
                break;
            case PERSOAL:
                per=(ContaBancariaCorrentePersoal) object;
                ras.writeDouble(per.getComision());
                writeAutorizados(per);
                break;
        }
    }

    @Override
    protected ContaBancaria readObject() throws IOException {
        ContaBancariaAforro af;
        ContaBancariaCorrenteEmpresa em;
        ContaBancariaCorrentePersoal per;
        ClienteRandomAccessFileDataStore cl_ras=new ClienteRandomAccessFileDataStore();
        Cliente cl;
        String dni;
        String ccc;
        double saldo;
        double comision;
        double descuberto;
        double tipo;
        
        TipoConta tc=Enum.valueOf(TipoConta.class, ras.readUTF());
        dni=ras.readUTF();
        cl=cl_ras.load(dni);
        if (cl==null) throw new IOException("ERROR: Client not found");
        ccc=ras.readUTF();
        saldo=ras.readDouble();
        switch(tc) {
            case AFORRO:
                tipo=ras.readDouble();
                af=new ContaBancariaAforro(cl,ccc,tipo);
                af.setSaldo(saldo);
                return af;
            case EMPRESA:
                comision=ras.readDouble();
                descuberto=ras.readDouble();
                tipo=ras.readDouble();
                em=new ContaBancariaCorrenteEmpresa(cl,ccc,descuberto,tipo,comision);
                readAutorizados(em);
                return em;
            case PERSOAL:
                comision=ras.readDouble();
                per=new ContaBancariaCorrentePersoal(cl,ccc,comision);
                readAutorizados(per);
                return per;
        }
        throw new IOException("Bad File Content");
    }
    
    
    private TipoConta getType(ContaBancaria object) {
        if (object instanceof ContaBancariaCorrentePersoal) return TipoConta.PERSOAL;
        if (object instanceof ContaBancariaCorrenteEmpresa) return TipoConta.EMPRESA;
        return TipoConta.AFORRO;
    }
    
    private void writeAutorizados(ContaBancariaCorrente conta) throws IOException {
        Collection <Entidad> lista=conta.listaAutorizados.values();
        autorizados=new RandomAccessFile(conta.getCcc()+"-"+AplicacionBanca.AUTORIZADOS_FILENAME,"rw");
        try {
            autorizados.setLength(0);
            for(Entidad e: lista) {
                writeEntidad(e);
            }
        } finally {
            autorizados.close();
            autorizados=null;
        }
    }
    
    private void writeEntidad(Entidad e) throws IOException {
        Collection <Domiciliacion> lista=e.domiciliaciones.values();
        autorizados.writeUTF(e.getCodigo());
        autorizados.writeUTF(e.getNome());
        autorizados.writeDouble(e.getMax_autorizado());
        autorizados.writeInt(lista.size());
        for(Domiciliacion d: lista) {
            autorizados.writeUTF(d.getCodigo());
            autorizados.writeUTF(d.getConcepto());
        }
    }
    
    private void readAutorizados(ContaBancariaCorrente conta) throws IOException {
        autorizados=new RandomAccessFile(conta.getCcc()+"-"+AplicacionBanca.AUTORIZADOS_FILENAME,"r");
        Entidad e;
        try {
            do {
                e=readEntidad();
                conta.autoriza(e);
            } while(true);
        } catch(EOFException ex) {
        } finally {
            autorizados.close();
        }
    }
    
    private Entidad readEntidad() throws IOException {
        Entidad e;
        String codigo;
        String nome;
        double max;
        int sz;
        
        codigo=autorizados.readUTF();
        nome=autorizados.readUTF();
        max=autorizados.readDouble();
        e=new Entidad(codigo,nome,max);
        sz=autorizados.readInt();
        while(sz > 0) {
            e.domicilia(autorizados.readUTF(),autorizados.readUTF());
            sz--;
        }
        return e;
    }
}