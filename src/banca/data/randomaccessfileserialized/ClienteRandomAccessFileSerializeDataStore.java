package banca.data.randomaccessfileserialized;

import banca.AplicacionBanca;
import banca.Cliente;
import storage.By;
import storage.randomaccessfile.RandomAccessFileSerializeDataStore;

/**
 *
 * @author xavi
 */
public class ClienteRandomAccessFileSerializeDataStore extends RandomAccessFileSerializeDataStore <String,Cliente> {

    public ClienteRandomAccessFileSerializeDataStore() {
        super(AplicacionBanca.CLIENTES_FILENAME);
    }

    @Override
    protected boolean filter(By c, Object info, Cliente data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
