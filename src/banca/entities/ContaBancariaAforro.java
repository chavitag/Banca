package banca.entities;

/**
 * A clase ContaBancariaAforro é unha ContaBancaria
 * As contas de aforro pagan un tipo de interés
 */
public class ContaBancariaAforro extends ContaBancaria {
    private final double tipo; // Tipo de interés.
    
    /**
     * Constructor.
     * @param cliente - Cliente ao que pertence a conta
     * @param ccc - Número de conta
     * @param tipo - Tipo de interés
     */
    public ContaBancariaAforro(Cliente cliente,String ccc,double tipo) {
        super(cliente,ccc);
        this.tipo=tipo/100; // Gardamos o tipo de interés xa dividido por 100
    }
    
    /**
     * Devolve o tipo de interés
     * @return 
     */
    public double getTipo() {
        return tipo*100;
    }

    /**
     * Sobreposición do método toString de Object para obter a representación como String do obxecto.
     * @return String representando o obxecto.
     */
    @Override
    public String toString() {
        String str=super.toString(); // Representación da ContaBancaria
        return "CONTA DE AFORRO: "+str;
    }
    
    /**
     * Devolve un String cos detalles da conta
     * @return  String cos detalles da conta
     */
    @Override
    public String details() {
        return  "CONTA DE AFORRO\n"+
                "-------------------\n"+
                "Datos do Cliente:\n\t"+
                getCliente().details()+
                "\nDatos da Conta:\n\t"+super.toString()+": \n"+
                "\tTipo de Interés: "+getTipo()+"%";
    }
}
