package classes;

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

    @Override
    public String toString() {
        return "Cliente\n" +
                "\tNome: " + this.getNome() + "\n" +
                "\tCPF: " + this.getCpf();
    }
}
