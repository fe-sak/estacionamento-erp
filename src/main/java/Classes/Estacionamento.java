package Classes;

import Excessoes.VeiculoJaEstacionado;
import Excessoes.EstacionamentoCheio;
import Excessoes.VeiculoNaoEncontrado;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Estacionamento {
    private final List<Veiculo> veiculosEstacionados = Arrays.asList(new Veiculo[8]);


    public List<Veiculo> getVeiculosEstacionados() {
        return veiculosEstacionados;
    }

    public void estacionarVeiculo(Veiculo veiculo) throws Exception {
        int posicaoLivre = -1;
        for (int i = 0; i < veiculosEstacionados.size(); i++) {
            if (veiculosEstacionados.get(i) != null) {
                if (Objects.equals(veiculosEstacionados.get(i).placa, veiculo.placa)) {
                    throw new VeiculoJaEstacionado("O veiculo de placa " + veiculo.placa + " já está estacionado.");
                }
            }

            if (veiculosEstacionados.get(i) == null) {
                posicaoLivre = i;
                break;
            }
        }

        if (posicaoLivre == -1) {
            throw new EstacionamentoCheio("O estacionamento está cheio.");
        }

        veiculo.setEntrada(LocalDateTime.now());
        veiculosEstacionados.set(posicaoLivre, veiculo);
    }

    public Veiculo tirarVeiculo(Veiculo veiculo) throws VeiculoNaoEncontrado {
        int posicaoRemovida = -1;
        for (int i = 0; i < veiculosEstacionados.size(); i++) {
            if (veiculosEstacionados.get(i) != null) {
                if (Objects.equals(veiculosEstacionados.get(i).placa, veiculo.placa)) {
                    posicaoRemovida = i;
                    break;
                }
            }
        }

        if (posicaoRemovida == -1) {
            throw new VeiculoNaoEncontrado("O veículo não está estacionado.");
        }

        Veiculo veiculoRemovido = veiculosEstacionados.get(posicaoRemovida);
        veiculoRemovido.setSaida(LocalDateTime.now());
        veiculosEstacionados.set(posicaoRemovida, null);

        return veiculoRemovido;
    }
}
