package com.origemacai.view;

import com.origemacai.model.Venda;
import com.origemacai.service.VendaService;
import com.origemacai.util.PdfRelatorioUtil;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class TelaRelatorioVendas extends Application {

    private VendaService vendaService;

    public void setVendaService(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Relatório de Vendas");

        TableView<Venda> tabela = new TableView<>();

        TableColumn<Venda, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Venda, String> colProduto = new TableColumn<>("Produto");
        colProduto.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getProduto().getNome()
            )
        );

        TableColumn<Venda, Integer> colQtd = new TableColumn<>("Quantidade");
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        TableColumn<Venda, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getDataHora().toString()
            )
        );

        tabela.getColumns().addAll(colId, colProduto, colQtd, colData);

        List<Venda> vendas = vendaService.listarTodas();
        tabela.setItems(FXCollections.observableArrayList(vendas));

        Button btnExportarPDF = new Button("Exportar para PDF");

        btnExportarPDF.setOnAction(e -> {
            try {
                PdfRelatorioUtil.gerarRelatorioVendas(vendas, "relatorio-vendas.pdf");

                Alert alertaSucesso = new Alert(Alert.AlertType.INFORMATION);
                alertaSucesso.setTitle("Exportação");
                alertaSucesso.setHeaderText(null);
                alertaSucesso.setContentText("PDF gerado com sucesso!");
                alertaSucesso.showAndWait();

            } catch (Exception ex) {
                Alert alertaErro = new Alert(Alert.AlertType.ERROR);
                alertaErro.setTitle("Erro");
                alertaErro.setHeaderText(null);
                alertaErro.setContentText("Erro ao gerar PDF: " + ex.getMessage());
                alertaErro.showAndWait();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(tabela, btnExportarPDF);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


