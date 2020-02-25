package storage;

import java.util.Collection;

/**
 * Define as operacións básicas que debe soportar un DataStore
 * 
 * @param <K> Clase á que pertence a chave do obxecto Gardable
 * @param <T> Clase á que pertence o obxecto Gardable
 */
public interface DataStore <K,T extends Gardable> {
        /**
         * Garda o obxecto no DataStore. O obxecto NON debe estar xa no datastore
         * 
         * @param object Obxecto a Gardar
         * @throws DataStoreException Non se gardou o obxecto (por exemplo, porque xa existe un obxecto con isa chave)
         */
        public void save(T object) throws DataStoreException;
        
        /**
         * Actualiza o obxecto almacenado no DataStore coa nova información. 
         * 
         * @param object Obxecto a Actualizar
         * @throws DataStoreException Non se actualizou o obxecto (por exemplo, porque non existe)
         */
        public void update(T object) throws DataStoreException;
        
        /**
         * Permite recuperar UN obxecto polo criterio indicado, tendo en conta a información 
         * facilitada en info. Por exemplo, podemos querer recuperar un Cliente por apelidos...
         * 
         * @param c Criterio para seleccionar o obxecto
         * @param info Información para elexir o obxecto 
         * @return Obxecto solicitado ou NULL si non existe
         */
        public T loadBy(By c,Object info); 
        
        /**
         * Permite recuperar UN obxecto pola súa chave.
         * 
         * @param info valor da chave do obxecto a recuperar
         * @return Obxecto solicitiado ou NULL si non existe
         */
        public T load(K info);
        
        /**
         * Devolve todos os obxectos do DataStore que cumplen co criterio indicado
         * @param c Criterio de selección dos obxectos
         * @param info Información para seleccionar os obxectos
         * @return Collection con todos os obxectos seleccionados.
         */
        public Collection <T> loadAllBy(By c,Object info);
        
        /**
         * Devolve todos os obxectos do DataStore
         * 
         * @return Collection con todos os obxectos do DataStore.
         */
        public Collection <T> loadAll();
      
        /**
         * "Abre" o DataStore. 
         */
        public void openDataStore() throws DataStoreException;
        
        /**
         * "Pecha" o DataStore. 
         */
        public void closeDataStore() throws DataStoreException;
}
