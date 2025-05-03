// Define o pacote ao qual esta classe pertence
package com.origemacai.view;

// Importações das classes de modelo e serviço utilizadas
import com.origemacai.model.Produto;
import com.origemacai.model.Venda;
import com.origemacai.service.ProdutoService;
import com.origemacai.service.VendaService;

// Importações do JavaFX para construir a interface gráfica
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public class FormularioVenda {

    /**
     * Método estático reutilizável que abre a janela de criação ou edição de venda.
     * Se o parâmetro vendaExistente for null → será um novo cadastro.
     * Se não for null → edição da venda existente.
     *
     * @param vendaService   Serviço de vendas para salvar ou atualizar.
     * @param produtoService Serviço de produtos para listar produtos disponíveis.
     * @param vendaExistente Venda a ser editada, ou null para nova.
     * @param aoSalvar       Callback que será chamado após salvar (ex: atualizar tabela).
     */
    public static void mostrar(VendaService vendaService,
                               ProdutoService produtoService,
                               Venda vendaExistente,
                               Consumer<Venda> aoSalvar) {

        // Cria uma nova janela modal (bloqueia a anterior até ser fechada)
        Stage janela = new Stage();
        janela.setTitle(vendaExistente == null ? "Nova Venda" : "Editar Venda");
        janela.initModality(Modality.APPLICATION_MODAL);

        // Cria um layout de grade para os campos
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Cria rótulo e comboBox para seleção de produto
        Label lblProduto = new Label("Produto:");
        List<Produto> produtos = produtoService.listarTodos(); // Busca os produtos do banco
        ComboBox<Produto> comboProduto = new ComboBox<>(FXCollections.observableArrayList(produtos));
        comboProduto.setPromptText("Selecione um produto");

        // Cria rótulo e campo de entrada para a quantidade
        Label lblQuantidade = new Label("Quantidade:");
        TextField txtQuantidade = new TextField();
        txtQuantidade.setPromptText("Ex: 2");

        // Se for edição, preenche os campos com os dados da venda existente
        if (vendaExistente != null) {
            comboProduto.setValue(vendaExistente.getProduto());
            txtQuantidade.setText(String.valueOf(vendaExistente.getQuantidade()));
        }

        // Cria o botão de salvar (funciona para novo ou editar)
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> {
            Produto produtoSelecionado = comboProduto.getValue();
            String quantidadeStr = txtQuantidade.getText();

            // Validação de campos
            if (produtoSelecionado == null || quantidadeStr.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos obrigatórios", "Preencha todos os campos.");
                return;
            }

            try {
                int quantidade = Integer.parseInt(quantidadeStr);

                if (quantidade <= 0) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Quantidade deve ser maior que zero.");
                    return;
                }

                // Calcula o total da venda (preço * quantidade)
                BigDecimal total = produtoSelecionado.getPreco().multiply(BigDecimal.valueOf(quantidade));

                // Se for uma nova venda, cria uma nova instância
                Venda venda = (vendaExistente == null) ? new Venda() : vendaExistente;

                // Define os dados da venda
                venda.setProduto(produtoSelecionado);
                venda.setQuantidade(quantidade);
                venda.setTotal(total);
                venda.setDataHora(LocalDateTime.now());

                // Salva a venda (novo ou atualiza)
                vendaService.salvar(venda);

                // Informa sucesso e fecha a janela
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Venda salva com sucesso!");
                janela.close();

                // Chama o callback para atualizar a tabela (se foi fornecido)
                if (aoSalvar != null) {
                    aoSalvar.accept(venda);
                }

            } catch (NumberFormatException ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Quantidade inválida.");
            } catch (Exception ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro ao salvar", ex.getMessage());
            }
        });

        // Adiciona os elementos à grade
        grid.add(lblProduto, 0, 0);
        grid.add(comboProduto, 1, 0);
        grid.add(lblQuantidade, 0, 1);
        grid.add(txtQuantidade, 1, 1);
        grid.add(btnSalvar, 1, 2);

        // Cria a cena e exibe a janela
        Scene scene = new Scene(grid, 400, 200);
        janela.setScene(scene);
        janela.showAndWait();
    }

    // Método auxiliar para exibir alertas ao usuário
    private static void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
