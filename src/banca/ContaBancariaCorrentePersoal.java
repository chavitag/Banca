package banca;

public class ContaBancariaCorrentePersoal extends ContaBancariaCorrente {
    private double comision;
    
    public ContaBancariaCorrentePersoal(Cliente cliente,String ccc) {
        super(cliente,ccc);
        this.comision=0;
    }

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
    
      @Override
    public String toString() {
        String str=super.toString();
        return "CONTA CORRENTE PERSOAL: "+str;
    }
    
    @Override
    public String details() {
        return  "CONTA CORRENTE PERSOAL\n"+
                "--------------------------\n"+
                "Datos do Cliente:\n"+
                getCliente().details()+
                "\nDatos da Conta:\n\t"+super.toString()+": \n"+
                "\tComisión Anual: "+comision+"€";
    }

}
