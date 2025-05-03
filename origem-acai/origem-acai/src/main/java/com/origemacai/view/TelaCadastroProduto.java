package com.origemacai.view; // Define o pacote onde a classe está localizada

// Importações necessárias
import com.origemacai.model.Produto; // Classe que representa o modelo Produto
import com.origemacai.service.ProdutoService; // Serviço responsável pelas operações com Produto
import com.origemacai.util.Idioma; // Utilitário para suporte a múltiplos idiomas
import com.origemacai.config.SpringContext; // Classe para acessar beans configurados via Spring

import javafx.geometry.Insets; // Define espaços (margens internas) nos layouts JavaFX
import javafx.scene.Scene; // Representa a "tela" do JavaFX
import javafx.scene.control.Alert; // Caixa de alerta (informação, erro, etc.)
import javafx.scene.control.Button; // Representa um botão
import javafx.scene.control.Label; // Texto fixo na tela
import javafx.scene.control.TextField; // Campo de texto para entrada do usuário
import javafx.scene.layout.GridPane; // Layout baseado em grade (tabela)
import javafx.stage.Stage; // Janela principal do JavaFX

import java.math.BigDecimal; // Tipo numérico para trabalhar com valores monetários com precisão

public class TelaCadastroProduto { // Classe responsável pela interface de cadastro de produto

    public void start(Stage stageAnterior) { // Método que inicia a tela de cadastro, recebendo a janela anterior

        Idioma idioma = Idioma.getInstance(); // Obtém a instância de idioma atual (Português ou Espanhol)

        // Cria os rótulos da interface com base no idioma
        Label labelNome = new Label(idioma.get("label.nome")); // Ex: "Nome:"
        Label labelTipo = new Label(idioma.get("label.tipo")); // Ex: "Tipo:"
        Label labelPreco = new Label(idioma.get("label.preco")); // Ex: "Preço:"
        Label labelEstoque = new Label(idioma.get("label.quantidade")); // Ex: "Quantidade em Estoque:"

        // Cria os campos de entrada de dados
        TextField campoNome = new TextField(); // Campo para nome do produto
        TextField campoTipo = new TextField(); // Campo para tipo do produto
        TextField campoPreco = new TextField(); // Campo para preço do produto
        TextField campoEstoque = new TextField(); // Campo para quantidade em estoque

        // Cria os botões principais (Salvar e Limpar)
        Button btnSalvar = new Button(idioma.get("botao.salvar")); // Ex: "Salvar"
        Button btnLimpar = new Button(idioma.get("botao.limpar")); // Ex: "Limpar"

        // Acessa o serviço ProdutoService do Spring para salvar no banco de dados
        ProdutoService produtoService = SpringContext.getBean(ProdutoService.class);

        // Define o que acontece ao clicar no botão SALVAR
        btnSalvar.setOnAction(e -> {
            try {
                // Coleta os dados digitados nos campos
                String nome = campoNome.getText(); // Lê o nome digitado
                String tipo = campoTipo.getText(); // Lê o tipo digitado
                BigDecimal preco = new BigDecimal(campoPreco.getText()); // Converte o texto do preço para número
                int quantidade = Integer.parseInt(campoEstoque.getText()); // Converte o texto da quantidade para inteiro

                // Cria um novo objeto Produto com os dados fornecidos
                Produto novo = new Produto(nome, tipo, preco, quantidade);

                // Chama o método do serviço para salvar no banco
                produtoService.salvar(novo);

                // Exibe uma mensagem de sucesso
                mostrarAlerta(idioma.get("mensagem.sucesso"), Alert.AlertType.INFORMATION);

            } catch (Exception ex) {
                // Se der erro (ex: valor inválido), exibe mensagem de erro
                mostrarAlerta(idioma.get("mensagem.erro") + ": " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        // Define a ação ao clicar no botão LIMPAR
        btnLimpar.setOnAction(e -> {
            campoNome.clear(); // Limpa o campo nome
            campoTipo.clear(); // Limpa o campo tipo
            campoPreco.clear(); // Limpa o campo preço
            campoEstoque.clear(); // Limpa o campo estoque
        });

        // Cria o layout da tela (estrutura em grade/tabela)
        GridPane layout = new GridPane(); // Layout com linhas e colunas
        layout.setPadding(new Insets(20)); // Espaço interno em volta do layout
        layout.setVgap(10); // Espaço vertical entre linhas
        layout.setHgap(10); // Espaço horizontal entre colunas

        // Adiciona os componentes à grade nas posições desejadas (coluna, linha)
        layout.add(labelNome, 0, 0);
        layout.add(campoNome, 1, 0);
        layout.add(labelTipo, 0, 1);
        layout.add(campoTipo, 1, 1);
        layout.add(labelPreco, 0, 2);
        layout.add(campoPreco, 1, 2);
        layout.add(labelEstoque, 0, 3);
        layout.add(campoEstoque, 1, 3);
        layout.add(btnSalvar, 0, 4);
        layout.add(btnLimpar, 1, 4);

        // Cria a cena com o layout e define tamanho da janela
        Scene cena = new Scene(layout, 450, 300); // Largura 450, altura 300

        Stage stage = new Stage(); // Cria uma nova janela
        stage.setTitle(idioma.get("titulo.cadastro")); // Define o título da janela com base no idioma
        stage.setScene(cena); // Aplica a cena com os elementos visuais
        stage.show(); // Exibe a nova janela

        stageAnterior.hide(); // Oculta a janela anterior (geralmente a dashboard)
    }

    // Método auxiliar para mostrar alertas ao usuário
    private void mostrarAlerta(String mensagem, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo); // Cria o alerta com tipo (INFORMATION ou ERROR)
        alerta.setHeaderText(null); // Remove o cabeçalho do alerta
        alerta.setContentText(mensagem); // Define o texto principal do alerta
        alerta.showAndWait(); // Exibe o alerta e espera o usuário clicar em "OK"
    }
}


