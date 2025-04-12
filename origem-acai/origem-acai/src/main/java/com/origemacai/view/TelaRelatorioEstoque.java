package com.origemacai.view; // Define o pacote onde a classe está localizada

// Importações de classes que serão utilizadas
import com.origemacai.model.Produto;
import com.origemacai.service.ProdutoService;
import com.origemacai.util.PdfRelatorioUtil; // Importa a classe utilitária para geração do PDF
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox; // Adiciona VBox para acomodar a tabela e o botão
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TelaRelatorioEstoque { // Declaração da classe pública

    private ProdutoService produtoService; // Instância do serviço de produtos

    // Construtor que recebe o serviço e armazena para uso interno
    public TelaRelatorioEstoque(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // Método que exibe a interface da tela de relatório de estoque
    public void start() {
        Stage stage = new Stage(); // Cria uma nova janela
        stage.setTitle("Relatório de Estoque"); // Define o título da janela

        TableView<Produto> tabela = new TableView<>(); // Cria a tabela onde os dados serão exibidos

        // Cria e configura a coluna de ID
        TableColumn<Produto, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id")); // Associa o campo "id" da entidade Produto

        // Cria e configura a coluna de Nome
        TableColumn<Produto, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome")); // Associa o campo "nome"

        // Cria e configura a coluna de Tipo
        TableColumn<Produto, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo")); // Associa o campo "tipo"

        // Cria e configura a coluna de Quantidade em Estoque
        TableColumn<Produto, Integer> colEstoque = new TableColumn<>("Estoque");
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque")); // Associa o campo "quantidadeEstoque"

        // Adiciona todas as colunas à tabela
        tabela.getColumns().addAll(colId, colNome, colTipo, colEstoque);

        // Obtém a lista de produtos através do serviço
        List<Produto> produtos = produtoService.listarTodos();

        // Converte a lista para um formato observável e coloca na tabela
        tabela.setItems(FXCollections.observableArrayList(produtos));

        // Cria o botão de exportação para PDF
        Button btnExportarPDF = new Button("Exportar para PDF");

        // Define a ação quando o botão for clicado
        btnExportarPDF.setOnAction(e -> {
            try {
                // Chama o método que gera o PDF, passando os produtos e o caminho do arquivo
                PdfRelatorioUtil.gerarRelatorioEstoque(produtos, "relatorio-estoque.pdf");

                // Exibe alerta de sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportação");
                alert.setHeaderText(null);
                alert.setContentText("PDF gerado com sucesso!");
                alert.showAndWait();

            } catch (IOException ex) {
                // Exibe alerta de erro caso algo dê errado
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro ao gerar PDF: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        // Layout vertical para conter a tabela e o botão
        VBox layout = new VBox(10); // Espaçamento vertical de 10px
        layout.getChildren().addAll(tabela, btnExportarPDF); // Adiciona tabela e botão ao layout

        // Cria a cena com o layout e define tamanho da janela
        Scene scene = new Scene(layout, 600, 450);
        stage.setScene(scene); // Define a cena na janela
        stage.show(); // Exibe a janela na tela
    }
}
