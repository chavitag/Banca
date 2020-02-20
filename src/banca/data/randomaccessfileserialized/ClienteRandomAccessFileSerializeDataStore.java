/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banca.data.randomaccessfileserialized;

import banca.AplicacionBanca;
import banca.Cliente;
import storage.By;
import storage.RandomAccessFileSerializeDataStore;

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
