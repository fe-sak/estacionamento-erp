package Classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pessoa {

    String nome;
    String cpf;

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public void imprimir() {
        System.out.println("Cliente");
        System.out.println("\tNome: " + this.getNome());
        System.out.println("\tCpf: " + this.getCpf());
    }

    public static boolean validaCPF(String cpf) {
        boolean result;

        Pattern pattern = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$|^\\d{11}$");
        Matcher mat = pattern.matcher(cpf);
        if (!mat.matches()) {
            result = false;
        } else {
            result = true;

        }
        return result;

    }
    
}
