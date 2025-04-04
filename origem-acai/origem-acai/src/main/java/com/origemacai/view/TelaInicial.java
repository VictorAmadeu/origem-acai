package com.origemacai.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaInicial extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Cria um rótulo de boas-vindas
        Label mensagem = new Label("🍧 Bem-vindo ao Sistema Origem Açaí!");
        
        // Layout vertical
        VBox layout = new VBox(20);
        layout.getChildren().add(mensagem);

        // Cria a cena principal
        Scene scene = new Scene(layout, 400, 200);

        // Define configurações da janela
        primaryStage.setTitle("Origem Açaí - Início");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Inicia a aplicação JavaFX
    }
}
