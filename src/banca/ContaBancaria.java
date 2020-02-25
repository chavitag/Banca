package banca;

import java.io.Serializable;
import storage.Gardable;

public abstract class ContaBancaria implements Gardable <String,ContaBancaria> {
    private final Cliente cliente;
    private final String ccc; // 20 chars
    private double saldo;
    
    ContaBancaria(Cliente cliente,String ccc)  {
        if (!verificaCCC(ccc)) throw new IllegalArgumentException("Error Núemero de Conta");
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
        int[] pd={4,8,5,10};
        int[] sd={9,7,3,6};
        int[] cc={1,2,4,8,5,10,9,7,3,6};
        int firstcd=0;
        int secondcd=0;
        String banco;
        String sucursal;
        String control;
        String cuenta;
        
        if (ccc.length()!=20) return false;
        banco=ccc.substring(0,4);
        sucursal=ccc.substring(4,8);
        control=ccc.substring(8,10);
        cuenta=ccc.substring(10);
        
        // Son números
        try {
            Integer.parseInt(banco);
            Integer.parseInt(sucursal);
            Integer.parseInt(control);
            Long.parseLong(cuenta);
        } catch(NumberFormatException ex) {
            return false;
        }
        
        // Calculo control
        for(int idx=0;idx<10;idx++) {
            if (idx<4) {
                firstcd+=Character.digit(banco.charAt(idx),10)*pd[idx];
                firstcd+=Character.digit(sucursal.charAt(idx),10)*sd[idx];
            }
            secondcd+=Character.digit(cuenta.charAt(idx),10)*cc[idx];
        }
        firstcd=11-(firstcd%11);
        if (firstcd > 9) firstcd=11-firstcd;
        secondcd=11-(secondcd%11);
        if (secondcd > 9) secondcd=11-secondcd;
        // Resultado
        return (firstcd==Character.digit(control.charAt(0),10)) && (secondcd==Character.digit(control.charAt(1),10));
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
