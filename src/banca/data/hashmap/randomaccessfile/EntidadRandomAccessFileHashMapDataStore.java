package banca.data.hashmap.randomaccessfile;

import banca.AplicacionBanca;
import banca.entities.Entidad;
import java.io.IOException;
import java.util.Collection;
import storage.By;
import storage.hashmap.RandomAccessFileHashMapDataStore;

/**
  *  HashMap persistente mediante RandomAccessFile para Entidades.
 */
public class EntidadRandomAccessFileHashMapDataStore extends RandomAccessFileHashMapDataStore <String,Entidad> {
    
    /**
     * Constructor. O nome do arquivo o colle da constante definida en AplicacionBanca
     */
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

    /**
     * Lee os atributos dunha Entidade e instancia un obxecto Entidade
     * @return Obxecto entidade lido do RandomAccessFile
     * @throws IOException Erro de Lectura
     */
    @Override
    protected Entidad readObject() throws IOException {
        String codigo=ras.readUTF();    // Codigo
        String nome=ras.readUTF();      // Nome
        return new Entidad(codigo,nome);
    }

    /**
     * Garda os atributos dunha Entidad no RandomAccessFile
     * @param object Entidade a gardar
     * @throws IOException  Erro ao escribir no ficheiro
     */
    @Override
    protected void writeObject(Entidad object) throws IOException {
        ras.writeUTF(object.getCodigo());   // Codigo
        ras.writeUTF(object.getNome());     // Nome
    }
}
