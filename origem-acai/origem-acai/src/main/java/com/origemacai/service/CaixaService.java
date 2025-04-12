// Define que esta classe faz parte do pacote de serviços da aplicação
package com.origemacai.service;

// Importa a classe Caixa, que representa os lançamentos financeiros no caixa
import com.origemacai.model.Caixa;

// Importa o repositório responsável por acessar os dados da entidade Caixa no banco
import com.origemacai.repository.CaixaRepository;

// Importa a anotação @Autowired que permite ao Spring injetar automaticamente dependências
import org.springframework.beans.factory.annotation.Autowired;

// Indica que esta classe é um serviço, gerenciado pelo Spring Framework
import org.springframework.stereotype.Service;

// Importa a classe BigDecimal, usada para trabalhar com valores monetários com precisão
import java.math.BigDecimal;

// Importa a classe List, para representar listas de lançamentos no caixa
import java.util.List;

// Define a classe pública CaixaService, responsável pela lógica de controle financeiro
@Service
public class CaixaService {

    // Injeta automaticamente o repositório CaixaRepository
    @Autowired
    private CaixaRepository caixaRepository;

    // Método público que registra uma entrada de dinheiro no caixa
    public void registrarEntrada(BigDecimal valor, String descricao) {

        // Cria um novo objeto Caixa, definindo o valor, tipo "ENTRADA" e a descrição da operação
        Caixa entrada = new Caixa(valor, "ENTRADA", descricao);

        // Salva essa entrada no banco de dados através do repositório
        caixaRepository.save(entrada);
    }

    // Método que calcula e retorna o saldo atual do caixa
    public BigDecimal getSaldoAtual() {

        // Busca todos os lançamentos financeiros registrados no caixa (entradas e saídas)
        List<Caixa> movimentos = caixaRepository.findAll();

        // Inicializa o saldo com zero (valor inicial do caixa)
        BigDecimal saldo = BigDecimal.ZERO;

        // Percorre todos os lançamentos financeiros
        for (Caixa c : movimentos) {

            // Se for uma entrada, soma ao saldo
            if (c.getTipo().equals("ENTRADA")) {
                saldo = saldo.add(c.getValor());
            }
            // Se for uma saída, subtrai do saldo
            else {
                saldo = saldo.subtract(c.getValor());
            }
        }

        // Retorna o saldo final calculado
        return saldo;
    }

    // Método público que retorna todos os lançamentos financeiros do caixa (entradas e saídas)
    public List<Caixa> getTodosMovimentos() {

        // Retorna a lista completa de movimentos financeiros do banco de dados
        return caixaRepository.findAll();
    }
}
