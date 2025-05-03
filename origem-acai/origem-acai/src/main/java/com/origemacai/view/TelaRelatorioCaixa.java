package com.origemacai.view;

import com.origemacai.model.Caixa;
import com.origemacai.service.CaixaService;
import com.origemacai.util.PdfRelatorioUtil;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class TelaRelatorioCaixa extends Application {

    private CaixaService caixaService;

    public void setCaixaService(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Relatório de Caixa");

        TableView<Caixa> tabela = new TableView<>();

        TableColumn<Caixa, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Caixa, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Caixa, String> colValor = new TableColumn<>("Valor");
        colValor.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty("R$ " + cellData.getValue().getValor().toString())
        );

        TableColumn<Caixa, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDataHora().toString())
        );

        tabela.getColumns().addAll(colTipo, colDesc, colValor, colData);

        List<Caixa> movimentos = caixaService.getTodosMovimentos();
        tabela.setItems(FXCollections.observableArrayList(movimentos));

        Button btnExportarPDF = new Button("Exportar para PDF");

        btnExportarPDF.setOnAction(e -> {
            try {
                List<Caixa> lista = caixaService.getTodosMovimentos();
                PdfRelatorioUtil.gerarRelatorioCaixa(lista, "relatorio-caixa.pdf");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportação");
                alert.setHeaderText(null);
                alert.setContentText("PDF gerado com sucesso!");
                alert.showAndWait();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro ao gerar PDF: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(tabela, btnExportarPDF);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

