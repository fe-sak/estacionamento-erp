package Classes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Veiculo {
    String cor;
    String placa;
    Pessoa dono;
    BigDecimal taxaHora;
    LocalDateTime entrada;
    LocalDateTime saida;

    public Veiculo(String placa) {
        this.placa = placa;
    }

    public Veiculo(String cor, String placa, Pessoa dono, BigDecimal taxaHora) {
        this.cor = cor;
        this.placa = placa;
        this.dono = dono;
        this.taxaHora = taxaHora;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }


}
