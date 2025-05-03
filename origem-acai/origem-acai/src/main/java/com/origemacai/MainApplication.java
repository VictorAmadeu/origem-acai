// Define o pacote em que esta classe está localizada
package com.origemacai;

// Importa a tela inicial da aplicação (tela de login)
import com.origemacai.view.TelaLogin;

// Importações da API JavaFX
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

// Importações do Spring Boot
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

// Importa a classe utilitária criada para guardar o contexto Spring
import com.origemacai.config.SpringContext;

/**
 * Classe principal que integra JavaFX com Spring Boot
 * Herda de javafx.application.Application para controlar a interface gráfica
 */
public class MainApplication extends Application {

    // Guarda o contexto da aplicação Spring (usado para acessar beans como serviços e repositórios)
    private static ConfigurableApplicationContext springContext;

    /**
     * Método chamado automaticamente **antes** da interface ser carregada.
     * Aqui inicializamos o Spring Boot.
     */
    @Override
    public void init() {
        // Inicia o Spring Boot passando a classe principal da aplicação
        springContext = new SpringApplicationBuilder(OrigemAcaiApplication.class).run();

        // Armazena o contexto em uma classe utilitária estática
        // Isso permitirá que outras classes JavaFX acessem os serviços gerenciados pelo Spring
        SpringContext.setApplicationContext(springContext);
    }

    /**
     * Método chamado para iniciar a interface gráfica JavaFX.
     * Aqui carregamos a primeira tela da aplicação.
     */
    @Override
    public void start(Stage primaryStage) {
        // Cria uma instância da tela de login (tela inicial do sistema)
        TelaLogin telaLogin = new TelaLogin();

        // Inicia a tela de login, passando o Stage principal do JavaFX
        telaLogin.start(primaryStage);
    }

    /**
     * Método chamado quando a aplicação for encerrada.
     * Aqui encerramos o contexto do Spring e a plataforma JavaFX.
     */
    @Override
    public void stop() {
        // Encerra corretamente o contexto Spring Boot
        springContext.close();

        // Finaliza a plataforma JavaFX
        Platform.exit();
    }

    /**
     * Método principal, ponto de entrada da aplicação JavaFX.
     * O método launch é responsável por iniciar todo o ciclo de vida da aplicação JavaFX.
     */
    public static void main(String[] args) {
        launch(args); // Inicia a aplicação JavaFX (que por sua vez chama init(), start() e stop())
    }
}

