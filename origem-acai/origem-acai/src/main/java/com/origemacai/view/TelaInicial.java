// Define o pacote onde esta classe está localizada
package com.origemacai.view;

// Importa a classe base da aplicação JavaFX
import javafx.application.Application;

// Importa classes necessárias para criar a interface gráfica
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Importa o serviço que será injetado pelo Spring
import com.origemacai.service.ProdutoService;

// Importa a classe utilitária que criamos para acessar o ApplicationContext do Spring
import com.origemacai.config.SpringContext;

// Define a classe TelaInicial que herda de Application (JavaFX)
public class TelaInicial extends Application {

    // Método principal da aplicação JavaFX (ponto de entrada)
    @Override
    public void start(Stage primaryStage) {
        // ⚙️ Injeção correta do ProdutoService usando o ApplicationContext do Spring
        ProdutoService produtoService = SpringContext.getBean(ProdutoService.class);

        // 🔍 Apenas para teste: vamos pegar a quantidade de produtos cadastrados no banco
        int totalProdutos = produtoService.listarTodos().size();

        // Cria um rótulo com mensagem de boas-vindas personalizada
        Label mensagem = new Label("🍧 Bem-vindo ao Sistema Origem Açaí!");

        // Cria outro rótulo com a quantidade de produtos (exemplo prático de uso do serviço)
        Label produtos = new Label("Produtos cadastrados: " + totalProdutos);

        // Cria um layout vertical com espaçamento de 20px entre os elementos
        VBox layout = new VBox(20);

        // Adiciona os rótulos ao layout
        layout.getChildren().addAll(mensagem, produtos);

        // Cria uma cena com o layout, com largura 400px e altura 200px
        Scene scene = new Scene(layout, 400, 200);

        // Define o título da janela
        primaryStage.setTitle("Origem Açaí - Início");

        // Define a cena (interface gráfica) a ser exibida
        primaryStage.setScene(scene);

        // Exibe a janela principal
        primaryStage.show();
    }

    // Método main responsável por iniciar a aplicação JavaFX
    public static void main(String[] args) {
        launch(args); // Inicializa o JavaFX, que chamará o método start()
    }
}

