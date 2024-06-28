package enums;

import classes.Carro;
import classes.Moto;
import classes.Pessoa;
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

    public Veiculo instanciar(String cor, String placa, Pessoa cliente) {
        switch (this) {
            case MOTO: {
                return new Moto(cor, placa, cliente);
            }
            case CARRO: {
                return new Carro(cor, placa, cliente);
            }
        }
        return null;
    }
}
