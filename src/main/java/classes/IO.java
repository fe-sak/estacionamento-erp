package classes;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.awt.*;

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

}
