package banca.data.hashmap.randomaccessfile;

import banca.AplicacionBanca;
import banca.entities.Entidad;
import java.io.IOException;
import java.util.Collection;
import storage.By;
import storage.hashmap.RandomAccessFileHashMapDataStore;

/**
 *
 * @author xavi
 */
public class EntidadRandomAccessFileHashMapDataStore extends RandomAccessFileHashMapDataStore <String,Entidad> {
    
    public EntidadRandomAccessFileHashMapDataStore() {
        super(AplicacionBanca.F_ENTIDADES);
    }
    
    @Override
    public Entidad loadBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Collection<Entidad> loadAllBy(By c, Object info) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected Entidad readObject() throws IOException {
        String codigo=ras.readUTF();
        String nome=ras.readUTF();
        return new Entidad(codigo,nome);
    }

    @Override
    protected void writeObject(Entidad object) throws IOException {
        ras.writeUTF(object.getCodigo());
        ras.writeUTF(object.getNome());
    }
}
