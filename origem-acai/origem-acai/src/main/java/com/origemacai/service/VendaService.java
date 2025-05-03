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

    // Injeta automaticamente o repositório de vendas (vendaRepository) — responsável por salvar e buscar vendas no banco
    @Autowired
    private VendaRepository vendaRepository;

    // Injeta automaticamente o repositório de produtos (produtoRepository) — utilizado para consultar e atualizar os produtos
    @Autowired
    private ProdutoRepository produtoRepository;

    // Injeta automaticamente o serviço de caixa (caixaService) — usado para registrar entradas de valores no sistema financeiro
    @Autowired
    private CaixaService caixaService;

    // Método público que registra uma nova venda, recebendo como parâmetros: ID do produto, quantidade e nome do cliente
    public Venda registrarVenda(Long produtoId, int quantidade, String cliente) {

        // Busca o produto pelo ID no banco de dados
        // Se o produto não for encontrado, lança uma exceção com a mensagem "Produto não encontrado"
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Validação: verifica se a quantidade informada é menor ou igual a zero
        // Se for, lança uma exceção indicando que a quantidade deve ser maior que zero
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }

        // Verifica se o produto possui estoque suficiente para atender a quantidade da venda
        // Se não tiver, lança uma exceção informando que o estoque é insuficiente
        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para a venda.");
        }

        // Calcula o valor total da venda multiplicando o preço do produto pela quantidade informada
        BigDecimal total = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));

        // Cria uma nova instância da entidade Venda com os dados fornecidos
        Venda venda = new Venda(produto, quantidade, total, cliente);

        // Salva a venda no banco de dados utilizando o repositório
        vendaRepository.save(venda);

        // Atualiza o estoque do produto, subtraindo a quantidade vendida
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);

        // Salva a nova quantidade do produto no banco de dados
        produtoRepository.save(produto);

        // Registra uma nova entrada no caixa, informando o valor total da venda e a descrição
        caixaService.registrarEntrada(total, "Venda de produto: " + produto.getNome());

        // Retorna o objeto Venda recém-criado, para que possa ser utilizado pela camada que chamou este método
        return venda;
    }

    // Método público que retorna uma lista com todas as vendas cadastradas no banco de dados
    public List<Venda> listarTodas() {
        // Consulta todas as vendas armazenadas no banco usando o repositório e retorna a lista
        return vendaRepository.findAll();
    }

}

