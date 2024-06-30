package excessoes;

public class EstacionamentoCheio extends Exception{
    public EstacionamentoCheio(String mensagemDeErro) {
        super(mensagemDeErro);
    }
}
