// Define o pacote onde a classe ProdutoService está localizada
package com.origemacai.service;

// Importa a classe Produto que representa a entidade/tabela de produtos
import com.origemacai.model.Produto;

// Importa a interface ProdutoRepository responsável pela persistência dos dados
import com.origemacai.repository.ProdutoRepository;

// Importa as anotações do Spring para controle de injeção de dependência e gerenciamento de componentes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa utilitários do Java para uso de listas e valores opcionais
import java.util.List;
import java.util.Optional;

// Define que esta classe é um serviço do Spring (camada de regras de negócio)
@Service
public class ProdutoService {

    // Injeta automaticamente uma instância de ProdutoRepository nesta classe
    @Autowired
    private ProdutoRepository produtoRepository;

    // Retorna uma lista com todos os produtos cadastrados no banco
    public List<Produto> listarTodos() {
        return produtoRepository.findAll(); // Busca todos os registros da tabela 'produto'
    }

    // Busca um produto específico pelo seu ID (retorna Optional, que pode estar vazio)
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id); // Realiza busca no banco usando o ID
    }

    // Salva um novo produto no banco de dados
    public Produto salvar(Produto produto) {
        validarProduto(produto); // Realiza validações antes de persistir
        return produtoRepository.save(produto); // Persiste o produto na base de dados
    }

    // Atualiza um produto existente com novos dados informados
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        // Busca o produto pelo ID e lança exceção se não existir
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Atualiza os atributos do produto com os novos valores
        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setTipo(produtoAtualizado.getTipo());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());

        // Salva as alterações no banco
        return produtoRepository.save(produtoExistente);
    }

    // Remove um produto do banco de dados pelo seu ID
    public void deletar(Long id) {
        produtoRepository.deleteById(id); // Executa o delete pelo ID usando o repositório
    }

    // Método privado que contém validações obrigatórias de um produto
    private void validarProduto(Produto produto) {
        // Verifica se o nome está vazio ou nulo
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }

        // Verifica se o preço é válido (não nulo e maior ou igual a zero)
        if (produto.getPreco() == null || produto.getPreco().doubleValue() < 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior ou igual a zero.");
        }

        // Verifica se a quantidade em estoque é negativa (o que não é permitido)
        if (produto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa.");
        }
    }

    // Reduz automaticamente o estoque de um produto após uma venda
    public void reduzirEstoque(Long produtoId, int quantidadeVendida) {
        // Busca o produto no banco pelo ID e lança exceção se não existir
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se a quantidade informada para venda é válida (maior que 0)
        if (quantidadeVendida <= 0) {
            throw new IllegalArgumentException("Quantidade vendida deve ser maior que zero.");
        }

        // Verifica se existe estoque suficiente para realizar a venda
        if (produto.getQuantidadeEstoque() < quantidadeVendida) {
            throw new IllegalArgumentException("Estoque insuficiente para a venda.");
        }

        // Calcula o novo valor do estoque após a venda
        int novoEstoque = produto.getQuantidadeEstoque() - quantidadeVendida;

        // Atualiza o campo de estoque no objeto Produto
        produto.setQuantidadeEstoque(novoEstoque);

        // Salva a atualização no banco de dados
        produtoRepository.save(produto);
    }
}
