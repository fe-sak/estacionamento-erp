import classes.Carro;
import classes.Estacionamento;
import classes.Veiculo;
import excessoes.EstacionamentoCheio;
import excessoes.VeiculoJaEstacionado;
import excessoes.VeiculoNaoEncontrado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EstacionamentoTest {
    Estacionamento estacionamento;
    Random random = new Random();

    Veiculo criarVeiculoComPlacaAleatoria() {
        return new Carro(String.valueOf(random.nextDouble()));
    }

    @BeforeEach
    void setUp() {
        estacionamento = new Estacionamento();
    }

    @Test
    void estacionarVeiculoDeveEstacionarNaPrimeiraVaga() throws Exception {
        Veiculo veiculoEsperado = criarVeiculoComPlacaAleatoria();

        int vagaEsperada = 1;
        int vagaReal = estacionamento.estacionarVeiculo(veiculoEsperado);
        assertEquals(vagaEsperada, vagaReal);

        Veiculo veiculoReal = estacionamento.getVeiculosEstacionados().get(0);
        assertEquals(veiculoEsperado, veiculoReal);
    }

    @Test
    void estacionarVeiculoDeveEstacionarNaUltimaVaga() throws Exception {
        for (int i = 0; i < 7; i++) {
            estacionamento.estacionarVeiculo(criarVeiculoComPlacaAleatoria());
        }

        Veiculo veiculoEsperado = new Carro("");

        int vagaEsperada = 8;
        int vagaReal = estacionamento.estacionarVeiculo(veiculoEsperado);

        assertEquals(vagaEsperada, vagaReal);

        Veiculo veiculoReal = estacionamento.getVeiculosEstacionados().get(7);
        assertEquals(veiculoEsperado, veiculoReal);
    }

    @Test
    void estacionarVeiculoDeveGerarExcessaoVeiculoJaEstacionado() throws Exception {
        Veiculo veiculo = criarVeiculoComPlacaAleatoria();

        estacionamento.estacionarVeiculo(veiculo);

        assertThrows(VeiculoJaEstacionado.class, () -> estacionamento.estacionarVeiculo(veiculo));
    }

    @Test
    void estacionarVeiculoDeveGerarExcessaoEstacionamentoCheio() throws Exception {
        for (int i = 0; i < 8; i++) {
            estacionamento.estacionarVeiculo(criarVeiculoComPlacaAleatoria());
        }

        assertThrows(EstacionamentoCheio.class, () -> estacionamento.estacionarVeiculo(criarVeiculoComPlacaAleatoria()));
    }

    @Test
    void tirarVeiculo() throws Exception {
        Veiculo veiculoEsperado = criarVeiculoComPlacaAleatoria();
        estacionamento.estacionarVeiculo(veiculoEsperado);

        Veiculo veiculoReal = estacionamento.retirarVeiculo(veiculoEsperado.getPlaca());
        assertEquals(veiculoEsperado, veiculoReal);
    }

    @Test
    void tirarVeiculoDeveGerarExcessaoVeiculoNaoEncontrado() {
        assertThrows(VeiculoNaoEncontrado.class, () -> estacionamento.retirarVeiculo(criarVeiculoComPlacaAleatoria().getPlaca()));
    }
}