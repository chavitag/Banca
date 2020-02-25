package banca.data.hashmap;

import banca.entities.ContaBancaria;
import banca.data.BancaBy;
import java.util.ArrayList;
import java.util.Collection;
import storage.By;
import storage.hashmap.HashMapDataStore;

/**
 *
 * @author xavi
 */
public class ContaHashMapDataStore extends HashMapDataStore <String,ContaBancaria> {
    
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
}
