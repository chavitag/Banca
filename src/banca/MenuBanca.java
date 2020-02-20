package banca;

import Utils.Menu;
import Utils.Utilidades;
import java.util.Calendar;
import java.util.Collection;

public class MenuBanca extends Menu {
    
    public MenuBanca() {
        super(new String[]{ "Abrir unha nova conta","Ver listado de clientes","Ver listado das contas dispoñibles",
                            "Listado de contas dun cliente","Ver Datos de Conta",
                            "Realizar ingreso","Retirar efectivo",
                            "Consultar saldo","Saír"});
    }

    @Override
    public boolean menu(int opc) throws Exception {
        String dni;
        String nome;
        String apelidos;
        String ccc;
        double cantidade;
        char choose;
        Calendar data;
        Cliente cliente;
        ContaBancaria conta;
        Collection <ContaBancaria> lista;
        
        switch(opc) {
            case 1: 
                System.out.println("Alta de Conta Bancaria");
                System.out.println("Cliente:");
                dni=Utilidades.getString("DNI: ");
                if (!Cliente.verificaDNI(dni)) throw new Exception("DNI erróneo");
                cliente=AplicacionBanca.clients.load(dni);
                if (cliente!=null) System.out.println("O cliente xa existe: "+cliente);
                else {
                    nome=Utilidades.getString("Nome: ");
                    apelidos=Utilidades.getString("Apelidos: ");
                    data=Utilidades.getData("Data nacemento: ");
                    cliente=new Cliente(dni,nome,apelidos,data);
                    AplicacionBanca.clients.save(cliente);
                }
                System.out.println("Datos da Conta:");
                ccc=Utilidades.getString("Número de Conta (CCC): ");
                if (!ContaBancaria.verificaCCC(ccc)) throw new Exception("Número de Conta erróneo");
                if (AplicacionBanca.contas.load(ccc)!=null) throw new Exception("A conta xa existe");
                choose=Utilidades.choose("Conta de A(f)orro, Conta Corrente (E)mpresa ou Conta Corrente (P)ersoal? ","FEPfep");
                conta=contaBancariaFactory(cliente,ccc,choose);
                AplicacionBanca.contas.save(conta);
                break;
                
            case 2:
                System.out.println("Lista de Clientes:");
                Utilidades.showArray(AplicacionBanca.clients.loadAll());
                break;
                
            case 3:
                lista=AplicacionBanca.contas.loadAll();
                System.out.println("Lista de Contas:");
                Utilidades.showArray(lista);
                break;
                
            case 4:
                dni=Utilidades.getString("DNI: ");
                lista=AplicacionBanca.contas.loadAllBy(BancaBy.DNI, dni);
                System.out.println("Listado de contas do Cliente "+dni);
                Utilidades.showArray(lista);
                break;
            
            case 5:
                ccc=Utilidades.getString("Número de Conta (CCC): ");
                conta=AplicacionBanca.contas.load(ccc);
                if (conta!=null) {
                    System.out.println(conta.details());
                    if (conta instanceof ContaBancariaCorrente) {
                        choose=Utilidades.choose("Xestionar (D)omiciliacións ","Dd");
                        if (Character.toLowerCase(choose)=='d') 
                            new MenuContaCorrente((ContaBancariaCorrente)conta).run();
                    }
                }
                else System.out.println("A conta "+ccc+" non existe");
                break;
                
            case 6:
                ccc=Utilidades.getString("Número de Conta (CCC): ");
                conta=AplicacionBanca.contas.load(ccc);
                if (conta!=null) {
                    System.out.println(conta);
                    cantidade=Utilidades.getDouble("Cantidade a Ingresar: ");
                    cantidade=conta.ingreso(cantidade);
                    AplicacionBanca.contas.update(conta);
                    System.out.println("O novo saldo é de "+cantidade);
                }
                else System.out.println("A conta "+ccc+" non existe");
                break; 
                
            case 7:
                ccc=Utilidades.getString("Número de Conta (CCC): ");
                conta=AplicacionBanca.contas.load(ccc);
                if (conta!=null) {
                    System.out.println(conta);
                    cantidade=Utilidades.getDouble("Cantidade a Retirar: ");
                    cantidade=conta.reintegro(cantidade);
                    AplicacionBanca.contas.update(conta);
                    System.out.println("O novo saldo é de "+cantidade);
                }
                else System.out.println("A conta "+ccc+" non existe");
                break;   

            case 8:
                ccc=Utilidades.getString("Número de Conta (CCC): ");
                conta=AplicacionBanca.contas.load(ccc);
                if (conta!=null) {
                    System.out.println(conta.details());
                }
                else System.out.println("A conta "+ccc+" non existe");
                break;   

            case 9:
                return true;
        }
        return false;
    }
    
    public static ContaBancaria contaBancariaFactory(Cliente cliente,String ccc,char type) throws Exception {
        ContaBancaria conta=null;
        double descuberto;
        double tipo;
        double comision;
        
        if (!ContaBancaria.verificaCCC(ccc)) throw new Exception("Número de Conta erróneo");
        switch(Character.toLowerCase(type)) {
            case 'f':
                tipo=Utilidades.getDouble("Tipo de Interés? ");
                conta=new ContaBancariaAforro(cliente,ccc,tipo);
                break;
            case 'e':
                descuberto=Utilidades.getDouble("Máximo descuberto: ");
                tipo=Utilidades.getDouble("Tipo descuberto: ");
                comision=Utilidades.getDouble("Comisión fixa descuberto: ");
                conta=new ContaBancariaCorrenteEmpresa(cliente,ccc,descuberto,tipo,comision);
                break;
            case 'p':
                comision=Utilidades.getDouble("Comisión de mantemento: ");
                conta=new ContaBancariaCorrentePersoal(cliente,ccc,comision);
                break;
            case '*':
                throw new Exception("Operación cancelada");
        }
        return conta;
    }
}