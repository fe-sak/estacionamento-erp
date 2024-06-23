package Classes;

import java.math.BigDecimal;

public class Moto extends Veiculo {
    public Moto(String placa) {
        super(placa);
    }

    public Moto(String cor, String placa, Pessoa dono) {
        super(cor, placa, dono, BigDecimal.valueOf(5));
    }
}
