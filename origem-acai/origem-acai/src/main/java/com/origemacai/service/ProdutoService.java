package com.origemacai.service;

import com.origemacai.model.Produto;
import com.origemacai.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marca esta classe como um serviço do Spring (componente de lógica de negócio)
public class ProdutoService {

    @Autowired // Injeta automaticamente o repositório no serviço
    private ProdutoRepository produtoRepository;

    // Lista todos os produtos do banco de dados
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // Busca um produto por ID (retorna Optional)
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    // Salva um novo produto após validar seus dados
    public Produto salvar(Produto produto) {
        validarProduto(produto);
        return produtoRepository.save(produto);
    }

    // Atualiza um produto existente (por ID)
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Atualiza os campos
        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setTipo(produtoAtualizado.getTipo());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());

        return produtoRepository.save(produtoExistente);
    }

    // Remove um produto por ID
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    // Validações básicas de negócio
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
}
