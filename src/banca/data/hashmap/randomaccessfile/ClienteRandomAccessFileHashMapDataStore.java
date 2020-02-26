package banca.data.hashmap.randomaccessfile;

import banca.AplicacionBanca;
import banca.entities.Cliente;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import storage.By;
import storage.hashmap.RandomAccessFileHashMapDataStore;

/**
 *
 * @author xavi
 */
public class ClienteRandomAccessFileHashMapDataStore extends RandomAccessFileHashMapDataStore <String,Cliente> {
    public ClienteRandomAccessFileHashMapDataStore() {
        super(AplicacionBanca.F_CLIENTES);
    }
            
    @Override
    public Cliente loadBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Cliente> loadAllBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
