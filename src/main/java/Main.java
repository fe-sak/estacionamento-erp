
import classes.*;
import enums.EOperacao;
import enums.EVeiculo;
import org.beryx.textio.TextIO;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        ConsoleIO consoleio = new ConsoleIO();

        consoleio.printarInfo("++++++++++ Gerenciador de estacionamento inicializado ++++++++++".toUpperCase());
        consoleio.printarLinhaVazia();

        Estacionamento estacionamento = new Estacionamento();

        while (true) {
            try {
                EOperacao EOperacao = escolherUmaOperacao(consoleio);

                if (EOperacao == enums.EOperacao.ENCERRAR_PROGRAMA) {
                    consoleio.printarInfo("O programa foi encerrado.");
                    Thread.sleep(1000);
                    consoleio.getTextIO().getTextTerminal().dispose();
                    return;
                }

                executarOperacao(consoleio, EOperacao, estacionamento);
                consoleio.printarLinhaVazia();
            } catch (Exception e) {
                consoleio.printarErro("ERRO: " + e.getMessage());
            }
        }
    }

    public static Veiculo coletarVeiculo(ConsoleIO consoleio) {
        Pessoa dono = coletarPessoa(consoleio);

        String tipoVeiculo = consoleio.getTextIO().newStringInputReader().read("Escolha um tipo de veículo:\n 1: Carro\n 2: Moto\nDigite sua escolha: ");
        while (!Arrays.stream(EVeiculo.values()).map(o -> String.valueOf(o.getCodigo())).collect(Collectors.toList()).contains(tipoVeiculo)) {
            consoleio.printarErro("Opção inválida. Digite 1 ou 2");
            tipoVeiculo = consoleio.getTextIO().newStringInputReader().read("Escolha um tipo de veículo:\n 1: Carro\n 2: Moto\nDigite sua escolha: ");
        }

        String placa = consoleio.getTextIO().newStringInputReader().read("Digite a placa do veículo: ");

        while (!Veiculo.validarPlaca(placa)) {
            consoleio.printarErro("Placa inválida. Ela deve obedecer a forma AAA-0000");
            placa = consoleio.getTextIO().newStringInputReader().read("Digite a placa do veículo: ");
        }

        String cor = consoleio.getTextIO().newStringInputReader().read("Digite a cor do veículo: ");

        return EVeiculo.escolher(Integer.valueOf(tipoVeiculo)).instanciar(cor, placa, dono);
    }

    private static Pessoa coletarPessoa(ConsoleIO consoleio) {
        String nome = consoleio.getTextIO().newStringInputReader().read("Digite o nome do cliente: ");

        String cpf = consoleio.getTextIO().newStringInputReader().read("Digite o cpf do cliente: ");

        while (!Pessoa.validaCPF(cpf)) {
            consoleio.printarErro("CPF inválido. O CPF deve ter a forma 000.000.000-04");
            cpf = consoleio.getTextIO().newStringInputReader().read("Digite o cpf do cliente: ");
        }

        return new Pessoa(nome, cpf);
    }

    public static EOperacao escolherUmaOperacao(ConsoleIO consoleio) {
        String operacao = consoleio.getTextIO().newStringInputReader().read("Escolha uma opção:\n"
                + String.join("\n", EOperacao.listar()) + "\nDigite uma opção");

        while (!Arrays.stream(EOperacao.values()).map(o -> String.valueOf(o.getCodigo())).collect(Collectors.toList()).contains(operacao)) {
            consoleio.printarErro("Opção inválida. Digite um número de 1 a 6");

            operacao = consoleio.getTextIO().newStringInputReader().read("Escolha uma opção:\n "
                    + String.join("\n", EOperacao.listar()) + "\n");
        }

        return EOperacao.escolher(Integer.valueOf(operacao));
    }

    private static void executarOperacao(ConsoleIO consoleio, EOperacao EOperacao, Estacionamento estacionamento) throws Exception {
        switch (EOperacao) {
            case ESTACIONAR: {
                consoleio.printarInfo(EOperacao.getNome() + " foi selecionada");

                Veiculo veiculo = coletarVeiculo(consoleio);

                int vaga = estacionamento.estacionarVeiculo(veiculo);

                consoleio.printarSucesso("Veículo estacionado com sucesso");
                printarTicketEntrada(consoleio, vaga, veiculo);

                break;
            }
            case RETIRAR_VEICULO: {
                consoleio.printarInfo(EOperacao.getNome() + " foi selecionada");

                String placa = consoleio.getTextIO().newStringInputReader().read("Digite a placa do veículo: ");

                while (!Veiculo.validarPlaca(placa)) {
                    consoleio.printarErro("Placa inválida. Ela deve obedecer a forma AAA-0000");

                    placa = consoleio.getTextIO().newStringInputReader().read("Digite a placa do veículo: ");
                }

                Veiculo veiculo = estacionamento.retirarVeiculo(placa);
                consoleio.printarSucesso("Veículo foi retirado com sucesso");

                printarTicketSaida(consoleio, veiculo);
                break;
            }
            case IMPRIMIR_ESTACIONAMENTO_CONSOLE: {
                consoleio.printarInfo(EOperacao.getNome() + " foi selecionada");

                printarEstacionamento(consoleio, estacionamento);
                consoleio.printarSucesso("Estacionamento impresso no console com sucesso");

                break;
            }
            case PRINTAR_REGISTROS_DO_DIA: {
                consoleio.printarInfo(EOperacao.getNome() + " foi selecionada");

                String opcao_d = consoleio.getTextIO().newStringInputReader().read("Informe o dia que deseja visualizar: ");

                int opcao_dia = Integer.parseInt(opcao_d);

                while (opcao_dia > 31 || opcao_dia < 1) {
                    consoleio.printarErro("Valor inválido, informe um dia Valido");
                    opcao_d = consoleio.getTextIO().newStringInputReader().read("Informe o dia que deseja visualizar: ");
                    opcao_dia = Integer.parseInt(opcao_d);
                }

                String registros = estacionamento.printarRegistrosDia(opcao_dia);

                if (registros.trim().isEmpty()) {
                    consoleio.printarSucesso("Não houveram registros no dia.");
                    break;
                }

                consoleio.printarSucesso("Registros do dia: ");
                consoleio.printar(registros);

                break;
            }

            case PRINTAR_RELATORIO: {
                consoleio.printarInfo(EOperacao.getNome() + " foi selecionada");

                String opcaoRelatorio = consoleio.getTextIO().newStringInputReader()
                        .read("Qual o Relatorio deseja gerar? Digite 1 para Apenas Registros Ativos e 2 para Todos os Regitros: ");

                while (!Arrays.asList(new String[]{"1", "2"}).contains(opcaoRelatorio)) {
                    opcaoRelatorio = consoleio.getTextIO().newStringInputReader()
                            .read("Opcao inválida.\nDigite 1 para Apenas Registros Ativos e 2 para Todos os Registros: ");
                }

                estacionamento.GerarPDF(opcaoRelatorio);

                consoleio.printarSucesso("Relatório Gerado com sucesso na pasta ./relatorios");
                break;
            }
        }
    }

    private static void printarTicketSaida(ConsoleIO consoleio, Veiculo veiculo) {
        consoleio.printar(veiculo.toString());

        consoleio.printar("\tHorário de entrada:" + veiculo.getEntradaFormatada());
        consoleio.printar("\tHorário de saída:" + veiculo.getSaidaFormatada());
        consoleio.printar("\tPermanência total: " + veiculo.getPermanencia() + "h");
        consoleio.printar("\tPagamento: R$" + veiculo.getPagamento());
    }

    private static void printarTicketEntrada(ConsoleIO consoleio, int vaga, Veiculo veiculo) {
        consoleio.printar("TICKET DO ESTACIONAMENTO ----------------------------------------------");

        consoleio.printar("Vaga: 0" + vaga);
        consoleio.printar("Entrada: " + veiculo.getEntradaFormatada());
        consoleio.printar("Taxa por hora: R$ " + veiculo.getTaxaHoraFormatada());

        consoleio.printar(veiculo.getCliente().toString());
        consoleio.printar(veiculo.toString());
    }

    public static void printarEstacionamento(ConsoleIO consoleio, Estacionamento estacionamento) {

        java.util.List<String> status_vaga = new ArrayList<>(8);

        int i;

        for (i = 0; i < estacionamento.getVeiculosEstacionados().size(); i++) {

            if (estacionamento.getVeiculosEstacionados().get(i) != null) {
                int vaga = i + 1;
                status_vaga.add("OCUPADO");

            }

            if (estacionamento.getVeiculosEstacionados().get(i) == null) {
                int vaga = i + 1;

                status_vaga.add("-LIVRE-");
            }
        }
        consoleio.printar("    -1-       -2-       -3-       -4-");
        consoleio.printar_2("  ");

        for (i = 0; i <= 3; i++) {
            int local = i;
            if ("OCUPADO".equals(status_vaga.get(i))) {
                consoleio.getTextIO().getTextTerminal()
                        .executeWithPropertiesConfigurator(props -> props
                        .setPromptColor(Color.RED), t
                                -> t.print(status_vaga.get(local)));

            } else {
                consoleio.getTextIO().getTextTerminal()
                        .executeWithPropertiesConfigurator(props -> props
                        .setPromptColor(Color.GREEN), t
                                -> t.print(status_vaga.get(local)));
            }
            if (i < 3) {
                consoleio.printar_2("   ");
            }
        }
        consoleio.printar("");
        for (i = 0; i <= 3; i++) {
            int local = i;
            if ("OCUPADO".equals(status_vaga.get(i))) {
                consoleio.getTextIO().getTextTerminal()
                        .executeWithPropertiesConfigurator(props -> props
                        .setPromptColor(Color.GRAY), t
                                -> t.print("  " + estacionamento.getVeiculosEstacionados().get(local).getPlaca()));

            } else {
                consoleio.printar_2("          ");
            }
        }
        consoleio.printar("");
        consoleio.printar("");
        consoleio.printar("----------------------------------------");
        consoleio.printar("    -5-       -6-       -7-       -8-");
        consoleio.printar_2("  ");
        for (i = 4; i <= 7; i++) {
            int local = i;
            if ("OCUPADO".equals(status_vaga.get(i))) {
                consoleio.getTextIO().getTextTerminal()
                        .executeWithPropertiesConfigurator(props -> props
                        .setPromptColor(Color.RED), t
                                -> t.print(status_vaga.get(local)));

            } else {
                consoleio.getTextIO().getTextTerminal()
                        .executeWithPropertiesConfigurator(props -> props
                        .setPromptColor(Color.GREEN), t
                                -> t.print(status_vaga.get(local)));
            }
            if (i < 7) {
                consoleio.printar_2("   ");
            }
        }
        consoleio.printar("");
        for (i = 4; i <= 7; i++) {
            int local = i;
            if ("OCUPADO".equals(status_vaga.get(i))) {
                consoleio.getTextIO().getTextTerminal()
                        .executeWithPropertiesConfigurator(props -> props
                        .setPromptColor(Color.GRAY), t
                                -> t.print("  " + estacionamento.getVeiculosEstacionados().get(local).getPlaca()));

            } else {
                consoleio.printar_2("          ");
            }
        }
        consoleio.printar("");
        consoleio.printar("");
        consoleio.printar("----------------------------------------");

    }

}
