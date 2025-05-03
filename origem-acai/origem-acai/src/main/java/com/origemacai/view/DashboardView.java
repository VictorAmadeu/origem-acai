package com.origemacai.view; // Define o pacote onde essa classe está localizada

// Importações JavaFX e serviços do sistema Origem Açaí
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

import com.origemacai.service.ProdutoService;
import com.origemacai.service.VendaService;
import com.origemacai.service.CaixaService;
import com.origemacai.config.SpringContext;

public class DashboardView extends Application { // Classe que representa a tela principal (dashboard), estende Application

    // Serviços injetados do Spring para uso nas telas
    private final ProdutoService produtoService = SpringContext.getBean(ProdutoService.class);
    private final VendaService vendaService = SpringContext.getBean(VendaService.class);
    private final CaixaService caixaService = SpringContext.getBean(CaixaService.class);

    // Construtor padrão necessário para instanciar via 'new DashboardView()'
    public DashboardView() {
    }

    // Método que constrói e exibe a tela principal
    @Override
    public void start(Stage primaryStage) {
        // Cria um layout principal BorderPane para estruturar a tela
        BorderPane borderPane = new BorderPane();

        // Cria e configura o título da tela
        Label lblTitle = new Label("Dashboard Origem Açaí");
        lblTitle.setFont(new Font("Arial", 24)); // Define tamanho da fonte
        lblTitle.setPadding(new Insets(10)); // Espaçamento ao redor do título
        borderPane.setTop(lblTitle); // Define o título no topo da tela
        BorderPane.setAlignment(lblTitle, Pos.CENTER); // Centraliza o título

        // GridPane usado para organizar os botões principais
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER); // Centraliza o conteúdo
        gridPane.setHgap(20); // Espaço horizontal entre colunas
        gridPane.setVgap(20); // Espaço vertical entre linhas
        gridPane.setPadding(new Insets(20)); // Espaçamento interno

        // Cria os botões com ícones e texto
        Button btnProdutos = createButton("Cadastro Produtos", "icons/produtos.png");
        Button btnEstoque = createButton("Gestão Estoque", "icons/estoque.png");
        Button btnVendas = createButton("Registro Vendas", "icons/vendas.png");
        Button btnCaixa = createButton("Controle Caixa", "icons/caixa.png");
        Button btnRelatorios = createButton("Gerar Relatórios", "icons/relatorios.png");
        Button btnExportPDF = createButton("Exportar PDF", "icons/pdf.png");
        Button btnLogout = createButton("Sair", "icons/logout.png");

        // Adiciona os botões no grid
        gridPane.add(btnProdutos, 0, 0);
        gridPane.add(btnEstoque, 1, 0);
        gridPane.add(btnVendas, 0, 1);
        gridPane.add(btnCaixa, 1, 1);
        gridPane.add(btnRelatorios, 0, 2);
        gridPane.add(btnExportPDF, 1, 2);
        gridPane.add(btnLogout, 0, 3, 2, 1); // Ocupa 2 colunas

        // Coloca o GridPane no centro da tela
        borderPane.setCenter(gridPane);

        // Define a ação do botão "Cadastro Produtos"
        btnProdutos.setOnAction(e -> {
            TelaCadastroProduto tela = new TelaCadastroProduto();
            Stage stage = new Stage();
            tela.start(stage); // Abre a tela de cadastro
        });

        // Define a ação do botão "Gestão Estoque"
        btnEstoque.setOnAction(e -> {
            TelaRelatorioEstoque tela = new TelaRelatorioEstoque();
            tela.setProdutoService(produtoService);
            Stage stage = new Stage();
            tela.start(stage);
        });

        // Define a ação do botão "Registro Vendas"
        btnVendas.setOnAction(e -> {
            TelaRelatorioVendas tela = new TelaRelatorioVendas();
            tela.setVendaService(vendaService);
            Stage stage = new Stage();
            tela.start(stage);
        });

        // Define a ação do botão "Controle Caixa"
        btnCaixa.setOnAction(e -> {
            TelaCaixa tela = new TelaCaixa();
            tela.setCaixaService(caixaService);
            Stage stage = new Stage();
            tela.start(stage);
        });

        // Define a ação do botão "Relatórios"
        btnRelatorios.setOnAction(e -> {
            TelaRelatorioCaixa tela = new TelaRelatorioCaixa();
            tela.setCaixaService(caixaService);
            Stage stage = new Stage();
            tela.start(stage);
        });

        // Define ação temporária para o botão de exportação PDF
        btnExportPDF.setOnAction(e -> System.out.println("Funcionalidade de exportar PDF ainda será implementada."));

        // Sai da aplicação ao clicar em "Sair"
        btnLogout.setOnAction(e -> Platform.exit());

        // Cria e aplica a cena com o layout da dashboard
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Origem Açaí - Dashboard Principal");
        primaryStage.show();
    }

    // Método auxiliar para criar botões com ícones
    private Button createButton(String text, String iconPath) {
        Button button = new Button(text); // Cria botão com texto
        button.setPrefSize(200, 100); // Define tamanho padrão

        // Cria ícone a partir do caminho informado
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/" + iconPath)));
        icon.setFitHeight(40); // Altura do ícone
        icon.setFitWidth(40); // Largura do ícone
        button.setGraphic(icon); // Associa o ícone ao botão

        // Aplica estilo CSS direto no botão
        button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        return button; // Retorna botão pronto
    }
}
