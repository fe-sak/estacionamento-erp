import Classes.Carro;
import Classes.Estacionamento;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");

        Estacionamento e = new Estacionamento();

        System.out.println(e.getVeiculosEstacionados());

        Carro c = new Carro("vermelho");

        e.estacionarVeiculo(c);

        System.out.println(e.getVeiculosEstacionados());
    }
}