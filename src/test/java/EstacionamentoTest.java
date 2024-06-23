import Classes.Carro;
import Classes.Estacionamento;
import Classes.Veiculo;
import Excessoes.EstacionamentoCheio;
import Excessoes.VeiculoJaEstacionado;
import Excessoes.VeiculoNaoEncontrado;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EstacionamentoTest {
    Estacionamento estacionamento;
    Veiculo veiculo;
    Random random = new Random();

    Veiculo criarVeiculoComPlacaAleatoria(){
        return new Carro(String.valueOf(random.nextDouble()));
    }

    @BeforeEach
    void setUp() {
        estacionamento = new Estacionamento();
    }

    @Test
    void estacionarVeiculo() throws Exception {
        Veiculo veiculo = criarVeiculoComPlacaAleatoria();
        estacionamento.estacionarVeiculo(veiculo);

        assertEquals(veiculo, estacionamento.getVeiculosEstacionados().get(0));
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
        Veiculo veiculo = criarVeiculoComPlacaAleatoria();
        estacionamento.estacionarVeiculo(veiculo);

        assertEquals(veiculo, estacionamento.tirarVeiculo(veiculo));
    }

    @Test
    void tirarVeiculoDeveGerarExcessaoVeiculoNaoEncontrado() {
        assertThrows(VeiculoNaoEncontrado.class, () -> estacionamento.tirarVeiculo(criarVeiculoComPlacaAleatoria()));
    }
}