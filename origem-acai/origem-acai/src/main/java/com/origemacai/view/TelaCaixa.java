package com.origemacai.view;

import com.origemacai.service.CaixaService;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class TelaCaixa {

    private final CaixaService caixaService;

    public TelaCaixa(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    public void start() {
        Stage stage = new Stage();
        BigDecimal saldo = caixaService.getSaldoAtual();

        Label label = new Label("💰 Saldo atual do caixa: R$ " + saldo);

        VBox layout = new VBox(20);
        layout.getChildren().add(label);

        Scene cena = new Scene(layout, 400, 200);
        stage.setTitle("Controle de Caixa");
        stage.setScene(cena);
        stage.show();
    }
}
