package com.origemacai.view;

import com.origemacai.model.Produto;
import com.origemacai.service.ProdutoService;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class TelaRelatorioEstoque {

    private ProdutoService produtoService;

    public TelaRelatorioEstoque(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public void start() {
        Stage stage = new Stage();
        stage.setTitle("Relatório de Estoque");

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

        stage.setScene(new Scene(tabela, 600, 400));
        stage.show();
    }
}
