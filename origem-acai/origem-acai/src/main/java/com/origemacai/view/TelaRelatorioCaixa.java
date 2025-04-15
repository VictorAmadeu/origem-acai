// Pacote onde a classe está localizada
package com.origemacai.view;

// Importa a entidade Caixa que contém os dados de cada movimentação (entrada/saída)
import com.origemacai.model.Caixa;

// Importa o serviço que fornece a lista de movimentações do caixa
import com.origemacai.service.CaixaService;

// Importa utilitário que exporta os dados em formato PDF
import com.origemacai.util.PdfRelatorioUtil;

// Importações necessárias do JavaFX para a interface gráfica
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Importa lista do Java para armazenar os dados do relatório
import java.util.List;

// Define a classe da tela de relatório de caixa como uma aplicação JavaFX
public class TelaRelatorioCaixa extends Application {

    // Atributo para acessar os dados de movimentação do caixa
    private CaixaService caixaService;

    // Construtor padrão obrigatório para JavaFX e para instanciar via Dashboard
    public TelaRelatorioCaixa() {
        // Cria uma nova instância do serviço
        this.caixaService = new CaixaService();
    }

    // Método obrigatório do JavaFX chamado ao abrir esta tela
    @Override
    public void start(Stage primaryStage) {
        // Define o título da janela
        primaryStage.setTitle("Relatório de Caixa");

        // Cria uma tabela para exibir os dados
        TableView<Caixa> tabela = new TableView<>();

        // Cria a coluna "Tipo" e vincula ao campo "tipo" da entidade Caixa
        TableColumn<Caixa, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        // Cria a coluna "Descrição" e vincula ao campo "descricao"
        TableColumn<Caixa, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        // Cria a coluna "Valor" e formata o valor com "R$"
        TableColumn<Caixa, String> colValor = new TableColumn<>("Valor");
        colValor.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty("R$ " + cellData.getValue().getValor().toString())
        );

        // Cria a coluna "Data" com o horário da movimentação
        TableColumn<Caixa, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDataHora().toString())
        );

        // Adiciona todas as colunas à tabela
        tabela.getColumns().addAll(colTipo, colDesc, colValor, colData);

        // Carrega os dados do serviço e insere na tabela
        List<Caixa> movimentos = caixaService.getTodosMovimentos();
        tabela.setItems(FXCollections.observableArrayList(movimentos));

        // Cria botão para exportar os dados da tabela para PDF
        Button btnExportarPDF = new Button("Exportar para PDF");

        // Define ação ao clicar no botão de exportação
        btnExportarPDF.setOnAction(e -> {
            try {
                // Busca novamente os dados atualizados
                List<Caixa> lista = caixaService.getTodosMovimentos();

                // Chama utilitário que gera o PDF
                PdfRelatorioUtil.gerarRelatorioCaixa(lista, "relatorio-caixa.pdf");

                // Alerta de sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportação");
                alert.setHeaderText(null);
                alert.setContentText("PDF gerado com sucesso!");
                alert.showAndWait();

            } catch (Exception ex) {
                // Em caso de erro, mostra mensagem ao usuário
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro ao gerar PDF: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        // Layout vertical com espaçamento entre elementos
        VBox layout = new VBox(10); // 10px de espaçamento entre os itens
        layout.getChildren().addAll(tabela, btnExportarPDF); // Adiciona a tabela e o botão no layout

        // Cria a cena da janela com largura 600px e altura 400px
        Scene scene = new Scene(layout, 600, 400);

        // Define a cena no palco (janela)
        primaryStage.setScene(scene);

        // Exibe a janela na tela
        primaryStage.show();
    }
}

