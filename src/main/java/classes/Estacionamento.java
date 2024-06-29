package classes;

import excessoes.VeiculoJaEstacionado;
import excessoes.EstacionamentoCheio;
import excessoes.VeiculoNaoEncontrado;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Estacionamento {

    private final List<Veiculo> veiculosEstacionados = Arrays.asList(new Veiculo[8]);
    private final List<Veiculo> registros = new ArrayList<>();

    public List<Veiculo> getVeiculosEstacionados() {
        return veiculosEstacionados;
    }

    public int estacionarVeiculo(Veiculo veiculo) throws Exception {
        int vaga = -1;
        for (int i = 0; i < veiculosEstacionados.size(); i++) {
            if (veiculosEstacionados.get(i) != null) {
                if (Objects.equals(veiculosEstacionados.get(i).getPlaca(), veiculo.getPlaca())) {
                    throw new VeiculoJaEstacionado("O veiculo de placa " + veiculo.getPlaca() + " já está estacionado.");
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
        registros.add(veiculo);

        return vaga + 1;
    }

    public Veiculo retirarVeiculo(String placa) throws VeiculoNaoEncontrado {
        int vagaLiberada = -1;
        for (int i = 0; i < veiculosEstacionados.size(); i++) {
            if (veiculosEstacionados.get(i) != null) {
                if (Objects.equals(veiculosEstacionados.get(i).getPlaca(), placa.toUpperCase())) {
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

    public String printarRegistrosDia(int dia) {
        StringBuilder sb = new StringBuilder();

        for (Veiculo veiculo : registros) {
            if (veiculo.getEntrada().getDayOfMonth() == dia) {
                sb.append(veiculo.getCliente().toString()).append("\n");
                sb.append(veiculo.toString()).append("\n");
                sb.append("INFORMAÇÕES").append("\n");
                sb.append("\tHorario de Entrada: ").append(veiculo.getEntradaFormatada()).append("h").append("\n");
                if (veiculo.getSaida() == null) {
                    sb.append("\tHorario de Saida: Veiculo estacionado").append("\n");
                } else {
                    sb.append("\tHorário de saída:").append(veiculo.getSaidaFormatada()).append("h").append("\n");
                    sb.append("\tPermanência total: ").append(veiculo.getPermanencia()).append("h").append("\n");
                    sb.append("\tPagamento: R$").append(veiculo.getPagamento()).append("\n");
                }
                sb.append("----------------------------------------------").append("\n");
            }
        }

        return sb.toString();
    }

    public void GerarPDF(String opcaoRelatorio) throws FileNotFoundException {
        String caminho = "./relatorios/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss"))
                + "__relatorio-" + (opcaoRelatorio.equals("1") ? "ativos" : "todos") + ".pdf";

        new File("./relatorios").mkdirs();

        PdfWriter writer = new PdfWriter(caminho);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setTextAlignment(TextAlignment.CENTER);
        document.add(new Paragraph("RELATÓRIO"));

        if ("1".equals(opcaoRelatorio)) {
            document.add(new Paragraph("TODOS OS VEICULOS ATIVOS")).setTextAlignment(TextAlignment.LEFT);

            for (Veiculo veiculo : veiculosEstacionados) {
                if (veiculo != null) {
                    document.add(new Paragraph("                                                                                                                                                ")).setFontSize(10).setMargins(0, 0, 0, 0);

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

        } else if ("2".equals(opcaoRelatorio)) {
            document.setTextAlignment(TextAlignment.CENTER);
            document.add(new Paragraph("TODOS OS VEICULOS")).setTextAlignment(TextAlignment.LEFT);

            for (Veiculo veiculo : registros) {
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
    }
}
