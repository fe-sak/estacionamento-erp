package enums;

import classes.Veiculo;

public enum EVeiculo {
    MOTO("Moto"),
    CARRO("Carro");

    private final String nome;

    EVeiculo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
