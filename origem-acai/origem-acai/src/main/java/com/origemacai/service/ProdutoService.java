// Define o pacote onde a classe ProdutoService está localizada.
package com.origemacai.service;

// Importa a classe Produto que representa a entidade (modelo do banco).
import com.origemacai.model.Produto;

// Importa a interface ProdutoRepository que se comunica com o banco de dados.
import com.origemacai.repository.ProdutoRepository;

// Importações do Spring para gerenciar dependências e indicar que é um serviço.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importações do Java para manipular listas e objetos opcionais.
import java.util.List;
import java.util.Optional;

// Indica que esta classe será gerenciada pelo Spring como um serviço de negócio.
@Service
public class ProdutoService {

    // Injeção automática do repositório ProdutoRepository.
    @Autowired
    private ProdutoRepository produtoRepository;

    // ✅ Lista todos os produtos cadastrados no banco de dados.
    public List<Produto> listarTodos() {
        return produtoRepository.findAll(); // Método padrão do JpaRepository.
    }

    // ✅ Busca um único produto pelo ID. Retorna Optional (pode ser vazio).
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // ✅ Salva um novo produto no banco, após validar seus dados.
    public Produto salvar(Produto produto) {
        validarProduto(produto); // Valida antes de salvar.
        return produtoRepository.save(produto); // Persiste no banco.
    }

    // ✅ Atualiza um produto existente, substituindo seus campos pelos novos valores.
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setTipo(produtoAtualizado.getTipo());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());

        return produtoRepository.save(produtoExistente); // Salva alterações
    }

    // ✅ NOVO: Método que exclui um produto do banco com base no ID.
    public void excluir(Long id) {
        try {
            produtoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir o produto pois ele está vinculado a uma ou mais vendas.");
        }
    }


    // ✅ Valida os dados do produto antes de salvar/atualizar.
    private void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }

        if (produto.getPreco() == null || produto.getPreco().doubleValue() < 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior ou igual a zero.");
        }

        if (produto.getQuantidadeEstoque() < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa.");
        }
    }

    // ✅ Reduz o estoque após uma venda, garantindo integridade dos dados.
    public void reduzirEstoque(Long produtoId, int quantidadeVendida) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (quantidadeVendida <= 0) {
            throw new IllegalArgumentException("Quantidade vendida deve ser maior que zero.");
        }

        if (produto.getQuantidadeEstoque() < quantidadeVendida) {
            throw new IllegalArgumentException("Estoque insuficiente para a venda.");
        }

        int novoEstoque = produto.getQuantidadeEstoque() - quantidadeVendida;
        produto.setQuantidadeEstoque(novoEstoque);
        produtoRepository.save(produto); // Atualiza estoque
    }
}
