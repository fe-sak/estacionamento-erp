package enums;

import classes.Carro;
import classes.Moto;
import classes.Pessoa;
import classes.Veiculo;

public enum EVeiculo {
    MOTO(1, "Moto"),
    CARRO(2, "Carro");

    private final String nome;
    private final int codigo;

    EVeiculo(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
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

    public static EVeiculo escolher(Integer codigo) {
        if (codigo == 1) {
            return CARRO;
        }
        return MOTO;
    }
}