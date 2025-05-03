// Define que esta classe está dentro do pacote de serviços da aplicação
package com.origemacai.service;

// Importa a entidade Produto, usada para representar os produtos no sistema
import com.origemacai.model.Produto;

// Importa a entidade Venda, usada para registrar e consultar vendas
import com.origemacai.model.Venda;

// Importa o repositório responsável por operações com produtos no banco de dados
import com.origemacai.repository.ProdutoRepository;

// Importa o repositório responsável por operações com vendas no banco de dados
import com.origemacai.repository.VendaRepository;

// Importa o serviço de caixa, utilizado para registrar entradas de dinheiro no sistema financeiro
import com.origemacai.service.CaixaService;

// Importa a anotação que permite que o Spring injete automaticamente as dependências necessárias
import org.springframework.beans.factory.annotation.Autowired;

// Marca esta classe como um componente de serviço do Spring, permitindo injeção e gerenciamento de dependências
import org.springframework.stereotype.Service;

// Importa a classe BigDecimal, usada para representar valores monetários com precisão
import java.math.BigDecimal;

// Importa a classe List, utilizada para armazenar e retornar listas de objetos
import java.util.List;

// Indica que esta é uma classe de serviço (regra de negócio), gerenciada pelo contêiner do Spring Boot
@Service
public class VendaService {

    // Injeta automaticamente o repositório de vendas (vendaRepository)
    @Autowired
    private VendaRepository vendaRepository;

    // Injeta automaticamente o repositório de produtos (produtoRepository)
    @Autowired
    private ProdutoRepository produtoRepository;

    // Injeta automaticamente o serviço de caixa (caixaService)
    @Autowired
    private CaixaService caixaService;

    // Método que registra uma nova venda com base no ID do produto, quantidade e cliente
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

        caixaService.registrarEntrada(total, "Venda de produto: " + produto.getNome());

        return venda;
    }

    // Método que retorna todas as vendas cadastradas
    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    // Método que salva uma venda recebida como objeto completo
    public void salvar(Venda venda) {
        vendaRepository.save(venda);

        Produto produto = venda.getProduto();
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - venda.getQuantidade());
        produtoRepository.save(produto);

        caixaService.registrarEntrada(venda.getValorTotal(), "Venda direta: " + produto.getNome());
    }

    // ✅ NOVO MÉTODO ADICIONADO
    // Método que exclui uma venda com base no ID
    public void deletar(Long id) {
        // Verifica se a venda existe no banco
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        // (Opcional) Poderia reverter o estoque se desejado

        // Remove a venda do banco de dados
        vendaRepository.deleteById(id);
    }
}

