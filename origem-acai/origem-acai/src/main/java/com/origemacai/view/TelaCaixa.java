package com.origemacai.view;

import com.origemacai.service.CaixaService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class TelaCaixa extends Application {

    private CaixaService caixaService;

    public void setCaixaService(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    @Override
    public void start(Stage primaryStage) {
        BigDecimal saldo = caixaService.getSaldoAtual();

        Label label = new Label("💰 Saldo atual do caixa: R$ " + saldo);
        VBox layout = new VBox(20);
        layout.getChildren().add(label);

        Scene cena = new Scene(layout, 400, 200);
        primaryStage.setTitle("Controle de Caixa");
        primaryStage.setScene(cena);
        primaryStage.show();
    }
}

