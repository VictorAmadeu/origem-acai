package com.origemacai.view;

// Importações necessárias para a construção da interface JavaFX
import com.origemacai.util.Idioma; // Classe utilitária de internacionalização
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TelaLogin extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Obtém a instância única da classe Idioma para uso da internacionalização
        Idioma idioma = Idioma.getInstance();

        // Criação de um ComboBox para o usuário escolher o idioma
        ComboBox<String> selecaoIdioma = new ComboBox<>();
        selecaoIdioma.getItems().addAll("Português", "Español"); // Opções disponíveis
        selecaoIdioma.setValue("Português"); // Valor padrão

        // Criação do ícone (logo da aplicação)
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/icons/acai.png")));
        logo.setFitHeight(80); // Altura do ícone
        logo.setFitWidth(80);  // Largura do ícone

        // Criação do título da aplicação
        Label titulo = new Label("Origem Açaí");
        titulo.setFont(new Font("Arial", 28)); // Define a fonte do título
        titulo.getStyleClass().add("titulo");  // Adiciona uma classe CSS para estilização

        // Criação dos rótulos, com textos vindos da classe Idioma
        Label labelIdioma = new Label(idioma.get("label.idioma"));
        Label labelUsuario = new Label(idioma.get("label.usuario"));
        Label labelSenha = new Label(idioma.get("label.senha"));

        // Campos de entrada para usuário e senha
        TextField campoUsuario = new TextField();
        PasswordField campoSenha = new PasswordField();

        // Botão de entrada com texto internacionalizado
        Button botaoEntrar = new Button(idioma.get("botao.entrar"));

        // Evento para troca de idioma no ComboBox
        selecaoIdioma.setOnAction(event -> {
            String idiomaSelecionado = selecaoIdioma.getValue(); // Pega valor selecionado
            if (idiomaSelecionado.equals("Português")) {
                idioma.setLocale("pt", "BR"); // Define localidade para Português
            } else {
                idioma.setLocale("es", "ES"); // Define localidade para Espanhol
            }

            // Atualiza todos os textos da tela com o novo idioma
            labelIdioma.setText(idioma.get("label.idioma"));
            labelUsuario.setText(idioma.get("label.usuario"));
            labelSenha.setText(idioma.get("label.senha"));
            botaoEntrar.setText(idioma.get("botao.entrar"));
        });

        // Ação do botão de login
        botaoEntrar.setOnAction(e -> {
            // Fecha a janela de login
            Stage loginStage = (Stage) botaoEntrar.getScene().getWindow();
            loginStage.close();

            // Abre a tela da dashboard principal
            Stage dashboardStage = new Stage();
            DashboardView dashboard = new DashboardView(); // Criação direta da dashboard

            try {
                dashboard.start(dashboardStage); // Exibe a dashboard
            } catch (Exception ex) {
                ex.printStackTrace(); // Exibe erros no console
            }
        });

        // Cria o GridPane que organiza os campos de formulário (idioma, usuário, senha, botão)
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);     // Centraliza no painel
        formGrid.setHgap(10);                  // Espaçamento horizontal entre colunas
        formGrid.setVgap(10);                  // Espaçamento vertical entre linhas
        formGrid.setPadding(new Insets(10));   // Espaçamento interno do grid

        // Adiciona os componentes ao grid (em posições de linha e coluna)
        formGrid.add(labelIdioma, 0, 0);
        formGrid.add(selecaoIdioma, 1, 0);
        formGrid.add(labelUsuario, 0, 1);
        formGrid.add(campoUsuario, 1, 1);
        formGrid.add(labelSenha, 0, 2);
        formGrid.add(campoSenha, 1, 2);
        formGrid.add(botaoEntrar, 1, 3); // Botão fica abaixo dos campos

        // VBox com logo, título e formulário, centralizado
        VBox painelCentral = new VBox(10, logo, titulo, formGrid);
        painelCentral.setAlignment(Pos.CENTER); // Centraliza tudo verticalmente
        painelCentral.setPadding(new Insets(20)); // Espaçamento interno
        painelCentral.setStyle("-fx-background-color: white; -fx-background-radius: 10;"); // Estilo de fundo
        painelCentral.setMaxWidth(350); // Largura máxima para o painel

        // StackPane como fundo cinza claro e centralizando o painel branco
        StackPane fundo = new StackPane(painelCentral);
        fundo.setStyle("-fx-background-color: #eeeeee;");
        fundo.setPadding(new Insets(50)); // Margem da tela

        // Cena principal com largura e altura definidas
        Scene cena = new Scene(fundo, 600, 400);

        // Aplica o CSS externo localizado em /styles/login.css
        cena.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());

        // Configurações finais do Stage (janela)
        primaryStage.setScene(cena);                      // Define a cena na janela
        primaryStage.setTitle("Login - Origem Açaí");     // Título da janela
        primaryStage.show();                              // Mostra a janela
    }
}

