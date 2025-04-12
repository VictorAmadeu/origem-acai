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

// Importa o serviço de caixa, utilizado para registrar entradas de dinheiro
import com.origemacai.service.CaixaService;

// Importa a anotação que permite que o Spring injete automaticamente os objetos
import org.springframework.beans.factory.annotation.Autowired;

// Marca esta classe como um componente de serviço do Spring
import org.springframework.stereotype.Service;

// Importa a classe BigDecimal, usada para representar valores monetários com precisão
import java.math.BigDecimal;

// Importa a classe List, para retornar listas de objetos
import java.util.List;

// Indica que esta é uma classe de serviço (regra de negócio), gerenciada pelo Spring
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

    // Método público que registra uma venda com base no ID do produto, quantidade e cliente
    public Venda registrarVenda(Long produtoId, int quantidade, String cliente) {

        // Busca o produto pelo ID. Se não encontrar, lança uma exceção.
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Validação: impede que seja vendida uma quantidade zero ou negativa
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }

        // Verifica se há estoque suficiente para realizar a venda
        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para a venda.");
        }

        // Calcula o valor total da venda: preço do produto × quantidade vendida
        BigDecimal total = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));

        // Cria uma nova instância de Venda com os dados informados
        Venda venda = new Venda(produto, quantidade, total, cliente);

        // Salva a venda no banco de dados
        vendaRepository.save(venda);

        // Atualiza o estoque do produto, subtraindo a quantidade vendida
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);

        // Salva a atualização de estoque no banco de dados
        produtoRepository.save(produto);

        // Registra no caixa a entrada do valor recebido por essa venda
        caixaService.registrarEntrada(total, "Venda de produto: " + produto.getNome());

        // Retorna a venda registrada para que possa ser usada por quem chamou este método
        return venda;
    }

    // Método público que retorna todas as vendas cadastradas no sistema
    public List<Venda> listarTodas() {
        // Usa o repositório para buscar e retornar todas as vendas no banco
        return vendaRepository.findAll();
    }
}

