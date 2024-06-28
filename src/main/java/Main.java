import classes.*;
import enums.EOperacao;
import enums.EVeiculo;
import org.beryx.textio.TextIO;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Main {
    public static void main(String[] args) {
        IO.printarInfo("++++++++++ Gerenciador de estacionamento inicializado ++++++++++".toUpperCase());
        IO.printarLinhaVazia();

        Estacionamento estacionamento = new Estacionamento();

        while (true) {
            try {
                EOperacao EOperacao = IO.escolherUmaOperacao();

                if (EOperacao == enums.EOperacao.ENCERRAR_PROGRAMA) {
                    IO.printar("O programa foi encerrado.");
                    Thread.sleep(1000);
                    IO.getTextIO().getTextTerminal().dispose();
                    return;
                }

                executarOperacao(EOperacao, estacionamento);
                IO.printarLinhaVazia();
            } catch (Exception e) {
                IO.printarErro("ERRO: " + e.getMessage());
            }
        }
    }

    private static void executarOperacao(EOperacao EOperacao, Estacionamento estacionamento) throws Exception {
        switch (EOperacao) {
            case ESTACIONAR: {
                IO.printarInfo("Estacionar um veículo foi selecionada");

                Veiculo veiculo = coletarVeiculo(IO.getTextIO());

                int vaga = estacionamento.estacionarVeiculo(veiculo);

                printarTicketEntrada(vaga, veiculo);

                break;
            }
            case RETIRAR_VEICULO: {
                String placa = IO.getTextIO().newStringInputReader().read("Digite a placa do veículo: ");

                Veiculo veiculo = estacionamento.tirarVeiculo(placa);

                printarTicketSaida(veiculo);

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

    private static void printarTicketSaida(Veiculo veiculo) {
        System.out.println("Veículo retirado com sucesso");
        IO.printar(veiculo.toString());

        System.out.println("\tHorário de entrada:" + veiculo.getEntradaFormatada());
        System.out.println("\tHorário de saída:" + veiculo.getSaidaFormatada());
        System.out.println("\tPermanência total: " + veiculo.getPermanencia() + "h");
        System.out.println("\tPagamento: R$" + veiculo.getPagamento());
    }

    private static void printarTicketEntrada(int vaga, Veiculo veiculo) {
        IO.printar("TICKET DO ESTACIONAMENTO ----------------------------------------------");

        IO.printar("Vaga: 0" + vaga);
        IO.printar("Entrada: " + veiculo.getEntradaFormatada());
        IO.printar("Taxa por hora: R$ " + veiculo.getTaxaHoraFormatada());

        IO.printar(veiculo.getCliente().toString());
        IO.printar(veiculo.toString());
    }

    public static Veiculo coletarVeiculo(TextIO textIO) {
        Pessoa dono = coletarPessoa(textIO);

        EVeiculo tipoVeiculo = textIO.newEnumInputReader(EVeiculo.class).withValueFormatter(EVeiculo::getNome).read("Digite o tipo do veículo: ");

        String placa = textIO.newStringInputReader().read("Digite a placa do veículo: ");

        String cor = textIO.newStringInputReader().read("Digite a cor do veículo: ");

        Veiculo veiculo;

        if (tipoVeiculo == EVeiculo.CARRO) {
            veiculo = new Carro(cor, placa, dono);
        } else {
            veiculo = new Moto(cor, placa, dono);
        }

        return veiculo;
    }

    private static Pessoa coletarPessoa(TextIO textIO){
        String nome =  textIO.newStringInputReader().read("Digite o nome do cliente: ");
        String cpf =  textIO.newStringInputReader().read("Digite o cpf do cliente: ");

        return new Pessoa(nome, cpf);
    }
}