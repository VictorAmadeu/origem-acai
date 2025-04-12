// Define o pacote onde esta classe está localizada
package com.origemacai.view;

// Importa a classe de utilitário para controle de idioma (Singleton)
import com.origemacai.util.Idioma;

// Importa classes do JavaFX necessárias para construção da interface
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// Importa a tela que será aberta após o login
import com.origemacai.view.TelaCadastroProduto;

// Classe que representa a tela de login. Estende Application do JavaFX.
public class TelaLogin extends Application {

    // Método principal chamado ao iniciar a aplicação
    @Override
    public void start(Stage primaryStage) {

        // Obtém o idioma atual selecionado (singleton)
        Idioma idioma = Idioma.getInstance();

        // Cria o ComboBox (menu suspenso) com as opções de idioma
        ComboBox<String> selecaoIdioma = new ComboBox<>();
        selecaoIdioma.getItems().addAll("Português", "Español");  // Adiciona as opções
        selecaoIdioma.setValue("Português");                       // Define valor padrão

        // Cria rótulos com os textos traduzidos conforme o idioma
        Label labelIdioma = new Label(idioma.get("label.idioma"));
        Label labelUsuario = new Label(idioma.get("label.usuario"));
        Label labelSenha = new Label(idioma.get("label.senha"));

        // Cria campos de entrada de texto
        TextField campoUsuario = new TextField();                  // Campo para nome de usuário
        PasswordField campoSenha = new PasswordField();            // Campo para senha
        Button botaoEntrar = new Button(idioma.get("botao.entrar")); // Botão "Entrar"

        // Evento que atualiza os textos quando o idioma é alterado no ComboBox
        selecaoIdioma.setOnAction(event -> {
            String idiomaSelecionado = selecaoIdioma.getValue();

            if (idiomaSelecionado.equals("Português")) {
                idioma.setLocale("pt", "BR");  // Define locale para português
            } else {
                idioma.setLocale("es", "ES");  // Define locale para espanhol
            }

            // Atualiza os textos visíveis na interface
            labelIdioma.setText(idioma.get("label.idioma"));
            labelUsuario.setText(idioma.get("label.usuario"));
            labelSenha.setText(idioma.get("label.senha"));
            botaoEntrar.setText(idioma.get("botao.entrar"));
        });

        // Evento do botão "Entrar" → abre a tela de cadastro
        botaoEntrar.setOnAction(e -> {
            Stage telaCadastro = new Stage();                      // Cria nova janela
            new TelaCadastroProduto().start(telaCadastro);         // Abre a tela de cadastro
            primaryStage.hide();                                   // Oculta a tela de login
        });

        // Cria um layout em grade e configura o espaçamento
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20));
        layout.setVgap(10);
        layout.setHgap(10);

        // Adiciona os componentes ao layout (coluna, linha)
        layout.add(labelIdioma, 0, 0);
        layout.add(selecaoIdioma, 1, 0);
        layout.add(labelUsuario, 0, 1);
        layout.add(campoUsuario, 1, 1);
        layout.add(labelSenha, 0, 2);
        layout.add(campoSenha, 1, 2);
        layout.add(botaoEntrar, 1, 3);

        // Cria a cena com o layout e define o tamanho da janela
        Scene cena = new Scene(layout, 400, 250);

        // Define o título da janela e exibe a tela
        primaryStage.setTitle("Login - Origem Açaí");
        primaryStage.setScene(cena);
        primaryStage.show();
    }

    // Método principal que inicia a aplicação JavaFX
    public static void main(String[] args) {
        launch(args);
    }
}
