package com.origemacai.view;

//Importações necessárias
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

public class DashboardView {

 private Stage stage;

 public DashboardView(Stage primaryStage) {
     this.stage = primaryStage;
 }

 public void showDashboard() {
     // Configuração inicial do BorderPane (estrutura principal da tela)
     BorderPane borderPane = new BorderPane();

     // Criação e configuração do cabeçalho
     Label lblTitle = new Label("Dashboard Origem Açaí");
     lblTitle.setFont(new Font("Arial", 24));
     lblTitle.setPadding(new Insets(10));
     borderPane.setTop(lblTitle);
     BorderPane.setAlignment(lblTitle, Pos.CENTER);

     // GridPane para organizar os botões no centro
     GridPane gridPane = new GridPane();
     gridPane.setAlignment(Pos.CENTER);
     gridPane.setHgap(20);  // Espaçamento horizontal
     gridPane.setVgap(20);  // Espaçamento vertical
     gridPane.setPadding(new Insets(20));

     // Criação dos botões com ícones e textos
     Button btnProdutos = createButton("Cadastro Produtos", "icons/produtos.png");
     Button btnEstoque = createButton("Gestão Estoque", "icons/estoque.png");
     Button btnVendas = createButton("Registro Vendas", "icons/vendas.png");
     Button btnCaixa = createButton("Controle Caixa", "icons/caixa.png");
     Button btnRelatorios = createButton("Gerar Relatórios", "icons/relatorios.png");
     Button btnExportPDF = createButton("Exportar PDF", "icons/pdf.png");
     Button btnLogout = createButton("Sair", "icons/logout.png");

     // Posicionando botões no GridPane
     gridPane.add(btnProdutos, 0, 0);
     gridPane.add(btnEstoque, 1, 0);
     gridPane.add(btnVendas, 0, 1);
     gridPane.add(btnCaixa, 1, 1);
     gridPane.add(btnRelatorios, 0, 2);
     gridPane.add(btnExportPDF, 1, 2);
     gridPane.add(btnLogout, 0, 3, 2, 1); // Ocupa duas colunas

     // Define o GridPane no centro do BorderPane
     borderPane.setCenter(gridPane);

     // Define o comportamento do botão Logout
     btnLogout.setOnAction(e -> Platform.exit());

     // Configuração final da cena
     Scene scene = new Scene(borderPane, 800, 600);
     stage.setScene(scene);
     stage.setTitle("Origem Açaí - Dashboard Principal");
     stage.show();
 }

 // Método auxiliar para criar botões padronizados com ícones
 private Button createButton(String text, String iconPath) {
     Button button = new Button(text);
     button.setPrefSize(200, 100);

     // Adiciona um ícone ao botão (garanta que as imagens estejam no diretório correto)
     ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/" + iconPath)));
     icon.setFitHeight(40);
     icon.setFitWidth(40);
     button.setGraphic(icon);

     button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
     return button;
 }
}