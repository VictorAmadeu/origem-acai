package com.origemacai.service;

import com.origemacai.model.Produto;
import com.origemacai.model.Venda;
import com.origemacai.repository.ProdutoRepository;
import com.origemacai.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // Registra uma nova venda e atualiza o estoque
    public Venda registrarVenda(Long produtoId, int quantidade, String cliente) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }

        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para a venda.");
        }

        BigDecimal total = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));

        Venda venda = new Venda(produto, quantidade, total, cliente);
        vendaRepository.save(venda);

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        produtoRepository.save(produto);

        return venda;
    }
}
