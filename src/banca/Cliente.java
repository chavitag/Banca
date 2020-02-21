package banca;

import Utils.Utilidades;
import java.io.Serializable;
import java.util.Calendar;
import storage.Gardable;

/**
 *
 * @author xavi
 */
public class Cliente implements Serializable,Gardable <String> {
    private String dni;
    private String nome;
    private String apelidos;
    private Calendar data_nacemento;

    public Cliente(String dni, String nome, String apelidos, Calendar data) {
        this.dni=dni;
        this.nome=nome;
        this.apelidos=apelidos;
        this.data_nacemento=data;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the apelidos
     */
    public String getApelidos() {
        return apelidos;
    }

    /**
     * @param apelidos the apelidos to set
     */
    public void setApelidos(String apelidos) {
        this.apelidos = apelidos;
    }

    /**
     * @return the data_nacemento
     */
    public Calendar getData_nacemento() {
        return data_nacemento;
    }

    /**
     * @param data_nacemento the data_nacemento to set
     */
    public void setData_nacemento(Calendar data_nacemento) {
        this.data_nacemento = data_nacemento;
    }
        
    public static boolean verificaDNI(String dni) {
        char[] l={'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
        char firstchar,lastchar;
        String number;
        int length=dni.length();
        int val;
        
        if (length!=9) return false;
        firstchar=dni.charAt(0);
        lastchar=dni.charAt(length-1);
        // NIE
        if (!Character.isDigit(firstchar)) {
            val=0;
            switch(Character.toUpperCase(firstchar)) {
                case 'Z': val++; 
                case 'Y': val++;
                case 'X': break;
                default: return false;
            }
            dni=val+dni.substring(1);
        } 
        number=dni.substring(0,length-1);
        try {
            val=Integer.parseInt(number);
            if (l[val%23]!=lastchar) return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    public String details() {
        String info=this+"\n\tData Nacemento: "+Utilidades.strData(data_nacemento);
        return info;
    }
    
    @Override
    public String toString() {
        return dni+": "+nome+" "+apelidos;
    }
    
    @Override
    public String getKey() {
        return dni;
    }
}
