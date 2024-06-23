package Classes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Veiculo {
    String cor;
    String placa;
    Pessoa cliente;
    BigDecimal taxaHora;
    LocalDateTime entrada;
    LocalDateTime saida;

    public String getCor() {
        return cor;
    }

    public String getPlaca() {
        return placa;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public BigDecimal getTaxaHora() {
        return taxaHora;
    }

    public Veiculo(String placa) {
        this.placa = placa;
    }

    public Veiculo(String cor, String placa, Pessoa cliente, BigDecimal taxaHora) {
        this.cor = cor;
        this.placa = placa;
        this.cliente = cliente;
        this.taxaHora = taxaHora;
        this.entrada = LocalDateTime.now();
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }
}
