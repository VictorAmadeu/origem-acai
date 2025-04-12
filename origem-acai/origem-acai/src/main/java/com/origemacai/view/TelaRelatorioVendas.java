package com.origemacai.view;
// Define o pacote onde está localizada a classe. Isso é importante para a organização do projeto (padrão MVC).

import com.origemacai.model.Venda;
// Importa a classe Venda, que representa os dados de cada venda.

import com.origemacai.service.VendaService;
// Importa o serviço que fornece as vendas a serem exibidas.

import com.origemacai.util.PdfRelatorioUtil;
// Importa a classe utilitária que gera o PDF com o relatório de vendas.

import javafx.collections.FXCollections;
// Importa utilitários para converter listas Java em listas observáveis (usadas nas tabelas JavaFX).

import javafx.scene.Scene;
// Importa a classe que representa a janela de conteúdo do JavaFX.

import javafx.scene.control.*;
// Importa todos os controles (tabela, colunas, botões, alertas, etc.) de JavaFX.

import javafx.scene.control.cell.PropertyValueFactory;
// Importa a classe que facilita o preenchimento de colunas com valores de propriedades dos objetos.

import javafx.scene.layout.VBox;
// Importa o layout vertical VBox, usado para organizar os componentes na tela.

import javafx.stage.Stage;
// Importa a classe que representa uma nova janela (tela) no JavaFX.

import java.util.List;
// Importa a classe List para lidar com listas de objetos.

public class TelaRelatorioVendas {
    // Define a classe pública responsável pela visualização do relatório de vendas.

    private VendaService vendaService;
    // Declara a variável que armazenará a instância do serviço de vendas.

    public TelaRelatorioVendas(VendaService vendaService) {
        // Construtor da classe, que recebe o serviço como parâmetro.
        this.vendaService = vendaService;
        // Armazena o serviço para uso posterior na tela.
    }

    public void start() {
        // Método que inicia a construção e exibição da tela de relatório.

        Stage stage = new Stage();
        // Cria uma nova janela (tela) para o relatório.

        stage.setTitle("Relatório de Vendas");
        // Define o título da janela.

        TableView<Venda> tabela = new TableView<>();
        // Cria uma nova tabela que exibirá os dados das vendas.

        TableColumn<Venda, Long> colId = new TableColumn<>("ID");
        // Cria a coluna "ID" do tipo Long.

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        // Diz à coluna para usar o valor da propriedade "id" de cada objeto Venda.

        TableColumn<Venda, String> colProduto = new TableColumn<>("Produto");
        // Cria a coluna "Produto" do tipo String.

        colProduto.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProduto().getNome()));
        // Usa expressão lambda para acessar o nome do produto dentro da entidade Venda.

        TableColumn<Venda, Integer> colQtd = new TableColumn<>("Quantidade");
        // Cria a coluna "Quantidade" do tipo Integer.

        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        // Usa o campo "quantidade" diretamente da entidade Venda.

        TableColumn<Venda, String> colData = new TableColumn<>("Data");
        // Cria a coluna "Data" para exibir a data da venda.

        colData.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDataHora().toString()));
        // Usa expressão lambda para converter a dataHora (LocalDateTime) em texto.

        tabela.getColumns().addAll(colId, colProduto, colQtd, colData);
        // Adiciona todas as colunas criadas à tabela.

        List<Venda> vendas = vendaService.listarTodas();
        // Chama o método do serviço para obter todas as vendas cadastradas.

        tabela.setItems(FXCollections.observableArrayList(vendas));
        // Converte a lista em uma ObservableList e define como conteúdo da tabela.

        // -------------------------------------------
        // IMPLEMENTAÇÃO DO BOTÃO DE EXPORTAÇÃO PARA PDF
        // -------------------------------------------

        Button btnExportarPDF = new Button("Exportar para PDF");
        // Cria o botão com o texto "Exportar para PDF".

        btnExportarPDF.setOnAction(e -> {
            // Define a ação que será executada ao clicar no botão.
            try {
                PdfRelatorioUtil.gerarRelatorioVendas(vendas, "relatorio-vendas.pdf");
                // Chama o método utilitário para gerar o PDF passando a lista de vendas e o nome do arquivo.

                Alert alertaSucesso = new Alert(Alert.AlertType.INFORMATION);
                // Cria um alerta de informação para feedback positivo.

                alertaSucesso.setTitle("Exportação");
                alertaSucesso.setHeaderText(null);
                alertaSucesso.setContentText("PDF gerado com sucesso!");
                alertaSucesso.showAndWait();
                // Exibe o alerta.

            } catch (Exception ex) {
                // Caso aconteça algum erro ao gerar o PDF:
                Alert alertaErro = new Alert(Alert.AlertType.ERROR);
                // Cria um alerta de erro.

                alertaErro.setTitle("Erro");
                alertaErro.setHeaderText(null);
                alertaErro.setContentText("Erro ao gerar PDF: " + ex.getMessage());
                alertaErro.showAndWait();
                // Exibe o alerta com a mensagem de erro.
            }
        });

        VBox layout = new VBox(10);
        // Cria um layout vertical com espaçamento de 10 pixels entre os elementos.

        layout.getChildren().addAll(tabela, btnExportarPDF);
        // Adiciona a tabela e o botão ao layout VBox.

        Scene cena = new Scene(layout, 600, 400);
        // Cria a cena JavaFX com o layout e define o tamanho da janela.

        stage.setScene(cena);
        // Define a cena na janela (Stage).

        stage.show();
        // Exibe a janela.
    }
}

