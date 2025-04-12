package com.origemacai.util;

// Importações necessárias para criação e manipulação de PDF
import com.origemacai.model.Venda;
import com.origemacai.model.Produto;
import com.origemacai.model.Caixa;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Classe utilitária responsável por gerar relatórios em PDF.
 * Contém métodos reutilizáveis para vendas, estoque e caixa.
 */
public class PdfRelatorioUtil {

    /**
     * Gera um relatório de vendas em PDF.
     *
     * @param vendas Lista de vendas a serem incluídas no relatório
     * @param caminhoDestino Caminho onde o arquivo PDF será salvo
     * @throws IOException Caso ocorra erro de escrita
     */
    public static void gerarRelatorioVendas(List<Venda> vendas, String caminhoDestino) throws IOException {
        PDDocument documento = new PDDocument(); // Cria o documento PDF
        PDPage pagina = new PDPage(PDRectangle.A4); // Define o tamanho da página
        documento.addPage(pagina); // Adiciona a página ao documento

        PDPageContentStream conteudo = new PDPageContentStream(documento, pagina); // Stream para escrever no PDF

        // Cabeçalho do relatório
        conteudo.setFont(PDType1Font.HELVETICA_BOLD, 14);
        conteudo.beginText();
        conteudo.newLineAtOffset(50, 750);
        conteudo.showText("Relatório de Vendas - Origem Açaí");
        conteudo.endText();

        // Conteúdo da lista
        conteudo.setFont(PDType1Font.HELVETICA, 12);
        int y = 720;

        for (Venda venda : vendas) {
            // Se a página estiver cheia, cria uma nova
            if (y < 100) {
                conteudo.close();
                pagina = new PDPage();
                documento.addPage(pagina);
                conteudo = new PDPageContentStream(documento, pagina);
                conteudo.setFont(PDType1Font.HELVETICA, 12);
                y = 750;
            }

            conteudo.beginText();
            conteudo.newLineAtOffset(50, y);
            conteudo.showText("ID: " + venda.getId()
                    + " | Produto: " + venda.getProduto().getNome()
                    + " | Qtd: " + venda.getQuantidade()
                    + " | Total: R$ " + venda.getTotal());
            conteudo.endText();
            y -= 20;
        }

        conteudo.close(); // Fecha o stream de escrita
        documento.save(new File(caminhoDestino)); // Salva o PDF no caminho desejado
        documento.close(); // Fecha o documento
    }

    /**
     * Gera um relatório de estoque em PDF.
     *
     * @param produtos Lista de produtos com dados de estoque
     * @param caminhoDestino Caminho onde o arquivo PDF será salvo
     * @throws IOException Caso ocorra erro de escrita
     */
    public static void gerarRelatorioEstoque(List<Produto> produtos, String caminhoDestino) throws IOException {
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage(PDRectangle.A4);
        documento.addPage(pagina);

        PDPageContentStream conteudo = new PDPageContentStream(documento, pagina);

        // Título do relatório
        conteudo.setFont(PDType1Font.HELVETICA_BOLD, 14);
        conteudo.beginText();
        conteudo.newLineAtOffset(50, 750);
        conteudo.showText("Relatório de Estoque - Origem Açaí");
        conteudo.endText();

        conteudo.setFont(PDType1Font.HELVETICA, 12);
        int y = 720;

        for (Produto produto : produtos) {
            if (y < 100) {
                conteudo.close();
                pagina = new PDPage();
                documento.addPage(pagina);
                conteudo = new PDPageContentStream(documento, pagina);
                conteudo.setFont(PDType1Font.HELVETICA, 12);
                y = 750;
            }

            conteudo.beginText();
            conteudo.newLineAtOffset(50, y);
            conteudo.showText("ID: " + produto.getId()
                    + " | Nome: " + produto.getNome()
                    + " | Tipo: " + produto.getTipo()
                    + " | Preço: R$ " + produto.getPreco()
                    + " | Estoque: " + produto.getQuantidadeEstoque());
            conteudo.endText();
            y -= 20;
        }

        conteudo.close();
        documento.save(new File(caminhoDestino));
        documento.close();
    }

    /**
     * Gera um relatório de caixa com entradas, saídas e saldo final.
     *
     * @param movimentos Lista de movimentações do caixa
     * @param caminhoDestino Caminho onde o PDF será salvo
     * @throws IOException Caso ocorra erro de escrita
     */
    public static void gerarRelatorioCaixa(List<Caixa> movimentos, String caminhoDestino) throws IOException {
        PDDocument documento = new PDDocument();
        PDPage pagina = new PDPage(PDRectangle.A4);
        documento.addPage(pagina);

        PDPageContentStream conteudo = new PDPageContentStream(documento, pagina);

        conteudo.setFont(PDType1Font.HELVETICA_BOLD, 14);
        conteudo.beginText();
        conteudo.newLineAtOffset(50, 750);
        conteudo.showText("Relatório de Caixa - Origem Açaí");
        conteudo.endText();

        conteudo.setFont(PDType1Font.HELVETICA, 12);
        int y = 720;

        BigDecimal totalEntradas = BigDecimal.ZERO;
        BigDecimal totalSaidas = BigDecimal.ZERO;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Caixa c : movimentos) {
            if (y < 100) {
                conteudo.close();
                pagina = new PDPage();
                documento.addPage(pagina);
                conteudo = new PDPageContentStream(documento, pagina);
                conteudo.setFont(PDType1Font.HELVETICA, 12);
                y = 750;
            }

            String linha = String.format("[%s] %s | R$ %.2f | %s",
                    c.getTipo(), c.getDescricao(), c.getValor(), c.getDataHora().format(formatter));

            if (c.getTipo().equals("ENTRADA")) {
                totalEntradas = totalEntradas.add(c.getValor());
            } else {
                totalSaidas = totalSaidas.add(c.getValor());
            }

            conteudo.beginText();
            conteudo.newLineAtOffset(50, y);
            conteudo.showText(linha);
            conteudo.endText();
            y -= 20;
        }

        y -= 20;
        conteudo.beginText(); conteudo.newLineAtOffset(50, y);
        conteudo.showText("Total de Entradas: R$ " + totalEntradas); conteudo.endText();
        y -= 20;
        conteudo.beginText(); conteudo.newLineAtOffset(50, y);
        conteudo.showText("Total de Saídas: R$ " + totalSaidas); conteudo.endText();
        y -= 20;
        conteudo.beginText(); conteudo.newLineAtOffset(50, y);
        conteudo.showText("Saldo Atual: R$ " + totalEntradas.subtract(totalSaidas)); conteudo.endText();

        conteudo.close();
        documento.save(new File(caminhoDestino));
        documento.close();
    }
}
