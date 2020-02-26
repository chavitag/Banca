package banca.entities;

import banca.AplicacionBanca;
import storage.Gardable;
/**
 * Clase ContaBancaria. representando unha Conta Bancaria
 * Implementa o interfaz Gardable, para poder ser almacenado nun DataStore
 * cunha chave de tipo String (o CCC).
 * 
 * Esta clse é abstracta, xa que non ten sentido instanciar obxectos ContaBancaria
 * por ser demasiado xenéricas.
 */
public abstract class ContaBancaria implements Gardable <String> {
    private final String cliente;  // "chave" do cliente propietario da conta (dni)
    private final String ccc;      // Número de conta
    private double saldo;          // Saldo da conta.
    
    /**
     * Constructor.
     * @param cliente - Cliente ao que pertence a conta. So gardaremos o DNI
     * @param ccc - Número de conta
     * 
     * En caso de que o número de conta sexa incorrecto se lanza unha IllegalArgumentException
     * que e non-checked, non sendo necesario especificar que se lanza nin obrigatoria a súa captura.
     */
    ContaBancaria(Cliente cliente,String ccc)  {
        if (!verificaCCC(ccc)) throw new IllegalArgumentException("Error Núemero de Conta");
        this.cliente=cliente.getKey();
        this.ccc=ccc;
        this.saldo=0;
    }
        
    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        Cliente cli=AplicacionBanca.CLIENTS.load(this.cliente);
        if (cli==null) throw new IllegalArgumentException("Client not found for "+ccc+".");
        return cli;
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
     * Ingreso de cartos
     * @param cantidade cantidade a ingresar
     * @return saldo resultante
     * @throws Exception Erro si o ingreso non é posible
     */
    public double ingreso(double cantidade) throws Exception {
        if (cantidade < 0) throw new Exception("Non podes ingresar unha cantidade negativa");
        this.saldo+=cantidade;
        return this.saldo;
    }
    
    /**
     * Reintegro de cartos
     * @param cantidade Cantidade a retirar
     * @return Saldo resultante
     * @throws Exception Erro si o reintegro non é posible
     */
    public double reintegro(double cantidade) throws Exception {
        if (cantidade < 0) throw new Exception("Non podes retirar unha cantidade negativa");
        if (cantidade > getSaldo()) throw new Exception("Saldo Insuficiente");
        setSaldo(getSaldo()-cantidade);
        return getSaldo();   
    }

    /**
     * Verifica si o número de conta e correcto ou non
     * @param ccc Número de conta
     * @return true si o número de conta é correcto, false en outro caso
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
        return (firstcd==Character.digit(control.charAt(0),10)) && 
               (secondcd==Character.digit(control.charAt(1),10));
    }
    
    /**
     * Sobreposición do método toString de Object que devolve a representación String do obxecto
     * @return String representando a conta bancaria.
     */
    @Override
    public String toString() {
        return "CCC: "+ccc+" Saldo: "+saldo+"€";
    }
    
    /**
     * Implementación do método getKey() de DataStore
     * @return 
     */
    @Override
    public String getKey() {
        return ccc;
    }
    
    /**
     * As contas bancarias deben ter un método details que visualice os detalles da conta
     * @return 
     */
    public abstract String details();
    
}
