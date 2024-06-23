import Classes.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Estacionamento estacionamento = new Estacionamento();

        System.out.println("Este programa permite gerenciar um estacionamento");

        while (true) {
            imprimirOperacoesDisponiveis();

            String operacao = escolherUmaOperacao(scanner);

            if (operacao.equals("6")) {
                return;
            }

            executarOperacao(operacao, scanner, estacionamento);
        }
    }

    private static void executarOperacao(String operacao, Scanner scanner, Estacionamento estacionamento) throws Exception {
        switch (operacao) {
            case "1": {
                System.out.println("Estacionar um veículo foi selecionada");

                System.out.println("Digite o nome do cliente: ");
                String nomeDono = scanner.nextLine();

                System.out.println("Digite o cpf do cliente: ");
                String cpfDono = scanner.nextLine();

                System.out.println("Qual o veículo? Digite 1 para carro e 2 para moto: ");
                String tipoVeiculo = scanner.nextLine();

                while (!Arrays.asList(new String[]{"1", "2"}).contains(operacao)) {
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
                System.out.println("Entrada: " + veiculo.getEntrada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")));
                System.out.println("Taxa por hora: R$ " + veiculo.getTaxaHora().setScale(2, RoundingMode.HALF_DOWN));

                System.out.println("Cliente");
                System.out.println("\tNome: " + veiculo.getCliente().getNome());
                System.out.println("\tCpf: " + veiculo.getCliente().getCpf());

                System.out.println("Veículo (" + veiculo.getClass().getSimpleName() + ")");
                System.out.println("\tPlaca: " + veiculo.getPlaca());
                System.out.println("\tCor: " + veiculo.getCor());

                break;
            }
            case "2": {
                System.out.println("2");
                throw new NotImplementedException();
            }
            case "3": {
                System.out.println("3");
                throw new NotImplementedException();
            }
            case "4": {
                System.out.println("4");
                throw new NotImplementedException();
            }
            case "5": {
                System.out.println("5");
                throw new NotImplementedException();
            }
            default: {
                System.out.println("Operação não reconhecida, tente novamente");
            }
        }
    }

    private static String escolherUmaOperacao(Scanner scanner) {
        System.out.print("Escolha uma operação: ");
        String operacao = scanner.nextLine();

        while (!Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6"}).contains(operacao)) {
            System.out.println("Operação inválida.\nDigite uma operação válida: ");
            operacao = scanner.nextLine();
        }
        return operacao;
    }

    private static void imprimirOperacoesDisponiveis() {
        System.out.println("Operações disponíveis:");
        System.out.println("1 - Estacionar um veículo");
        System.out.println("2 - Retirar um veículo");
        System.out.println("3 - Mostrar o estacionamento");
        System.out.println("4 - Ver os registros de hoje");
        System.out.println("5 - Imprimir relatório em pdf");
        System.out.println("6 - Parar o programa");
    }
}