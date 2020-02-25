package banca.entities;

import storage.Gardable;

/**
 * Representa unha Entidade que pode ser autorizada (ou non) nunha ContaBancariaCorrente.
 * Queremos poder gardar Entidades nun DataStore... polo tanto implementa a interfaz Gardable
 */
public class Entidad implements Gardable <String>{
    private final String codigo; // Codigo de entidade
    private final String nome;   // Nome de entidade
      
    public Entidad(String codigo,String nome) {
        this.codigo=codigo;
        this.nome=nome;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return codigo+" - "+nome;
    }

    @Override
    public String getKey() {
        return codigo;
    }
}
