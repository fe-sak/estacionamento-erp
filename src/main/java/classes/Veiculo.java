package classes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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

    public BigDecimal getTaxaHoraFormatada() {
        return getTaxaHora().setScale(2, RoundingMode.HALF_DOWN);
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

    public String getSaidaFormatada() {
        return this.getSaida().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    @Override
    public String toString() {
        return "Veículo\n" +
                "\tTipo: " + this.getClass().getSimpleName() + "\n" +
                "\tPlaca: " + this.getPlaca() + "\n" +
                "\tCor: " + this.getCor();
    }

    public String getEntradaFormatada() {
        return this.getEntrada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
    }

    public long getPermanencia() {
        return ChronoUnit.HOURS.between(getEntrada(), getSaida());
    }

    public String getPagamento() {
        return (BigDecimal.valueOf(getPermanencia()).multiply(getTaxaHora()).setScale(2, RoundingMode.HALF_DOWN)).toString();
    }
}
