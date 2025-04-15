package com.origemacai;

import com.origemacai.view.TelaLogin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

// Classe principal que une JavaFX com Spring Boot
public class MainApplication extends Application {

    // Referência para o contexto Spring
    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        // Inicializa o contexto do Spring
        springContext = new SpringApplicationBuilder(OrigemAcaiApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) {
        // Inicializa a interface gráfica (por exemplo: TelaLogin)
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.start(primaryStage);
    }

    @Override
    public void stop() {
        // Encerra o contexto do Spring quando a aplicação for fechada
        springContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        // Inicia a aplicação JavaFX (que por sua vez inicia o Spring Boot)
        launch(args);
    }
}
