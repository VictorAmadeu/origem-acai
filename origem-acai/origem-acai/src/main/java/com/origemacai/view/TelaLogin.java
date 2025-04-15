// Define o pacote onde esta classe está localizada
package com.origemacai.view;

// Importa a classe de utilitário responsável pelo controle de idiomas (internacionalização)
import com.origemacai.util.Idioma;

// Importações das bibliotecas JavaFX para construção da interface gráfica
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// Importa a nova tela principal que será aberta após o login
import com.origemacai.view.DashboardView;

// Define a classe TelaLogin como uma aplicação JavaFX
public class TelaLogin extends Application {

    // Método principal da JavaFX. É chamado automaticamente quando a aplicação é iniciada.
    @Override
    public void start(Stage primaryStage) {

        // Obtém a instância única do idioma atual (Singleton). Isso garante que todas as telas compartilhem o mesmo idioma selecionado.
        Idioma idioma = Idioma.getInstance();

        // Cria um ComboBox para o usuário escolher o idioma da interface
        ComboBox<String> selecaoIdioma = new ComboBox<>();

        // Adiciona as opções de idioma disponíveis ao ComboBox
        selecaoIdioma.getItems().addAll("Português", "Español");

        // Define "Português" como idioma padrão ao abrir a tela
        selecaoIdioma.setValue("Português");

        // Cria os rótulos de texto do formulário, traduzidos conforme o idioma selecionado
        Label labelIdioma = new Label(idioma.get("label.idioma"));     // Exemplo: "Idioma:"
        Label labelUsuario = new Label(idioma.get("label.usuario"));   // Exemplo: "Usuário:"
        Label labelSenha = new Label(idioma.get("label.senha"));       // Exemplo: "Senha:"

        // Cria os campos de entrada de dados (formulário)
        TextField campoUsuario = new TextField();         // Campo para digitar o nome de usuário
        PasswordField campoSenha = new PasswordField();   // Campo para digitar a senha

        // Cria o botão de login com o texto traduzido conforme o idioma
        Button botaoEntrar = new Button(idioma.get("botao.entrar"));  // Exemplo: "Entrar"

        // Evento acionado quando o usuário muda o idioma no ComboBox
        selecaoIdioma.setOnAction(event -> {
            // Pega o idioma escolhido pelo usuário
            String idiomaSelecionado = selecaoIdioma.getValue();

            // Define o novo idioma no sistema
            if (idiomaSelecionado.equals("Português")) {
                idioma.setLocale("pt", "BR");  // Define locale para português do Brasil
            } else {
                idioma.setLocale("es", "ES");  // Define locale para espanhol da Espanha
            }

            // Atualiza os textos dos rótulos e botão com base no novo idioma
            labelIdioma.setText(idioma.get("label.idioma"));
            labelUsuario.setText(idioma.get("label.usuario"));
            labelSenha.setText(idioma.get("label.senha"));
            botaoEntrar.setText(idioma.get("botao.entrar"));
        });

        // Evento acionado quando o botão "Entrar" é clicado
        botaoEntrar.setOnAction(e -> {

            // Fecha a janela atual (login)
            Stage loginStage = (Stage) botaoEntrar.getScene().getWindow();
            loginStage.close();

            // Cria e abre uma nova janela (Stage) para a tela principal (Dashboard)
            DashboardView dashboard = new DashboardView();   // Instancia a dashboard
            Stage dashboardStage = new Stage();              // Cria uma nova janela

            try {
                dashboard.start(dashboardStage);             // Abre a tela da dashboard
            } catch (Exception ex) {
                ex.printStackTrace();                        // Caso ocorra erro ao abrir, imprime no console
            }
        });

        // Cria o layout principal da tela: um GridPane (layout em forma de tabela)
        GridPane layout = new GridPane();

        // Define o espaçamento interno das bordas do layout
        layout.setPadding(new Insets(20));

        // Define o espaço vertical entre os componentes (linhas)
        layout.setVgap(10);

        // Define o espaço horizontal entre os componentes (colunas)
        layout.setHgap(10);

        // Adiciona todos os elementos no layout com suas respectivas posições: (coluna, linha)
        layout.add(labelIdioma, 0, 0);
        layout.add(selecaoIdioma, 1, 0);

        layout.add(labelUsuario, 0, 1);
        layout.add(campoUsuario, 1, 1);

        layout.add(labelSenha, 0, 2);
        layout.add(campoSenha, 1, 2);

        layout.add(botaoEntrar, 1, 3);

        // Cria uma cena com o layout e define o tamanho da janela
        Scene cena = new Scene(layout, 400, 250);

        // Define o título da janela
        primaryStage.setTitle("Login - Origem Açaí");

        // Define a cena que será exibida na janela
        primaryStage.setScene(cena);

        // Exibe a janela na tela
        primaryStage.show();
    }

    // ⚠️ Atenção: o método main() foi removido, pois essa tela agora será iniciada pela MainApplication.java
}

