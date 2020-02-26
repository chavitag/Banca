package banca.data.hashmap.randomaccessfile;

import banca.AplicacionBanca;
import banca.entities.Cliente;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import storage.By;
import storage.hashmap.RandomAccessFileHashMapDataStore;

/**
 *  HashMap persistente mediante RandomAccessFile para Clientes.
 */
public class ClienteRandomAccessFileHashMapDataStore extends RandomAccessFileHashMapDataStore <String,Cliente> {

    /**
     * Constructor. O nome de ficheiro o colle da constante definida na aplicación
     */
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

    /**
     * Escribe os atributos do cliente no RandomAccessFile
     * @param cliente - Obxecto Cliente
     * @throws IOException Erro na escritura
     */
    @Override
    protected void writeObject(Cliente cliente) throws IOException {
        ras.writeUTF(cliente.getDni());
        ras.writeUTF(cliente.getNome());
        ras.writeUTF(cliente.getApelidos());
        ras.writeLong(cliente.getData_nacemento().getTimeInMillis());
    }

    /**
     * Lee os atributos dun cliente do RandomAccessFile e instancia un obxecto Cliente
     * @return Obxecto Cliente recuperado do RandomAccessFile
     * @throws IOException Erro na lectura
     */
    @Override
    protected Cliente readObject() throws IOException {
        String dni;
        String nome;
        String apelidos;
        Calendar data_nacemento;
        
        dni=ras.readUTF();  // dni
        nome=ras.readUTF(); // nome
        apelidos=ras.readUTF(); // apelidos
        data_nacemento=Calendar.getInstance();  
        data_nacemento.setTimeInMillis(ras.readLong()); // Data Nacemento
        return new Cliente(dni,nome,apelidos,data_nacemento);
    }
}
