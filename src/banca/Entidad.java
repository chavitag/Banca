/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banca;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author xavi
 */
class Entidad {
    private final String codigo;
    private final String nome;
    private double max_autorizado;
    public final HashMap <String,Domiciliacion> domiciliaciones=new HashMap<>();    
    
    Entidad(String codigo,String nome,double max_autorizado) {
        this.codigo=codigo;
        this.nome=nome;
        this.max_autorizado=max_autorizado;
    }
    
    public void domicilia(String codigo,String concepto) {
        Domiciliacion d=new Domiciliacion(codigo,concepto);
        domiciliaciones.put(codigo,d);
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

    public double getMax_autorizado() {
        return max_autorizado;
    }
    
    /**
     * @param max_autorizado the max_autorizado to set
     */
    public void setMax_autorizado(double max_autorizado) {
        this.max_autorizado = max_autorizado;
    }
    
    public String details() {
        String result=this+"\nLista de Recibos:\n\t";
        Collection <Domiciliacion> list=domiciliaciones.values();
        for(Domiciliacion d: list) {
            result+="\t"+d+"\n";
        }
        return result;
    }
    
    @Override
    public String toString() {
        return codigo+" - "+nome+" (Max Autorizado "+max_autorizado+"€)";
    }
}
