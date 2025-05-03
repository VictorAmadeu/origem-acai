// Define o pacote onde essa classe está localizada
package com.origemacai.view;

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

public class DashboardView extends Application {

    // Serviços são recuperados do Spring diretamente via SpringContext
    private final ProdutoService produtoService = SpringContext.getBean(ProdutoService.class);
    private final VendaService vendaService = SpringContext.getBean(VendaService.class);
    private final CaixaService caixaService = SpringContext.getBean(CaixaService.class);

    // Construtor padrão necessário para instanciar via new DashboardView()
    public DashboardView() {
    }

    // Método principal da tela que configura todos os componentes e exibe a interface
    @Override
    public void start(Stage primaryStage) {
        // Cria o layout principal com divisão em áreas (topo, centro, etc.)
        BorderPane borderPane = new BorderPane();

        // Cria o título da tela e define estilo visual
        Label lblTitle = new Label("Dashboard Origem Açaí");
        lblTitle.setFont(new Font("Arial", 24));        // Fonte maior para o título
        lblTitle.setPadding(new Insets(10));            // Espaçamento interno ao redor
        borderPane.setTop(lblTitle);                    // Posiciona no topo da tela
        BorderPane.setAlignment(lblTitle, Pos.CENTER);  // Alinha ao centro

        // Cria um grid para organizar os botões do menu
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);              // Centraliza no meio da tela
        gridPane.setHgap(20);                            // Espaço horizontal entre colunas
        gridPane.setVgap(20);                            // Espaço vertical entre linhas
        gridPane.setPadding(new Insets(20));             // Espaçamento interno geral

        // Criação dos botões do dashboard com ícones
        Button btnProdutos = createButton("Cadastro Produtos", "icons/produtos.png");
        Button btnEstoque = createButton("Gestão Estoque", "icons/estoque.png");
        Button btnVendas = createButton("Registro Vendas", "icons/vendas.png");
        Button btnCaixa = createButton("Controle Caixa", "icons/caixa.png");
        Button btnRelatorios = createButton("Gerar Relatórios", "icons/relatorios.png");
        Button btnExportPDF = createButton("Exportar PDF", "icons/pdf.png");
        Button btnLogout = createButton("Sair", "icons/logout.png");

        // Posiciona os botões no grid (linha, coluna)
        gridPane.add(btnProdutos, 0, 0);
        gridPane.add(btnEstoque, 1, 0);
        gridPane.add(btnVendas, 0, 1);
        gridPane.add(btnCaixa, 1, 1);
        gridPane.add(btnRelatorios, 0, 2);
        gridPane.add(btnExportPDF, 1, 2);
        gridPane.add(btnLogout, 0, 3, 2, 1); // Ocupa 2 colunas

        // Insere o grid de botões no centro do layout principal
        borderPane.setCenter(gridPane);

        // === AÇÕES DOS BOTÕES ===

        // Abre a tela de cadastro de produtos
        btnProdutos.setOnAction(e -> {
            TelaCadastroProduto tela = new TelaCadastroProduto();
            Stage stage = new Stage();
            tela.start(stage);
        });

        // Abre a tela de relatório de estoque (usa Spring para injeção de dependência)
        btnEstoque.setOnAction(e -> {
            TelaRelatorioEstoque tela = new TelaRelatorioEstoque();
            tela.setProdutoService(produtoService); // ainda exige setter manual
            Stage stage = new Stage();
            tela.start(stage);
        });

        // Abre a tela de relatório de vendas (serviços agora injetados via Spring diretamente)
        btnVendas.setOnAction(e -> {
            TelaRelatorioVendas tela = new TelaRelatorioVendas(); // services já acessam SpringContext
            Stage stage = new Stage();
            tela.start(stage);
        });

        // Abre a tela de controle de caixa
        btnCaixa.setOnAction(e -> {
            TelaCaixa tela = new TelaCaixa();
            tela.setCaixaService(caixaService); // ainda exige setter manual
            Stage stage = new Stage();
            tela.start(stage);
        });

        // Abre a tela de relatórios financeiros
        btnRelatorios.setOnAction(e -> {
            TelaRelatorioCaixa tela = new TelaRelatorioCaixa();
            tela.setCaixaService(caixaService); // ainda exige setter manual
            Stage stage = new Stage();
            tela.start(stage);
        });

        // Ação temporária do botão "Exportar PDF"
        btnExportPDF.setOnAction(e -> {
            System.out.println("Funcionalidade de exportar PDF ainda será implementada.");
        });

        // Encerra a aplicação ao clicar em "Sair"
        btnLogout.setOnAction(e -> Platform.exit());

        // Cria a cena com os elementos e define na janela principal
        Scene scene = new Scene(borderPane, 800, 600); // largura e altura fixas
        primaryStage.setScene(scene);                  // Aplica a cena
        primaryStage.setTitle("Origem Açaí - Dashboard Principal"); // Título da janela
        primaryStage.show();                           // Exibe a janela
    }

    // Método auxiliar para criar botões com ícones customizados
    private Button createButton(String text, String iconPath) {
        Button button = new Button(text);              // Cria botão com texto
        button.setPrefSize(200, 100);                  // Tamanho padrão do botão

        // Cria o ícone visual com imagem carregada do caminho
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/" + iconPath)));
        icon.setFitHeight(40);                         // Altura do ícone
        icon.setFitWidth(40);                          // Largura do ícone
        button.setGraphic(icon);                       // Associa o ícone ao botão

        // Aplica estilo visual no botão via CSS inline
        button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        return button;                                 // Retorna o botão criado
    }
}

