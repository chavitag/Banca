package banca.data.randomaccessfile;

import banca.AplicacionBanca;
import banca.Cliente;
import java.io.IOException;
import java.util.Calendar;
import storage.By;
import storage.randomaccessfile.RandomAccessFileDataStore;

/**
 *
 * @author xavi
 */
public class ClienteRandomAccessFileDataStore  extends RandomAccessFileDataStore <String,Cliente> {
    public ClienteRandomAccessFileDataStore() {
        super(AplicacionBanca.CLIENTES_FILENAME);
    }
    
    @Override
    protected void writeObject(Cliente cliente) throws IOException {
        ras.writeUTF(cliente.getDni());
        ras.writeUTF(cliente.getNome());
        ras.writeUTF(cliente.getApelidos());
        ras.writeLong(cliente.getData_nacemento().getTimeInMillis());
    }

    @Override
    protected Cliente readObject() throws IOException {
        String dni;
        String nome;
        String apelidos;
        Calendar data_nacemento;
        
        dni=ras.readUTF();
        nome=ras.readUTF();
        apelidos=ras.readUTF();
        data_nacemento=Calendar.getInstance();
        data_nacemento.setTimeInMillis(ras.readLong());
        return new Cliente(dni,nome,apelidos,data_nacemento);
    }

    @Override
    protected boolean filter(By c, Object info, Cliente data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeDataStore() {
    }
}
