// Define o pacote onde essa classe está localizada, seguindo a estrutura do projeto Origem Açaí
package com.origemacai.view;

// Importações do JavaFX necessárias para criar a interface gráfica
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// Classe principal da dashboard. Ela herda de Application, que é a base de qualquer aplicação JavaFX.
public class DashboardView extends Application {

    // Método principal que será executado ao iniciar essa tela
    @Override
    public void start(Stage primaryStage) {

        // Cria um layout principal do tipo BorderPane (estrutura com topo, centro, base, etc.)
        BorderPane borderPane = new BorderPane();

        // Cria o título da dashboard e define o texto
        Label lblTitle = new Label("Dashboard Origem Açaí");

        // Define a fonte e tamanho do texto do título
        lblTitle.setFont(new Font("Arial", 24));

        // Adiciona espaçamento ao redor do título
        lblTitle.setPadding(new Insets(10));

        // Define o título como o conteúdo da parte superior da tela
        borderPane.setTop(lblTitle);

        // Centraliza o título no topo
        BorderPane.setAlignment(lblTitle, Pos.CENTER);

        // Cria um layout GridPane para organizar os botões no centro
        GridPane gridPane = new GridPane();

        // Centraliza o GridPane no centro da tela
        gridPane.setAlignment(Pos.CENTER);

        // Define o espaçamento horizontal entre os botões
        gridPane.setHgap(20);

        // Define o espaçamento vertical entre os botões
        gridPane.setVgap(20);

        // Define uma margem interna para o GridPane
        gridPane.setPadding(new Insets(20));

        // Criação de cada botão com texto e ícone (usando o método auxiliar createButton)
        Button btnProdutos = createButton("Cadastro Produtos", "icons/produtos.png");
        Button btnEstoque = createButton("Gestão Estoque", "icons/estoque.png");
        Button btnVendas = createButton("Registro Vendas", "icons/vendas.png");
        Button btnCaixa = createButton("Controle Caixa", "icons/caixa.png");
        Button btnRelatorios = createButton("Gerar Relatórios", "icons/relatorios.png");
        Button btnExportPDF = createButton("Exportar PDF", "icons/pdf.png");
        Button btnLogout = createButton("Sair", "icons/logout.png");

        // Adiciona os botões ao GridPane em suas posições respectivas (coluna, linha)
        gridPane.add(btnProdutos, 0, 0);
        gridPane.add(btnEstoque, 1, 0);
        gridPane.add(btnVendas, 0, 1);
        gridPane.add(btnCaixa, 1, 1);
        gridPane.add(btnRelatorios, 0, 2);
        gridPane.add(btnExportPDF, 1, 2);

        // Adiciona o botão "Sair" ocupando duas colunas (colSpan = 2)
        gridPane.add(btnLogout, 0, 3, 2, 1);

        // Define o GridPane como conteúdo central da tela
        borderPane.setCenter(gridPane);

        // Define a ação do botão "Cadastro Produtos"
        btnProdutos.setOnAction(e -> {
            TelaCadastroProduto tela = new TelaCadastroProduto(); // Cria a tela de cadastro de produtos
            Stage stage = new Stage(); // Nova janela
            try {
                tela.start(stage); // Abre a tela
            } catch (Exception ex) {
                ex.printStackTrace(); // Mostra o erro se falhar
            }
        });

        // Define a ação do botão "Gestão Estoque"
        btnEstoque.setOnAction(e -> {
            TelaRelatorioEstoque tela = new TelaRelatorioEstoque(); // Cria a tela de estoque
            Stage stage = new Stage();
            try {
                tela.start(stage); // Abre a tela
            } catch (Exception ex) {
                ex.printStackTrace(); // Mostra erro se ocorrer
            }
        });

        // Define a ação do botão "Registro Vendas"
        btnVendas.setOnAction(e -> {
            TelaRelatorioVendas tela = new TelaRelatorioVendas(); // Cria a tela de vendas
            Stage stage = new Stage();
            try {
                tela.start(stage); // Abre a tela
            } catch (Exception ex) {
                ex.printStackTrace(); // Mostra erro se ocorrer
            }
        });

        // Define a ação do botão "Controle Caixa"
        btnCaixa.setOnAction(e -> {
            TelaCaixa tela = new TelaCaixa(); // Cria a tela do caixa
            Stage stage = new Stage();
            try {
                tela.start(stage); // Abre a tela
            } catch (Exception ex) {
                ex.printStackTrace(); // Mostra erro se ocorrer
            }
        });

        // Define a ação do botão "Gerar Relatórios"
        btnRelatorios.setOnAction(e -> {
            TelaRelatorioCaixa tela = new TelaRelatorioCaixa(); // Reutiliza a tela de relatório de caixa
            Stage stage = new Stage();
            try {
                tela.start(stage); // Abre a tela
            } catch (Exception ex) {
                ex.printStackTrace(); // Mostra erro se ocorrer
            }
        });

        // Define a ação do botão "Exportar PDF"
        btnExportPDF.setOnAction(e -> {
            // Ainda em desenvolvimento, imprime no console uma mensagem simples
            System.out.println("Funcionalidade de exportar PDF ainda será implementada.");
        });

        // Define a ação do botão "Sair"
        btnLogout.setOnAction(e -> {
            Platform.exit(); // Fecha completamente a aplicação
        });

        // Cria a cena principal com largura 800px e altura 600px
        Scene scene = new Scene(borderPane, 800, 600);

        // Define a cena no Stage principal (janela principal)
        primaryStage.setScene(scene);

        // Define o título da janela principal
        primaryStage.setTitle("Origem Açaí - Dashboard Principal");

        // Exibe a janela
        primaryStage.show();
    }

    // Método auxiliar que cria botões personalizados com texto e ícone
    private Button createButton(String text, String iconPath) {
        Button button = new Button(text); // Cria o botão com o texto passado
        button.setPrefSize(200, 100); // Define largura e altura padrão

        // Cria o ícone do botão carregando imagem da pasta /resources/icons
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/" + iconPath)));
        icon.setFitHeight(40); // Altura do ícone
        icon.setFitWidth(40);  // Largura do ícone
        button.setGraphic(icon); // Define o ícone no botão

        // Define o estilo visual do botão (tamanho da fonte e negrito)
        button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        return button; // Retorna o botão pronto para ser usado
    }
}

