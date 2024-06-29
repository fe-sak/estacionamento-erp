package classes;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.awt.*;

public class ConsoleIO {
    TextIO textIO;

    public TextIO getTextIO() {
        return textIO;
    }

    public ConsoleIO() {
        this.textIO = TextIoFactory.getTextIO();
    }

    public void printar(String message) {
        textIO.getTextTerminal().println(message);
    }

    public void printarLinhaVazia() {
        textIO.getTextTerminal().println();
    }

    public void printarSucesso(String message) {
        getTextIO().getTextTerminal().executeWithPropertiesConfigurator(props -> props.setPromptColor(Color.GREEN), t -> t.println(message));
    }

    public void printarInfo(String message) {
        getTextIO().getTextTerminal().executeWithPropertiesConfigurator(props -> props.setPromptColor(Color.CYAN), t -> t.println(message));
    }

    public void printarErro(String message) {
        getTextIO().getTextTerminal().executeWithPropertiesConfigurator(props -> props.setPromptColor(Color.RED), t -> t.println(message));
    }
}
