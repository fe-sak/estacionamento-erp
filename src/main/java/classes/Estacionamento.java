package classes;

import excessoes.VeiculoJaEstacionado;
import excessoes.EstacionamentoCheio;
import excessoes.VeiculoNaoEncontrado;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Estacionamento {
    private final List<Veiculo> veiculosEstacionados = Arrays.asList(new Veiculo[8]);

    public List<Veiculo> getVeiculosEstacionados() {
        return veiculosEstacionados;
    }

    public int estacionarVeiculo(Veiculo veiculo) throws Exception {
        int vaga = -1;
        for (int i = 0; i < veiculosEstacionados.size(); i++) {
            if (veiculosEstacionados.get(i) != null) {
                if (Objects.equals(veiculosEstacionados.get(i).placa, veiculo.placa)) {
                    throw new VeiculoJaEstacionado("O veiculo de placa " + veiculo.placa + " já está estacionado.");
                }
            }

            if (veiculosEstacionados.get(i) == null) {
                vaga = i;
                break;
            }
        }

        if (vaga == -1) {
            throw new EstacionamentoCheio("O estacionamento está cheio.");
        }

        veiculosEstacionados.set(vaga, veiculo);

        return vaga + 1;
    }

    public Veiculo tirarVeiculo(String placa) throws VeiculoNaoEncontrado {
        int vagaLiberada = -1;
        for (int i = 0; i < veiculosEstacionados.size(); i++) {
            if (veiculosEstacionados.get(i) != null) {
                if (Objects.equals(veiculosEstacionados.get(i).placa, placa)) {
                    vagaLiberada = i;
                    break;
                }
            }
        }

        if (vagaLiberada == -1) {
            throw new VeiculoNaoEncontrado("O veículo não está estacionado.");
        }

        Veiculo veiculoRemovido = veiculosEstacionados.get(vagaLiberada);
        veiculoRemovido.setSaida(LocalDateTime.now());
        veiculosEstacionados.set(vagaLiberada, null);

        return veiculoRemovido;
    }
}
