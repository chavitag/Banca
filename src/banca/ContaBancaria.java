package banca;

import storage.Gardable;

public abstract class ContaBancaria implements Gardable <String> {
    private final Cliente cliente;
    private final String ccc;
    private double saldo;
    
    ContaBancaria(Cliente cliente,String ccc) {
        this.cliente=cliente;
        this.ccc=ccc;
        this.saldo=0;
    }
    
    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @return the ccc
     */
    public String getCcc() {
        return ccc;
    }

    /**
     * @return the saldo
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     *
     * @param cantidade
     * @return
     * @throws Exception
     */
    public double ingreso(double cantidade) throws Exception {
        if (cantidade < 0) throw new Exception("Non podes ingresar unha cantidade negativa");
        this.saldo+=cantidade;
        return this.saldo;
    }
    
    /**
     * 
     * @param cantidade
     * @return
     * @throws Exception 
     */
    public double reintegro(double cantidade) throws Exception {
        if (cantidade < 0) throw new Exception("Non podes retirar unha cantidade negativa");
        if (cantidade > getSaldo()) throw new Exception("Saldo Insuficiente");
        setSaldo(getSaldo()-cantidade);
        return getSaldo();   
    }

    /**
     * 
     * @param ccc
     * @return 
     */
    public static boolean verificaCCC(String ccc) {
        return true;
    }
    
    @Override
    public String toString() {
        return "CCC: "+ccc+" Saldo: "+saldo+"€";
    }
    
    @Override
    public String getKey() {
        return ccc;
    }
    
    public abstract String details();
    
}
