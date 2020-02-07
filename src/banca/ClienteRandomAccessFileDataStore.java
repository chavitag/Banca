/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banca;

import java.util.Collection;
import storage.By;
import storage.RandomAccessFileDataStore;

/**
 *
 * @author xavi
 */
public class ClienteRandomAccessFileDataStore extends RandomAccessFileDataStore <String,Cliente> {

    public ClienteRandomAccessFileDataStore(String filename) {
        super(filename);
    }
    
    @Override
    public Cliente loadBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Cliente> loadAllBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
