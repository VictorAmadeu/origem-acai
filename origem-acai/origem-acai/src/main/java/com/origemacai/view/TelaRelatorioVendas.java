// Define o pacote ao qual esta classe pertence
package com.origemacai.view;

// Importações das entidades e serviços necessários
import com.origemacai.model.Venda;
import com.origemacai.service.ProdutoService;
import com.origemacai.service.VendaService;
import com.origemacai.util.PdfRelatorioUtil;
import com.origemacai.config.SpringContext;

// Importações JavaFX
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class TelaRelatorioVendas extends Application {

    // Lista observável que alimenta a tabela
    private ObservableList<Venda> listaVendas;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Relatório de Vendas");

        // Obtém os serviços diretamente do contexto do Spring
        VendaService vendaService = SpringContext.getBean(VendaService.class);
        ProdutoService produtoService = SpringContext.getBean(ProdutoService.class);

        // Inicializa a tabela de vendas
        TableView<Venda> tabela = new TableView<>();
        tabela.setPlaceholder(new Label("Nenhuma venda cadastrada"));

        // Coluna ID da venda
        TableColumn<Venda, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Coluna Nome do Produto
        TableColumn<Venda, String> colProduto = new TableColumn<>("Produto");
        colProduto.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getProduto().getNome()
                )
        );

        // Coluna Quantidade
        TableColumn<Venda, Integer> colQtd = new TableColumn<>("Quantidade");
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        // Coluna Data da Venda
        TableColumn<Venda, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getDataHora().toString()
                )
        );

        // Coluna de ações (Editar / Excluir)
        TableColumn<Venda, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox box = new HBox(5, btnEditar, btnExcluir);

            {
                btnEditar.setOnAction(e -> {
                    Venda vendaSelecionada = getTableView().getItems().get(getIndex());
                    FormularioVenda.mostrar(vendaService, produtoService, vendaSelecionada, v -> atualizarTabela(vendaService));
                });

                btnExcluir.setOnAction(e -> {
                    Venda vendaSelecionada = getTableView().getItems().get(getIndex());
                    Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmar.setTitle("Confirmação");
                    confirmar.setHeaderText("Deseja realmente excluir esta venda?");
                    confirmar.setContentText("ID: " + vendaSelecionada.getId() + " | Produto: " + vendaSelecionada.getProduto().getNome());

                    confirmar.showAndWait().ifPresent(resposta -> {
                        if (resposta == ButtonType.OK) {
                            vendaService.deletar(vendaSelecionada.getId());
                            atualizarTabela(vendaService);
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean vazio) {
                super.updateItem(item, vazio);
                if (vazio) {
                    setGraphic(null);
                } else {
                    setGraphic(box);
                }
            }
        });

        // Adiciona todas as colunas à tabela
        tabela.getColumns().addAll(colId, colProduto, colQtd, colData, colAcoes);

        // Inicializa os dados da tabela
        listaVendas = FXCollections.observableArrayList(vendaService.listarTodas());
        tabela.setItems(listaVendas);

        // Botão para adicionar nova venda
        Button btnAdicionar = new Button("Adicionar Venda");
        btnAdicionar.setOnAction(e ->
                FormularioVenda.mostrar(vendaService, produtoService, null, v -> atualizarTabela(vendaService))
        );

        // Botão para exportar relatório em PDF
        Button btnExportarPDF = new Button("Exportar PDF");
        btnExportarPDF.setOnAction(e -> {
            try {
                PdfRelatorioUtil.gerarRelatorioVendas(listaVendas, "relatorio-vendas.pdf");
                mostrarAlerta(Alert.AlertType.INFORMATION, "Exportação", "PDF gerado com sucesso!");
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao gerar PDF: " + ex.getMessage());
            }
        });

        // Agrupamento dos botões
        HBox hboxBotoes = new HBox(10, btnAdicionar, btnExportarPDF);
        hboxBotoes.setPadding(new Insets(10));

        // Layout principal
        VBox layout = new VBox(10, hboxBotoes, tabela);
        layout.setPadding(new Insets(10));
        VBox.setVgrow(tabela, Priority.ALWAYS);

        // Configura e exibe a cena
        Scene scene = new Scene(layout, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Atualiza a tabela após adicionar, editar ou excluir.
     */
    private void atualizarTabela(VendaService vendaService) {
        listaVendas.setAll(vendaService.listarTodas());
    }

    /**
     * Método auxiliar para exibir mensagens de alerta.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
