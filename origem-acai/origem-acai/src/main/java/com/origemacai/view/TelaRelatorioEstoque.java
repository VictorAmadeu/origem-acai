package com.origemacai.view;

import com.origemacai.model.Produto;
import com.origemacai.service.ProdutoService;
import com.origemacai.util.PdfRelatorioUtil;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TelaRelatorioEstoque extends Application {

    private ProdutoService produtoService;

    public void setProdutoService(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Relatório de Estoque");

        TableView<Produto> tabela = new TableView<>();

        TableColumn<Produto, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Produto, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Produto, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Produto, Integer> colEstoque = new TableColumn<>("Estoque");
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque"));

        tabela.getColumns().addAll(colId, colNome, colTipo, colEstoque);

        List<Produto> produtos = produtoService.listarTodos();
        tabela.setItems(FXCollections.observableArrayList(produtos));

        Button btnExportarPDF = new Button("Exportar para PDF");
        btnExportarPDF.setOnAction(e -> {
            try {
                PdfRelatorioUtil.gerarRelatorioEstoque(produtos, "relatorio-estoque.pdf");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportação");
                alert.setHeaderText(null);
                alert.setContentText("PDF gerado com sucesso!");
                alert.showAndWait();

            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro ao gerar PDF: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(tabela, btnExportarPDF);

        Scene scene = new Scene(layout, 600, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
