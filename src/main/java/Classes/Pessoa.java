package Classes;

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
}
