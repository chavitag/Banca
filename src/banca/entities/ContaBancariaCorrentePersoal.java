package banca.entities;

/**
 * Unha ContaBancariaCorrentePersoal é unha ContaBancariaCorrente.
 * As contas persoais teñen unha comision de mantemento
 */
public class ContaBancariaCorrentePersoal extends ContaBancariaCorrente {
    private double comision; // Comisión de mantemento
    
    /**
     * Constructor con comisión 0
     * @param cliente Cliente ao que pertence a conta
     * @param ccc  Número de conta
     */
    public ContaBancariaCorrentePersoal(Cliente cliente,String ccc)  {
        super(cliente,ccc);
        this.comision=0;
    }

    /**
     * Constructor indicando a comisión
     * @param cliente Cliente ao que pertence a conta
     * @param ccc Número de conta
     * @param comision Comisión de mantemento
     */
    public ContaBancariaCorrentePersoal(Cliente cliente,String ccc,double comision) {
        super(cliente,ccc);
        this.comision=comision;
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
     * Sobreposición do método toString de Object
     * @return String representando o obxecto
     */
    @Override
    public String toString() {
        String str=super.toString();
        return "CONTA CORRENTE PERSOAL: "+str;
    }
    
    /**
     * Devolve un String cos detalles da conta
     * @return  String cos detalles da conta
     */
    @Override
    public String details() {
        return  "CONTA CORRENTE PERSOAL\n"+
                "--------------------------\n"+
                "Datos do Cliente:\n\t"+
                getCliente().details()+
                "\nDatos da Conta:\n\t"+super.toString()+"\n"+
                "\tComisión Anual: "+comision+"€";
    }
}
