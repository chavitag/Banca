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
public class ContaBancariaCorrenteEmpresa extends ContaBancariaCorrente {
    private double descuberto;
    private double tipod;
    private double comision;
    
    public ContaBancariaCorrenteEmpresa(Cliente cliente,String ccc) throws Exception {
        super(cliente,ccc);
        this.descuberto=0;
        this.tipod=0;
        this.comision=0;
    }

    public ContaBancariaCorrenteEmpresa(Cliente cliente,String ccc,double descuberto,double tipod,double comision)  {
        super(cliente,ccc);
        this.descuberto=descuberto;
        this.tipod=tipod/100;
        this.comision=comision;
    }
    
    /**
     * @return the descuberto
     */
    public double getDescubierto() {
        return descuberto;
    }

    /**
     * @param descubierto the descuberto to set
     */
    public void setDescubierto(double descubierto) {
        this.descuberto = descubierto;
    }

    /**
     * @return the tipod
     */
    public double getTipod() {
        return tipod*100;
    }

    /**
     * @param tipod the tipod to set
     */
    public void setTipod(double tipod) {
        this.tipod = tipod/100;
    }

    /**
     * @return the comision
     */
    public double getComision() {
        return comision;
    }

    /**
     * @param comision the comision to set
     */
    public void setComision(double comision) {
        this.comision = comision;
    }

    @Override
    public double reintegro(double cantidade) throws Exception {
        double saldo=getSaldo();
        
        if (cantidade < 0) throw new Exception("Non podes retirar unha cantidade negativa");
        saldo-=cantidade;
        if (saldo < 0) {
            saldo-=this.comision;
            saldo-=(saldo*this.tipod);
        }
        if (saldo < descuberto) throw new Exception("Excede o descuberto permitido ("+this.descuberto+")");
        setSaldo(saldo);
        return saldo;
    }
    
    @Override
    public String toString() {
        String str=super.toString();
        return "CONTA CORRENTE EMPRESARIAL: "+str;
    }
    
    @Override
    public String details() {
        return  "CONTA CORRENTE EMPRESARIAL\n"+
                "--------------------------\n"+
                "Datos do Cliente:\n\t"+
                getCliente().details()+
                "\nDatos da Conta:\n\t"+super.toString()+": \n"+
                "\tMáximo Descuberto: "+descuberto+"€"+
                "\tComisión Descuberto: "+comision+"€"+
                "\tTipo de Interés Descuberto: "+getTipod()+"%";
    }
}
