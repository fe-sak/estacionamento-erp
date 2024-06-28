package Classes;

import Excessoes.VeiculoJaEstacionado;
import Excessoes.EstacionamentoCheio;
import Excessoes.VeiculoNaoEncontrado;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Estacionamento {

    private final List<Veiculo> veiculosEstacionados = Arrays.asList(new Veiculo[8]);
    private final List<Veiculo> registro_geral = new ArrayList<>();

    public List<Veiculo> getVeiculosEstacionados() {
        return veiculosEstacionados;
    }

    public int estacionarVeiculo(Veiculo veiculo) throws Exception {
        int vaga = -1;
        for (int i = 0; i < veiculosEstacionados.size(); i++) {
            if (veiculosEstacionados.get(i) != null) {
                if (Objects.equals(veiculosEstacionados.get(i).placa, veiculo.placa)) {
                    throw new VeiculoJaEstacionado("O veiculo de placa " + veiculo.placa + " já está estacionado.");
                }
            }

            if (veiculosEstacionados.get(i) == null) {
                vaga = i;
                break;
            }
        }

        if (vaga == -1) {
            throw new EstacionamentoCheio("O estacionamento está cheio.");
        }

        veiculosEstacionados.set(vaga, veiculo);
        registro_geral.add(veiculo);

        return vaga + 1;
    }

    public Veiculo tirarVeiculo(String placa) throws VeiculoNaoEncontrado {
        int vagaLiberada = -1;
        for (int i = 0; i < veiculosEstacionados.size(); i++) {
            if (veiculosEstacionados.get(i) != null) {
                if (Objects.equals(veiculosEstacionados.get(i).placa, placa)) {
                    vagaLiberada = i;
                    break;
                }
            }
        }

        if (vagaLiberada == -1) {
            throw new VeiculoNaoEncontrado("O veículo não está estacionado.");
        }

        Veiculo veiculoRemovido = veiculosEstacionados.get(vagaLiberada);
        veiculoRemovido.setSaida(LocalDateTime.now());
        veiculosEstacionados.set(vagaLiberada, null);

        return veiculoRemovido;
    }

    public void ImprimirRegistros() {

        for (Veiculo veiculo : registro_geral) {
            veiculo.getCliente().imprimir();
            veiculo.imprimir();
            System.out.println("\tINFORMAÇÕES");
            System.out.println("\tHorario de Entrada: " + veiculo.getEntradaFormatada() + "h");
            if (veiculo.getSaida() == null) {
                System.out.println("Horario de Saida: Veiculo Ativo no Estacionamento!");
            } else {
                System.out.println("\tHorário de saída:" + veiculo.getSaidaFormatada() + "h");
                System.out.println("\tPermanência total: " + veiculo.getPermanencia() + "h");
                System.out.println("\tPagamento: R$" + veiculo.getPagamento());
            }
            System.out.println("----------------------------------------------");
        }

    }
    
    public void Mostra_Estacionamento(){
        
        for (int i = 0; i < veiculosEstacionados.size(); i++) {
            System.out.println("-----------");
            if (veiculosEstacionados.get(i) != null) {
                System.out.println("///////////");
                System.out.println(i+1 + "//OCUPADO/");
                System.out.println("///////////");
                
            }

            if (veiculosEstacionados.get(i) == null) {
                System.out.println("///////////");
                System.out.println(i+1 + "//LIVRE///");
                System.out.println("///////////");
            }
        }
    }

    public void GerarPDF() {
        String dest = "C:\\Users\\moise\\Desktop\\Nova pasta (2)\\estacionamento-erp\\Auxiliar\\example.pdf";
        Scanner scanner = new Scanner(System.in);

        try {
            PdfWriter writer = new PdfWriter(dest);

            PdfDocument pdf = new PdfDocument(writer);

            Document document = new Document(pdf);

            document.setTextAlignment(TextAlignment.CENTER);
            document.add(new Paragraph("RELATÓRIO"));;

            System.out.println("Qual o Relatorio deseja gerar? Digite 1 para Apenas Registros Ativos e 2 para Todos os Regitros: ");
            String opcao_relatorio = scanner.nextLine();

            while (!Arrays.asList(new String[]{"1", "2"}).contains(opcao_relatorio)) {
                System.out.print("Opcao inválida.\nDigite 1 para Apenas Registros Ativos e 2 para Todos os Registros: ");
                opcao_relatorio = scanner.nextLine();
            }

            if ("1".equals(opcao_relatorio)) {
                document.add(new Paragraph("TODOS OS VEICULOS ATIVOS")).setTextAlignment(TextAlignment.LEFT);

                for (Veiculo veiculo : veiculosEstacionados) {
                    if (veiculo != null) {
                        document.add(new Paragraph("                                                                                                                                                ")).setFontSize(10).setMargins(0, 0, 0, 0);;
                        document.add(new Paragraph("CLIENTE")).setFontSize(8).setMargins(0, 0, 0, 0);

                        document.add(new Paragraph("NOME: " + veiculo.getCliente().getNome() + "     DOCUMENTO: " + veiculo.getCliente().getCpf())).setFontSize(10).setMargins(0, 0, 0, 0);
                        document.add(new Paragraph("VEICULO")).setFontSize(8).setMargins(0, 0, 0, 0);

                        document.add(new Paragraph("PLACA: " + veiculo.getPlaca() + "           TIPO: " + veiculo.getClass().getSimpleName())).setFontSize(8).setMargins(0, 0, 0, 0);
                        document.add(new Paragraph("COR: " + veiculo.getCor())).setFontSize(8).setMargins(0, 0, 0, 0);

                        document.add(new Paragraph("DATA DE ENTRADA: " + veiculo.getEntradaFormatada() + "h")).setFontSize(8);

                        document.add(new Paragraph("DATA DE SAIDA: VEICULO AINDA ESTACIONADO")).setFontSize(8);
                        document.add(new Paragraph("                                                                                                                                                "));
                        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"));

                    }

                }

            } else if ("2".equals(opcao_relatorio)) {
                document.setTextAlignment(TextAlignment.CENTER);
                document.add(new Paragraph("TODOS OS VEICULOS")).setTextAlignment(TextAlignment.LEFT);;

                for (Veiculo veiculo : registro_geral) {
                    if (veiculo != null) {
                        document.add(new Paragraph("                                                                                                                                                ")).setFontSize(10).setMargins(0, 0, 0, 0);
                        document.add(new Paragraph("CLIENTE")).setFontSize(8).setMargins(0, 0, 0, 0);

                        document.add(new Paragraph("NOME: " + veiculo.getCliente().getNome() + "     DOCUMENTO: " + veiculo.getCliente().getCpf())).setFontSize(10).setMargins(0, 0, 0, 0);
                        document.add(new Paragraph("VEICULO")).setFontSize(8).setMargins(0, 0, 0, 0);

                        document.add(new Paragraph("PLACA: " + veiculo.getPlaca() + "           TIPO: " + veiculo.getClass().getSimpleName())).setFontSize(8).setMargins(0, 0, 0, 0);
                        document.add(new Paragraph("COR: " + veiculo.getCor())).setFontSize(8).setMargins(0, 0, 0, 0);

                        if (veiculo.getSaida() == null) {
                            document.add(new Paragraph("DATA DE ENTRADA: " + veiculo.getEntradaFormatada() + "h")).setFontSize(8);
                            document.add(new Paragraph("DATA DE SAIDA: VEICULO AINDA ESTACIONADO")).setFontSize(8);
                        } else {
                            document.add(new Paragraph("DATA DE ENTRADA: " + veiculo.getEntradaFormatada() + "h")).setFontSize(8);
                            document.add(new Paragraph("DATA DE SAIDA: " + veiculo.getSaidaFormatada() + "h")).setFontSize(8);
                            document.add(new Paragraph("TAXA: " + veiculo.getPagamento())).setFontSize(8);
                            document.add(new Paragraph("PERMANENCIA: " + veiculo.getPermanencia() + "h")).setFontSize(8);

                            document.add(new Paragraph("                                                                                                                                                "));

                        }
                        document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"));
                    }

                }
            }

            document.close();

            System.out.println("PDF criado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
