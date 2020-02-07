/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banca;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import serializator.Serializator;
import storage.By;
import storage.RandomAccessFileDataStore;

/**
 *
 * @author xavi
 */
public class ContaRandomAccessFileDataStore extends RandomAccessFileDataStore <String,ContaBancaria>{

    public ContaRandomAccessFileDataStore(String filename) {
        super(filename);
    }
    
    @Override
    public ContaBancaria loadBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<ContaBancaria> loadAllBy(By c, Object info) {
        Collection <ContaBancaria> result=null;
        ContaBancaria cb;
        String data;
        BancaBy by=(BancaBy) c;
        switch(by) {
            case DNI:
                if (!(info instanceof String)) return null;
                result=new ArrayList <>();
                try {
                    open();
                    do {
                        data=ras.readUTF(); // Si e fin de ficheiro ou non lee un String lanza unha Exception
                        cb=Serializator.unserialize(data);
                        if (cb.getCliente().getDni().equals((String)info)) result.add(cb);
                    } while(true);
                } catch (EOFException e) { 
                } catch (IOException | ClassNotFoundException e) {
                    result=null;
                } finally {
                    try { close(); } catch(Exception e) {};
                }
                break;
        }
        return result;
    }
}
