package enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EOperacao {
    ESTACIONAR(1, "Estacionar um veículo"), RETIRAR_VEICULO(2, "Retirar um veículo"), IMPRIMIR_ESTACIONAMENTO_CONSOLE(3, "Imprimir o estacionamento no console"), PRINTAR_REGISTROS_DO_DIA(4, "Ver os registros no console"), PRINTAR_RELATORIO(5, "Imprimir relatório em pdf"), ENCERRAR_PROGRAMA(6, "Parar o programa");

    private final Integer codigo;
    private final String nome;

    EOperacao(Integer codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public static List<String> listar() {
        return Arrays.stream(EOperacao.values()).map(o -> " " + o.codigo + ": " + o.nome).collect(Collectors.toList());
    }

    public static EOperacao escolher(Integer codigo) {
        switch (codigo) {
            case 1: {
                return ESTACIONAR;
            }
            case 2: {
                return RETIRAR_VEICULO;
            }
            case 3: {
                return IMPRIMIR_ESTACIONAMENTO_CONSOLE;
            }
            case 4: {
                return PRINTAR_REGISTROS_DO_DIA;
            }
            case 5: {
                return PRINTAR_RELATORIO;
            }
            default: {
                return ENCERRAR_PROGRAMA;
            }
        }
    }
}
