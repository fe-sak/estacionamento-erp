package Classes;

import java.math.BigDecimal;

public class Carro extends Veiculo {
    public Carro(String placa) {
        super(placa);
    }

    public Carro(String cor, String placa, Pessoa dono) {
        super(cor, placa, dono, BigDecimal.valueOf(10));
    }
}