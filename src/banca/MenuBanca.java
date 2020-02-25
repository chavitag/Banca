package banca;

import banca.entities.Cliente;
import banca.entities.ContaBancariaCorrentePersoal;
import banca.entities.ContaBancariaCorrenteEmpresa;
import banca.entities.ContaBancariaAforro;
import banca.entities.ContaBancariaCorrente;
import banca.entities.ContaBancaria;
import banca.data.BancaBy;
import Utils.Menu;
import Utils.Utilidades;
import java.util.Calendar;
import java.util.Collection;

/**
 * Menú Principal da aplicación.
 */
public class MenuBanca extends Menu {
    
    public MenuBanca() {
        super("M E N U    P R I N C I P A L",
              new String[]{ "Abrir unha nova conta",
                            "Ver listado de clientes",
                            "Ver listado das contas dispoñibles",
                            "Listado de contas dun cliente",
                            "Xestionar Conta",
                            "Saír"}
        );
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
            case 1: // Nova Conta
                System.out.println("Alta de Conta Bancaria");
                System.out.println("Cliente:");
                dni=Utilidades.getString("DNI: ");
                if (!Cliente.verificaDNI(dni)) throw new Exception("DNI erróneo");
                cliente=AplicacionBanca.CLIENTS.load(dni);
                if (cliente!=null) System.out.println("O cliente xa existe: "+cliente);
                else {
                    nome=Utilidades.getString("Nome: ");
                    apelidos=Utilidades.getString("Apelidos: ");
                    data=Utilidades.getData("Data nacemento: ");
                    cliente=new Cliente(dni,nome,apelidos,data);
                    AplicacionBanca.CLIENTS.save(cliente);  // Gardamos o cliente no DataStore. Si falla lanza Exception
                }
                System.out.println("Datos da Conta:");
                ccc=Utilidades.getString("Número de Conta (CCC): ");
                if (!ContaBancaria.verificaCCC(ccc)) throw new Exception("Número de Conta erróneo");
                if (AplicacionBanca.CONTAS.load(ccc)!=null) throw new Exception("A conta xa existe");
                choose=Utilidades.choose("Conta de A(f)orro, Conta Corrente (E)mpresa ou Conta Corrente (P)ersoal? ","FEPfep");
                conta=contaBancariaFactory(cliente,ccc,choose); // Este método crea contas de varios tipos
                AplicacionBanca.CONTAS.save(conta); // Gardamos a conta no DataStore
                break;
                
            case 2: // Listado de Clientes
                System.out.println("Lista de Clientes:");
                Utilidades.showArray(AplicacionBanca.CLIENTS.loadAll());
                Utilidades.getString("Pulsa Enter para continuar...");
                break;
                
            case 3: // Listado de Contas
                lista=AplicacionBanca.CONTAS.loadAll();
                System.out.println("Lista de Contas:");
                Utilidades.showArray(lista);
                Utilidades.getString("Pulsa Enter para continuar...");
                break;
                
            case 4: // Listado de Contas dun Cliente
                dni=Utilidades.getString("DNI: ");
                lista=AplicacionBanca.CONTAS.loadAllBy(BancaBy.DNI, dni);
                System.out.println("Listado de contas do Cliente "+dni);
                Utilidades.showArray(lista);
                Utilidades.getString("Pulsa Enter para continuar...");
                break;
            
            case 5: // Xestión dunha conta bancaria.
                ccc=Utilidades.getString("Número de Conta (CCC): ");
                conta=AplicacionBanca.CONTAS.load(ccc);
                if (conta!=null) {
                    System.out.println(conta.details());
                    choose=Utilidades.choose("(X)estionar Conta ","Xx");
                    if (Character.toLowerCase(choose)=='x') {
                        // Lanzamos o Menú de Conta Corrente
                        if (conta instanceof ContaBancariaCorrente)
                            new MenuConta((ContaBancariaCorrente)conta).run();
                        else
                            new MenuConta((ContaBancariaAforro)conta).run();
                    }
                }
                else System.out.println("A conta "+ccc+" non existe");
                break;
                
            case 6:
                return true;
        }
        return false;
    }
    
    /**
     * Crea unha ContaBancaria do tipo indicado en type solicitando os datos necesarios.
     * @param cliente Clietne propietario da conta
     * @param ccc Número de conta
     * @param type Tipo de conta a crear 'f' (aforro) 'e' (empresa) ou 'p' (persoal)
     * @return ContaBancaria creada
     * @throws Exception  Error (ccc erróneo, por exemplo)
     */
    public static ContaBancaria contaBancariaFactory(Cliente cliente,String ccc,char type) throws Exception {
        ContaBancaria conta=null;
        double descuberto;
        double tipo;
        double comision;
        
        if (!ContaBancaria.verificaCCC(ccc)) throw new Exception("Número de Conta erróneo");
        switch(Character.toLowerCase(type)) {
            case 'f': // Conta de Aforro
                tipo=Utilidades.getDouble("Tipo de Interés? ");
                conta=new ContaBancariaAforro(cliente,ccc,tipo);
                break;
                
            case 'e': // Conta de Empresa
                descuberto=Utilidades.getDouble("Máximo descuberto: ");
                tipo=Utilidades.getDouble("Tipo descuberto: ");
                comision=Utilidades.getDouble("Comisión fixa descuberto: ");
                conta=new ContaBancariaCorrenteEmpresa(cliente,ccc,descuberto,tipo,comision);
                break;
                
            case 'p': // Conta Persoal
                comision=Utilidades.getDouble("Comisión de mantemento: ");
                conta=new ContaBancariaCorrentePersoal(cliente,ccc,comision);
                break;
                
            case '*':
                throw new Exception("Operación cancelada");
        }
        return conta;
    }
}
