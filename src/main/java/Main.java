import classes.*;
import enums.Operacao;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IO.printarInfo("++++++++++ Gerenciador de estacionamento inicializado ++++++++++".toUpperCase());
        IO.printarLinhaVazia();

        Scanner scanner = new Scanner(System.in);
        Estacionamento estacionamento = new Estacionamento();

        while (true) {
            try {
                Operacao operacao = escolherUmaOperacao();

                if (operacao == Operacao.ENCERRAR_PROGRAMA) {
                    IO.printar("O programa foi encerrado.");
                    Thread.sleep(1000);
                    IO.getTextIO().getTextTerminal().dispose();
                    return;
                }

                executarOperacao(operacao, scanner, estacionamento);
                System.out.println();
            } catch (Exception e) {
                IO.printarErro("ERRO: " + e.getMessage());
            }
        }
    }

    private static void executarOperacao(Operacao operacao, Scanner scanner, Estacionamento estacionamento) throws Exception {
        switch (operacao) {
            case ESTACIONAR: {
                System.out.println("Estacionar um veículo foi selecionada");

                System.out.println("Digite o nome do cliente: ");
                String nomeDono = scanner.nextLine();

                System.out.println("Digite o cpf do cliente: ");
                String cpfDono = scanner.nextLine();

                System.out.println("Qual o veículo? Digite 1 para carro e 2 para moto: ");
                String tipoVeiculo = scanner.nextLine();

                while (!Arrays.asList(new String[]{"1", "2"}).contains(String.valueOf(operacao.getCodigo()))) {
                    System.out.print("Veículo inválido.\nDigite 1 para carro e 2 para moto: ");
                    tipoVeiculo = scanner.nextLine();
                }

                System.out.print("Digite a placa do veículo: ");
                String placa = scanner.nextLine();

                System.out.println("Digite a cor do veículo: ");
                String cor = scanner.nextLine();

                Pessoa dono = new Pessoa(nomeDono, cpfDono);

                Veiculo veiculo;

                if (tipoVeiculo.equals("1")) {
                    veiculo = new Carro(cor, placa, dono);
                } else {
                    veiculo = new Moto(cor, placa, dono);
                }

                int vaga = estacionamento.estacionarVeiculo(veiculo);

                System.out.println("TICKET DO ESTACIONAMENTO ----------------------------------------------");

                System.out.println("Vaga: 0" + vaga);
                System.out.println("Entrada: " + veiculo.getEntradaFormatada());
                System.out.println("Taxa por hora: R$ " + veiculo.getTaxaHoraFormatada());

                veiculo.getCliente().imprimir();
                veiculo.imprimir();

                break;
            }
            case RETIRAR_VEICULO: {
                System.out.println("Digite a placa do veículo: ");
                String placa = scanner.nextLine();

                Veiculo veiculo = estacionamento.tirarVeiculo(placa);

                System.out.println("Veículo retirado com sucesso"); // TODO pintar de verde
                veiculo.imprimir();

                System.out.println("\tHorário de entrada:" + veiculo.getEntradaFormatada());
                System.out.println("\tHorário de saída:" + veiculo.getSaidaFormatada());
                System.out.println("\tPermanência total: " + veiculo.getPermanencia() + "h");
                System.out.println("\tPagamento: R$" + veiculo.getPagamento());

                break;
            }
            case PRINTAR_ESTACIONAMENTO: {
                System.out.println("3");
                throw new NotImplementedException();
            }
            case PRINTAR_REGISTROS_DO_DIA: {
                System.out.println("4");
                throw new NotImplementedException();
            }
            case PRINTAR_RELATORIO: {
                System.out.println("5");
                throw new NotImplementedException();
            }
        }
    }

    private static Operacao escolherUmaOperacao() {
        return IO.getTextIO().newEnumInputReader(Operacao.class)
                .withInvalidIndexErrorMessagesProvider((s, s1, i, i1) ->
                        Collections.singletonList("Opção inválida. Digite um número de " + i + " até " + i1))
                .withValueFormatter(Operacao::getNome)
                .withPromptAdjustments(true)
                .read("Escolha uma operação: ");
    }
}