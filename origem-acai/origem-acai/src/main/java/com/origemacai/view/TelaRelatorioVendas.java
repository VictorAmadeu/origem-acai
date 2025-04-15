package com.origemacai.view; 
// Define o pacote onde esta classe está localizada no projeto (padrão MVC).

// Importações de modelos, serviços e utilitários
import com.origemacai.model.Venda; // Classe que representa os dados de uma venda.
import com.origemacai.service.VendaService; // Serviço que fornece a lista de vendas.
import com.origemacai.util.PdfRelatorioUtil; // Utilitário que gera o relatório PDF.

// Importações JavaFX para interface gráfica
import javafx.application.Application; // Torna a classe uma aplicação JavaFX.
import javafx.collections.FXCollections; // Usado para criar ObservableList a partir de List padrão.
import javafx.scene.Scene; // Representa a tela do JavaFX.
import javafx.scene.control.*; // Importa todos os controles (botões, tabelas, colunas, etc.).
import javafx.scene.control.cell.PropertyValueFactory; // Facilita a associação de colunas com atributos.
import javafx.scene.layout.VBox; // Layout vertical.
import javafx.stage.Stage; // Janela (tela) principal.

import java.util.List; // Permite trabalhar com listas de objetos.

public class TelaRelatorioVendas extends Application {
    // Classe pública que representa a tela de relatório de vendas, agora estendendo Application para funcionar com JavaFX.

    private VendaService vendaService; 
    // Serviço responsável por fornecer os dados de vendas. Será inicializado dentro do construtor.

    public TelaRelatorioVendas() {
        // Construtor sem parâmetros, necessário para funcionar com JavaFX e com new TelaRelatorioVendas().
        this.vendaService = new VendaService(); 
        // Instancia o serviço manualmente (sem injeção de dependência).
    }

    @Override
    public void start(Stage primaryStage) {
        // Método obrigatório do JavaFX que constrói e exibe a interface da tela.

        primaryStage.setTitle("Relatório de Vendas");
        // Define o título da janela (exibido na barra superior).

        TableView<Venda> tabela = new TableView<>();
        // Cria a tabela que exibirá os dados de vendas.

        // Cria a coluna ID e associa com a propriedade "id" da entidade Venda
        TableColumn<Venda, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Cria a coluna "Produto" que mostra o nome do produto relacionado à venda
        TableColumn<Venda, String> colProduto = new TableColumn<>("Produto");
        colProduto.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getProduto().getNome()
            )
        );
        // Expressão lambda que pega o nome do produto de dentro do objeto Venda.

        // Cria a coluna "Quantidade" e associa com o atributo da venda
        TableColumn<Venda, Integer> colQtd = new TableColumn<>("Quantidade");
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        // Cria a coluna "Data" e formata a data/hora como texto
        TableColumn<Venda, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getDataHora().toString()
            )
        );

        // Adiciona todas as colunas à tabela
        tabela.getColumns().addAll(colId, colProduto, colQtd, colData);

        // Obtém a lista de vendas do serviço
        List<Venda> vendas = vendaService.listarTodas();

        // Converte a lista normal para uma ObservableList e define como conteúdo da tabela
        tabela.setItems(FXCollections.observableArrayList(vendas));

        // ----------------------------
        // Botão de Exportação para PDF
        // ----------------------------

        Button btnExportarPDF = new Button("Exportar para PDF");
        // Cria o botão que permitirá exportar os dados da tabela.

        btnExportarPDF.setOnAction(e -> {
            // Define a ação que ocorre ao clicar no botão
            try {
                PdfRelatorioUtil.gerarRelatorioVendas(vendas, "relatorio-vendas.pdf");
                // Gera o arquivo PDF com os dados da tabela, usando a classe utilitária

                Alert alertaSucesso = new Alert(Alert.AlertType.INFORMATION);
                alertaSucesso.setTitle("Exportação");
                alertaSucesso.setHeaderText(null);
                alertaSucesso.setContentText("PDF gerado com sucesso!");
                alertaSucesso.showAndWait();
                // Exibe um alerta de sucesso para o usuário

            } catch (Exception ex) {
                // Caso ocorra erro na exportação
                Alert alertaErro = new Alert(Alert.AlertType.ERROR);
                alertaErro.setTitle("Erro");
                alertaErro.setHeaderText(null);
                alertaErro.setContentText("Erro ao gerar PDF: " + ex.getMessage());
                alertaErro.showAndWait();
                // Exibe alerta de erro com a mensagem do problema
            }
        });

        // Layout vertical para organizar a tabela e o botão
        VBox layout = new VBox(10); // Espaçamento de 10px entre os elementos
        layout.getChildren().addAll(tabela, btnExportarPDF); // Adiciona tabela e botão ao layout

        // Cria a cena (área visível da tela) e define tamanho da janela
        Scene scene = new Scene(layout, 600, 400);

        // Aplica a cena à janela principal
        primaryStage.setScene(scene);

        // Exibe a janela
        primaryStage.show();
    }
}


