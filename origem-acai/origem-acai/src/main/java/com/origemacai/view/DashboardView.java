package com.origemacai.view; // Define o pacote onde está a classe (estrutura MVC do projeto)

// Importações necessárias para os componentes e layouts do JavaFX
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

public class DashboardView extends Application { // A classe herda Application para permitir execução como JavaFX

    @Override
    public void start(Stage primaryStage) {
        // Cria o layout principal da janela usando BorderPane (estrutura dividida em topo, centro, etc.)
        BorderPane borderPane = new BorderPane();

        // Cria um rótulo (título da tela) e define fonte e padding
        Label lblTitle = new Label("Dashboard Origem Açaí");
        lblTitle.setFont(new Font("Arial", 24)); // Define o tamanho da fonte
        lblTitle.setPadding(new Insets(10)); // Adiciona espaço em volta do texto
        borderPane.setTop(lblTitle); // Coloca o título no topo da tela
        BorderPane.setAlignment(lblTitle, Pos.CENTER); // Centraliza o título no topo

        // Cria um GridPane para organizar os botões no centro da tela em forma de grade
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER); // Centraliza o GridPane
        gridPane.setHgap(20); // Espaço horizontal entre os botões
        gridPane.setVgap(20); // Espaço vertical entre os botões
        gridPane.setPadding(new Insets(20)); // Margem interna do GridPane

        // Criação dos botões com texto e ícone, usando método auxiliar
        Button btnProdutos = createButton("Cadastro Produtos", "icons/produtos.png");
        Button btnEstoque = createButton("Gestão Estoque", "icons/estoque.png");
        Button btnVendas = createButton("Registro Vendas", "icons/vendas.png");
        Button btnCaixa = createButton("Controle Caixa", "icons/caixa.png");
        Button btnRelatorios = createButton("Gerar Relatórios", "icons/relatorios.png");
        Button btnExportPDF = createButton("Exportar PDF", "icons/pdf.png");
        Button btnLogout = createButton("Sair", "icons/logout.png");

        // Adiciona os botões ao GridPane nas posições desejadas
        gridPane.add(btnProdutos, 0, 0);
        gridPane.add(btnEstoque, 1, 0);
        gridPane.add(btnVendas, 0, 1);
        gridPane.add(btnCaixa, 1, 1);
        gridPane.add(btnRelatorios, 0, 2);
        gridPane.add(btnExportPDF, 1, 2);
        gridPane.add(btnLogout, 0, 3, 2, 1); // Ocupa 2 colunas (colSpan = 2)

        // Define o GridPane como conteúdo central da tela
        borderPane.setCenter(gridPane);

        // Define o que acontece ao clicar no botão "Sair"
        btnLogout.setOnAction(e -> Platform.exit()); // Fecha a aplicação

        // Botão "Cadastro Produtos" abre a TelaCadastroProduto
        btnProdutos.setOnAction(e -> {
            TelaCadastroProduto tela = new TelaCadastroProduto(); // Cria instância da tela de produtos
            Stage stage = new Stage(); // Nova janela (Stage)
            try {
                tela.start(stage); // Chama o método start() da nova tela
            } catch (Exception ex) {
                ex.printStackTrace(); // Em caso de erro, imprime no console
            }
        });

        // Botão "Controle Caixa" abre a TelaCaixa
        btnCaixa.setOnAction(e -> {
            TelaCaixa tela = new TelaCaixa(); // Cria instância da tela de caixa
            Stage stage = new Stage(); // Nova janela
            try {
                tela.start(stage); // Abre a tela de caixa
            } catch (Exception ex) {
                ex.printStackTrace(); // Erro, se houver
            }
        });

        // Botão "Gestão Estoque" abre a TelaRelatorioEstoque
        btnEstoque.setOnAction(e -> {
            TelaRelatorioEstoque tela = new TelaRelatorioEstoque(); // Instância da tela de estoque
            Stage stage = new Stage(); // Nova janela
            try {
                tela.start(stage); // Abre a tela de estoque
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Botão "Registro Vendas" abre a TelaRelatorioVendas
        btnVendas.setOnAction(e -> {
            TelaRelatorioVendas tela = new TelaRelatorioVendas(); // Instância da tela de vendas
            Stage stage = new Stage();
            try {
                tela.start(stage); // Abre tela de vendas
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Botão "Gerar Relatórios" abre a TelaRelatorioCaixa (exemplo)
        btnRelatorios.setOnAction(e -> {
            TelaRelatorioCaixa tela = new TelaRelatorioCaixa(); // Instância do relatório de caixa
            Stage stage = new Stage();
            try {
                tela.start(stage); // Abre a tela de relatório de caixa
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Botão "Exportar PDF" reutiliza a mesma tela de relatórios (ou futura tela específica)
        btnExportPDF.setOnAction(e -> {
            TelaRelatorioCaixa tela = new TelaRelatorioCaixa(); // Usando tela de caixa como exemplo
            Stage stage = new Stage();
            try {
                tela.start(stage); // Exibe a tela usada para exportar
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Cria a cena final com o BorderPane e define no Stage (janela principal)
        Scene scene = new Scene(borderPane, 800, 600); // Tamanho da janela
        primaryStage.setScene(scene); // Aplica a cena na janela
        primaryStage.setTitle("Origem Açaí - Dashboard Principal"); // Título da janela
        primaryStage.show(); // Mostra a janela
    }

    // Método auxiliar para criar botões padronizados com texto e ícone
    private Button createButton(String text, String iconPath) {
        Button button = new Button(text); // Cria botão com texto
        button.setPrefSize(200, 100); // Define tamanho padrão

        // Tenta carregar a imagem do ícone
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/" + iconPath)));
        icon.setFitHeight(40); // Altura do ícone
        icon.setFitWidth(40); // Largura do ícone
        button.setGraphic(icon); // Define o ícone no botão

        // Estilo do botão: fonte em negrito, tamanho médio
        button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        return button; // Retorna o botão pronto
    }
}
