/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banca;

/**
 *
 * @author xavi
 */
public class ContaBancariaAforro extends ContaBancaria {
    private final double tipo;
    
    public ContaBancariaAforro(Cliente cliente,String ccc,double tipo) {
        super(cliente,ccc);
        this.tipo=tipo/100;
    }
    
    public double getTipo() {
        return tipo*100;
    }

    @Override
    public String toString() {
        String str=super.toString();
        return "CONTA DE AFORRO: "+str;
    }
    
    @Override
    public String details() {
        return  "CONTA DE AFORRO\n"+
                "-------------------\n"+
                "Datos do Cliente:\n"+
                getCliente().details()+
                "\nDatos da Conta:\n\t"+super.toString()+": \n"+
                "\tTipo de Interés: "+getTipo()+"%";
    }
}
