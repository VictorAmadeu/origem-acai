package com.origemacai.view;

import com.origemacai.model.Produto;
import com.origemacai.service.ProdutoService;
import com.origemacai.util.PdfRelatorioUtil;
import com.origemacai.config.SpringContext;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TelaRelatorioEstoque extends Application {

    private ObservableList<Produto> listaProdutos;

    private ProdutoService produtoService;

    public void setProdutoService(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Relatório de Estoque");

        if (produtoService == null) {
            produtoService = SpringContext.getBean(ProdutoService.class);
        }

        TableView<Produto> tabela = new TableView<>();
        tabela.setPlaceholder(new Label("Nenhum produto encontrado"));

        // Coluna ID
        TableColumn<Produto, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Coluna Nome
        TableColumn<Produto, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        // Coluna Tipo
        TableColumn<Produto, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        // Coluna Estoque
        TableColumn<Produto, Integer> colEstoque = new TableColumn<>("Estoque");
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque"));

        // Coluna Ações
        TableColumn<Produto, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(5, btnEditar, btnExcluir);

            {
                box.setAlignment(Pos.CENTER);
                btnEditar.setOnAction(e -> {
                    Produto produto = getTableView().getItems().get(getIndex());
                    FormularioProduto.mostrar(produtoService, produto, p -> atualizarTabela());
                });

                btnExcluir.setOnAction(e -> {
                    Produto produto = getTableView().getItems().get(getIndex());
                    if (confirmarExclusao(produto.getNome())) {
                        try {
                            produtoService.excluir(produto.getId());
                            atualizarTabela();
                            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Produto excluído com sucesso!");
                        } catch (RuntimeException ex) {
                            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao excluir", ex.getMessage());
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean vazio) {
                super.updateItem(item, vazio);
                setGraphic(vazio ? null : box);
            }
        });

        tabela.getColumns().addAll(colId, colNome, colTipo, colEstoque, colAcoes);

        listaProdutos = FXCollections.observableArrayList(produtoService.listarTodos());
        tabela.setItems(listaProdutos);

        Button btnExportarPDF = new Button("Exportar para PDF");
        btnExportarPDF.setId("exportar-btn");
        btnExportarPDF.setOnAction(e -> {
            try {
                PdfRelatorioUtil.gerarRelatorioEstoque(listaProdutos, "relatorio-estoque.pdf");
                mostrarAlerta(Alert.AlertType.INFORMATION, "Exportação", "PDF gerado com sucesso!");
            } catch (IOException ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao gerar PDF: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10, tabela, btnExportarPDF);
        layout.setPadding(new Insets(10));
        VBox.setVgrow(tabela, Priority.ALWAYS);

        Scene scene = new Scene(layout, 920, 580);
        scene.getStylesheets().add(getClass().getResource("/styles/telarelatorioestoque.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void atualizarTabela() {
        listaProdutos.setAll(produtoService.listarTodos());
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private boolean confirmarExclusao(String nome) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText(null);
        alert.setContentText("Deseja realmente excluir o produto: " + nome + "?");
        return alert.showAndWait().filter(res -> res == ButtonType.OK).isPresent();
    }
}
