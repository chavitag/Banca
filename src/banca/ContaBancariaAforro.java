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
                "Datos do Cliente:\n\t"+
                getCliente().details()+
                "\nDatos da Conta:\n\t"+super.toString()+": \n"+
                "\tTipo de Interés: "+getTipo()+"%";
    }

    @Override
    public byte[] serialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContaBancariaAforro unserialize(byte[] bytes) throws ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
