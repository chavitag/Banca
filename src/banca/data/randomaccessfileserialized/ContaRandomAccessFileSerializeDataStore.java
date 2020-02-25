package banca.data.randomaccessfileserialized;

import banca.AplicacionBanca;
import banca.ContaBancaria;
import banca.data.BancaBy;
import storage.By;
import storage.randomaccessfile.RandomAccessFileSerializeDataStore;

/**
 *
 * @author xavi
 */
public class ContaRandomAccessFileSerializeDataStore extends RandomAccessFileSerializeDataStore <String,ContaBancaria> {

    public ContaRandomAccessFileSerializeDataStore() {
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
    public void closeDataStore() {
    }
}
