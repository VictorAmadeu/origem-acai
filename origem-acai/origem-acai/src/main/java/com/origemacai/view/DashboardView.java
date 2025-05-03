package com.origemacai.view;

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

    // Serviços injetados via SpringContext
    private final ProdutoService produtoService = SpringContext.getBean(ProdutoService.class);
    private final VendaService vendaService = SpringContext.getBean(VendaService.class);
    private final CaixaService caixaService = SpringContext.getBean(CaixaService.class);

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        Label lblTitle = new Label("Dashboard Origem Açaí");
        lblTitle.setFont(new Font("Arial", 24));
        lblTitle.setPadding(new Insets(10));
        borderPane.setTop(lblTitle);
        BorderPane.setAlignment(lblTitle, Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20));

        Button btnProdutos = createButton("Cadastro Produtos", "icons/produtos.png");
        Button btnEstoque = createButton("Gestão Estoque", "icons/estoque.png");
        Button btnVendas = createButton("Registro Vendas", "icons/vendas.png");
        Button btnCaixa = createButton("Controle Caixa", "icons/caixa.png");
        Button btnRelatorios = createButton("Gerar Relatórios", "icons/relatorios.png");
        Button btnExportPDF = createButton("Exportar PDF", "icons/pdf.png");
        Button btnLogout = createButton("Sair", "icons/logout.png");

        gridPane.add(btnProdutos, 0, 0);
        gridPane.add(btnEstoque, 1, 0);
        gridPane.add(btnVendas, 0, 1);
        gridPane.add(btnCaixa, 1, 1);
        gridPane.add(btnRelatorios, 0, 2);
        gridPane.add(btnExportPDF, 1, 2);
        gridPane.add(btnLogout, 0, 3, 2, 1);

        borderPane.setCenter(gridPane);

        btnProdutos.setOnAction(e -> {
            TelaCadastroProduto tela = new TelaCadastroProduto();
            Stage stage = new Stage();
            try {
                tela.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnEstoque.setOnAction(e -> {
            TelaRelatorioEstoque tela = new TelaRelatorioEstoque();
            tela.setProdutoService(produtoService);
            Stage stage = new Stage();
            try {
                tela.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnVendas.setOnAction(e -> {
            TelaRelatorioVendas tela = new TelaRelatorioVendas();
            tela.setVendaService(vendaService);
            Stage stage = new Stage();
            try {
                tela.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnCaixa.setOnAction(e -> {
            TelaCaixa tela = new TelaCaixa();
            tela.setCaixaService(caixaService);
            Stage stage = new Stage();
            try {
                tela.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnRelatorios.setOnAction(e -> {
            TelaRelatorioCaixa tela = new TelaRelatorioCaixa();
            tela.setCaixaService(caixaService);
            Stage stage = new Stage();
            try {
                tela.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnExportPDF.setOnAction(e -> System.out.println("Funcionalidade de exportar PDF ainda será implementada."));
        btnLogout.setOnAction(e -> Platform.exit());

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Origem Açaí - Dashboard Principal");
        primaryStage.show();
    }

    private Button createButton(String text, String iconPath) {
        Button button = new Button(text);
        button.setPrefSize(200, 100);
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/" + iconPath)));
        icon.setFitHeight(40);
        icon.setFitWidth(40);
        button.setGraphic(icon);
        button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        return button;
    }
}

