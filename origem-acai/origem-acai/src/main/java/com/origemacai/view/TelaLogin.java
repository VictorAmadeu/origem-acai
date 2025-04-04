// Define o pacote onde esta classe está localizada
package com.origemacai.view;

// Importa a classe de utilitário para controle de idioma
import com.origemacai.util.Idioma;

// Importa a classe principal do JavaFX para criar uma aplicação gráfica
import javafx.application.Application;

// Importa a classe para definir espaçamentos internos (padding)
import javafx.geometry.Insets;

// Importa os componentes gráficos que serão usados na tela
import javafx.scene.Scene;
import javafx.scene.control.*;

// Importa o layout em grade para organizar os elementos
import javafx.scene.layout.GridPane;

// Importa a janela principal (Stage)
import javafx.stage.Stage;

// Classe principal da interface de login que estende Application do JavaFX
public class TelaLogin extends Application {

    // Método que é executado ao iniciar a aplicação
    @Override
    public void start(Stage primaryStage) {

        // Obtém a instância atual do idioma (singleton)
        Idioma idioma = Idioma.getInstance();

        // Cria o ComboBox (menu suspenso) com as opções de idioma
        ComboBox<String> selecaoIdioma = new ComboBox<>();

        // Adiciona as opções de idioma ao ComboBox
        selecaoIdioma.getItems().addAll("Português", "Español");

        // Define o idioma padrão como "Português"
        selecaoIdioma.setValue("Português");

        // Cria os componentes de texto (rótulos e campos) com base no idioma atual
        Label labelIdioma = new Label(idioma.get("label.idioma"));           // "Idioma"
        Label labelUsuario = new Label(idioma.get("label.usuario"));         // "Usuário" ou "Usuario"
        Label labelSenha = new Label(idioma.get("label.senha"));             // "Senha" ou "Contraseña"
        TextField campoUsuario = new TextField();                            // Campo de entrada para o usuário
        PasswordField campoSenha = new PasswordField();                      // Campo de entrada para senha
        Button botaoEntrar = new Button(idioma.get("botao.entrar"));         // Botão de login: "Entrar"

        // Adiciona um evento que detecta quando o usuário muda o idioma no ComboBox
        selecaoIdioma.setOnAction(event -> {
            // Captura o idioma selecionado pelo usuário
            String idiomaSelecionado = selecaoIdioma.getValue();

            // Atualiza o idioma global com base na escolha
            if (idiomaSelecionado.equals("Português")) {
                idioma.setLocale("pt", "BR");
            } else if (idiomaSelecionado.equals("Español")) {
                idioma.setLocale("es", "ES");
            }

            // Atualiza os textos visíveis com base no novo idioma
            labelIdioma.setText(idioma.get("label.idioma"));            // Atualiza rótulo "Idioma"
            labelUsuario.setText(idioma.get("label.usuario"));          // Atualiza rótulo "Usuário"
            labelSenha.setText(idioma.get("label.senha"));              // Atualiza rótulo "Senha"
            botaoEntrar.setText(idioma.get("botao.entrar"));            // Atualiza botão "Entrar"
        });

        // Cria o layout em grade (GradePane) para organizar os componentes
        GridPane layout = new GridPane();

        // Define o espaçamento interno do layout (padding)
        layout.setPadding(new Insets(20));

        // Define o espaçamento vertical entre as linhas
        layout.setVgap(10);

        // Define o espaçamento horizontal entre as colunas
        layout.setHgap(10);

        // Adiciona os componentes ao layout com suas respectivas posições (coluna, linha)
        layout.add(labelIdioma, 0, 0);
        layout.add(selecaoIdioma, 1, 0);
        layout.add(labelUsuario, 0, 1);
        layout.add(campoUsuario, 1, 1);
        layout.add(labelSenha, 0, 2);
        layout.add(campoSenha, 1, 2);
        layout.add(botaoEntrar, 1, 3);

        // Cria uma cena com o layout e define seu tamanho
        Scene cena = new Scene(layout, 400, 250);

        // Define o título da janela principal
        primaryStage.setTitle("Login - Origem Açaí");

        // Define a cena principal da janela
        primaryStage.setScene(cena);

        // Exibe a janela
        primaryStage.show();
    }

    // Método principal que inicia a aplicação
    public static void main(String[] args) {
        launch(args);  // Chama o método start()
    }
}

