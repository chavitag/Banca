package banca.entities;

/**
 * ContaBancariaCorrenteEmpresa é unha ContaBancariaCorrent
 * As contas de empresa permiten un descuberto ata unha cantidade, e
 * teñen un tipo e unha comision de descuberto.
 */
public class ContaBancariaCorrenteEmpresa extends ContaBancariaCorrente {
    private double descuberto;  // Importe máximo de descuberto permitido
    private double tipo;        // Tipo de interés aplicado ao descuberto
    private double comision;    // Comisión por descuberto
    
    /**
     * Constructor
     * @param cliente Cliente ao que pertence a Conta
     * @param ccc Número de Conta
     */
    public ContaBancariaCorrenteEmpresa(Cliente cliente,String ccc)  {
        super(cliente,ccc);
        this.descuberto=0;
        this.tipo=0;
        this.comision=0;
    }

    /**
     * Constructor indicando o descuberto tipo e comisión
     * @param cliente Cliente ao que pertence a conta
     * @param ccc Número de conta
     * @param descuberto Importe de descuberto máximo
     * @param tipo - Tipo de interes aplicado ao descuberto
     * @param comision  - Comisión por descuberto
     */
    public ContaBancariaCorrenteEmpresa(Cliente cliente,String ccc,double descuberto,double tipo,double comision)  {
        super(cliente,ccc);
        this.descuberto=descuberto;
        this.tipo=tipo/100;
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
     * @return the tipo
     */
    public double getTipo() {
        return tipo*100;
    }

    /**
     * @param tipod the tipo to set
     */
    public void setTipo(double tipod) {
        this.tipo = tipo/100;
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

    /**
     * Retira unha cantidade da conta tendo en conta o descuberto permitido e 
     * a comisión e tipo por descuberto.
     * @param cantidade - Cantidade a retirar
     * @return Saldo resultante
     * @throws Exception  Si non é posible realizar o reintegro
     */
    @Override
    public double reintegro(double cantidade) throws Exception {
        double saldo=getSaldo();
        
        if (cantidade < 0) throw new Exception("Non podes retirar unha cantidade negativa");
        saldo-=cantidade;
        if (saldo < 0) {
            saldo-=this.comision;
            // O tipo por descuberto non se aplica no instante da retirada
            // saldo-=(saldo*this.tipo);
        }
        if (saldo < descuberto) throw new Exception("Excede o descuberto permitido ("+this.descuberto+")");
        setSaldo(saldo);
        return saldo;
    }
    
    /**
     * Sobreposición do método toString de Object 
     * @return String representando o obxecto.
     */
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
                "\tTipo de Interés Descuberto: "+getTipo()+"%";
    }
}
