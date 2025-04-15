package com.origemacai.view; // Define o pacote onde a classe está localizada, seguindo a estrutura MVC

// Importações das classes necessárias
import com.origemacai.model.Produto; // Classe que representa o produto
import com.origemacai.service.ProdutoService; // Serviço responsável por acessar os dados dos produtos
import com.origemacai.util.PdfRelatorioUtil; // Classe utilitária que gera o PDF

import javafx.application.Application; // Necessário para usar JavaFX com start(Stage)
import javafx.collections.FXCollections; // Utilizado para transformar lista comum em observable list
import javafx.scene.Scene; // Representa a tela JavaFX
import javafx.scene.control.*; // Importa todos os componentes de controle (Label, TableView, Button, etc.)
import javafx.scene.control.cell.PropertyValueFactory; // Permite vincular colunas da tabela com atributos da entidade
import javafx.scene.layout.VBox; // Layout vertical
import javafx.stage.Stage; // Janela principal

import java.io.IOException; // Necessário para capturar exceções de IO (como erro ao gerar PDF)
import java.util.List; // Importa a interface de lista para lidar com a lista de produtos

public class TelaRelatorioEstoque extends Application { // A classe herda Application para ser compatível com JavaFX

    private ProdutoService produtoService; // Variável de instância que acessa os dados do produto

    // Construtor sem parâmetros (obrigatório para ser chamado pela Dashboard com new TelaRelatorioEstoque())
    public TelaRelatorioEstoque() {
        this.produtoService = new ProdutoService(); // Inicializa o serviço internamente
    }

    // Método obrigatório do JavaFX, chamado ao abrir a tela
    @Override
    public void start(Stage primaryStage) {
        // Define o título da janela
        primaryStage.setTitle("Relatório de Estoque");

        // Cria uma tabela visual para exibir os produtos
        TableView<Produto> tabela = new TableView<>();

        // Coluna para ID do produto
        TableColumn<Produto, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id")); // Mapeia para o campo "id" da classe Produto

        // Coluna para Nome do produto
        TableColumn<Produto, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome")); // Mapeia para o campo "nome"

        // Coluna para Tipo do produto
        TableColumn<Produto, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo")); // Mapeia para o campo "tipo"

        // Coluna para Quantidade em Estoque
        TableColumn<Produto, Integer> colEstoque = new TableColumn<>("Estoque");
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque")); // Mapeia para o campo "quantidadeEstoque"

        // Adiciona todas as colunas na tabela
        tabela.getColumns().addAll(colId, colNome, colTipo, colEstoque);

        // Busca todos os produtos cadastrados através do serviço
        List<Produto> produtos = produtoService.listarTodos();

        // Transforma a lista em uma lista observável para ser usada na tabela
        tabela.setItems(FXCollections.observableArrayList(produtos));

        // Cria o botão que exporta os dados para um arquivo PDF
        Button btnExportarPDF = new Button("Exportar para PDF");

        // Define o que acontece quando o botão for clicado
        btnExportarPDF.setOnAction(e -> {
            try {
                // Chama a função utilitária para gerar o relatório
                PdfRelatorioUtil.gerarRelatorioEstoque(produtos, "relatorio-estoque.pdf");

                // Cria um alerta de sucesso após gerar o PDF
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportação");
                alert.setHeaderText(null);
                alert.setContentText("PDF gerado com sucesso!");
                alert.showAndWait(); // Exibe o alerta

            } catch (IOException ex) {
                // Cria alerta de erro caso algo dê errado ao gerar PDF
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro ao gerar PDF: " + ex.getMessage());
                alert.showAndWait(); // Exibe o erro
            }
        });

        // Cria um layout vertical com espaçamento de 10px
        VBox layout = new VBox(10);
        layout.getChildren().addAll(tabela, btnExportarPDF); // Adiciona tabela e botão ao layout

        // Cria a cena com largura 600px e altura 450px
        Scene scene = new Scene(layout, 600, 450);

        // Define a cena na janela principal
        primaryStage.setScene(scene);

        // Exibe a janela para o usuário
        primaryStage.show();
    }
}
