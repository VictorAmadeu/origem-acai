// Pacote onde a classe está localizada
package com.origemacai.view;

// Importa a entidade Caixa com os dados de cada entrada/saída no caixa
import com.origemacai.model.Caixa;

// Importa o serviço responsável por acessar os dados de caixa
import com.origemacai.service.CaixaService;

// Importa utilitário para exportação de PDF
import com.origemacai.util.PdfRelatorioUtil;

// Importações do JavaFX para elementos gráficos
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Importa classes utilitárias do Java
import java.util.List;

// Define a classe que representa a tela de relatório de caixa
public class TelaRelatorioCaixa {

    // Declaração do serviço de caixa que será usado para buscar os dados
    private CaixaService caixaService;

    // Construtor que recebe o serviço como parâmetro
    public TelaRelatorioCaixa(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    // Método principal para exibir a tela
    public void start() {
        // Cria uma nova janela (Stage) para a tela de relatório
        Stage stage = new Stage();
        stage.setTitle("Relatório de Caixa");

        // Cria uma tabela visual para exibir os dados do caixa
        TableView<Caixa> tabela = new TableView<>();

        // Coluna para o tipo (ENTRADA/SAÍDA)
        TableColumn<Caixa, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        // Coluna para a descrição da movimentação
        TableColumn<Caixa, String> colDesc = new TableColumn<>("Descrição");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        // Coluna para o valor, formatado com "R$"
        TableColumn<Caixa, String> colValor = new TableColumn<>("Valor");
        colValor.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty("R$ " + cellData.getValue().getValor().toString()));

        // Coluna para a data e hora da movimentação
        TableColumn<Caixa, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDataHora().toString()));

        // Adiciona todas as colunas à tabela
        tabela.getColumns().addAll(colTipo, colDesc, colValor, colData);

        // Obtém todos os lançamentos do caixa usando o serviço
        List<Caixa> movimentos = caixaService.getTodosMovimentos();
        tabela.setItems(FXCollections.observableArrayList(movimentos));

        // Cria botão para exportar o conteúdo da tabela para PDF
        Button btnExportarPDF = new Button("Exportar para PDF");

        // Define a ação do botão ao ser clicado
        btnExportarPDF.setOnAction(e -> {
            try {
                // Reutiliza os dados carregados na tabela
                List<Caixa> lista = caixaService.getTodosMovimentos();

                // Chama o método que gera o PDF passando a lista e o nome do arquivo
                PdfRelatorioUtil.gerarRelatorioCaixa(lista, "relatorio-caixa.pdf");

                // Exibe alerta informando sucesso
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportação");
                alert.setHeaderText(null);
                alert.setContentText("PDF gerado com sucesso!");
                alert.showAndWait();

            } catch (Exception ex) {
                // Se ocorrer erro, exibe alerta de erro com a mensagem
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro ao gerar PDF: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        // Cria um layout vertical para organizar os componentes
        VBox layout = new VBox(10); // espaçamento vertical de 10px
        layout.getChildren().addAll(tabela, btnExportarPDF); // adiciona tabela e botão

        // Define a cena da janela com o layout
        stage.setScene(new Scene(layout, 600, 400));

        // Exibe a janela na tela
        stage.show();
    }
}
