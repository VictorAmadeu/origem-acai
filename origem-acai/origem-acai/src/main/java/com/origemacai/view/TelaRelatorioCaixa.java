package com.origemacai.view;

import com.origemacai.model.Caixa;
import com.origemacai.service.CaixaService;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class TelaRelatorioCaixa {

    private CaixaService caixaService;

    public TelaRelatorioCaixa(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    public void start() {
        Stage stage = new Stage();
        stage.setTitle("Relatório de Caixa");

        TableView<Caixa> tabela = new TableView<>();

        TableColumn<Caixa, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Caixa, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Caixa, String> colValor = new TableColumn<>("Valor");
        colValor.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty("R$ " + cellData.getValue().getValor().toString()));

        TableColumn<Caixa, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDataHora().toString()));

        tabela.getColumns().addAll(colTipo, colDesc, colValor, colData);

        List<Caixa> movimentos = caixaService.getTodosMovimentos(); // Criar método no serviço
        tabela.setItems(FXCollections.observableArrayList(movimentos));

        stage.setScene(new Scene(tabela, 600, 400));
        stage.show();
    }
}
