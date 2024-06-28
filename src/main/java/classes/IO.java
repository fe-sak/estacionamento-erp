package classes;

import enums.EOperacao;
import enums.EVeiculo;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.awt.*;
import java.util.Collections;

public abstract class IO {
    static TextIO textIO = TextIoFactory.getTextIO();

    public static TextIO getTextIO() {
        return textIO;
    }

    public static void printar(String message) {
        textIO.getTextTerminal().println(message);
    }

    public static void printarLinhaVazia() {
        textIO.getTextTerminal().println();
    }

    public static void printarInfo(String message) {
        getTextIO().getTextTerminal().executeWithPropertiesConfigurator(
                props -> props.setPromptColor(Color.CYAN),
                t -> t.println(message)
        );
    }

    public static void printarErro(String message) {
        getTextIO().getTextTerminal().executeWithPropertiesConfigurator(
                props -> props.setPromptColor(Color.RED),
                t -> t.println(message)
        );
    }

    public static EOperacao escolherUmaOperacao() {
        return IO.getTextIO().newEnumInputReader(EOperacao.class)
                .withInvalidIndexErrorMessagesProvider((s, s1, i, i1) ->
                        Collections.singletonList("Opção inválida. Digite um número de " + i + " até " + i1))
                .withValueFormatter(EOperacao::getNome)
                .read("Escolha uma operação: ");
    }
}
