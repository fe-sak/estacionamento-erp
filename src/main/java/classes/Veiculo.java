package classes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

public abstract class Veiculo {

    private String cor;
    private final String placa;
    private Pessoa cliente;
    private BigDecimal taxaHora;
    private LocalDateTime entrada;
    private LocalDateTime saida;

    public String getCor() {
        return cor;
    }

    public String getPlaca() {
        return placa.toUpperCase();
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
        long permanenciaMinima = getPermanencia() > 1 ? getPermanencia() : 1L;

        return (BigDecimal.valueOf(permanenciaMinima).multiply(getTaxaHora()).setScale(2, RoundingMode.HALF_DOWN)).toString();
    }

    public static boolean validarPlaca(String placa) {
        Pattern pattern = Pattern.compile("[A-Z]{3}-\\d{4}$");
        return pattern.matcher(placa.toUpperCase()).matches();
    }
}
