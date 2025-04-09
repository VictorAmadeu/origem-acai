package com.origemacai.view;

import com.origemacai.model.Venda;
import com.origemacai.service.VendaService;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class TelaRelatorioVendas {

    private VendaService vendaService;

    public TelaRelatorioVendas(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    public void start() {
        Stage stage = new Stage();
        stage.setTitle("Relatório de Vendas");

        TableView<Venda> tabela = new TableView<>();

        TableColumn<Venda, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Venda, String> colProduto = new TableColumn<>("Produto");
        colProduto.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProduto().getNome()));

        TableColumn<Venda, Integer> colQtd = new TableColumn<>("Quantidade");
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        TableColumn<Venda, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDataHora().toString()));

        tabela.getColumns().addAll(colId, colProduto, colQtd, colData);

        List<Venda> vendas = vendaService.listarTodas(); // Criar método no serviço
        tabela.setItems(FXCollections.observableArrayList(vendas));

        stage.setScene(new Scene(tabela, 600, 400));
        stage.show();
    }
}
