package com.origemacai.service;

import com.origemacai.model.Caixa;
import com.origemacai.repository.CaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository caixaRepository;

    public void registrarEntrada(BigDecimal valor, String descricao) {
        Caixa entrada = new Caixa(valor, "ENTRADA", descricao);
        caixaRepository.save(entrada);
    }

    public BigDecimal getSaldoAtual() {
        List<Caixa> movimentos = caixaRepository.findAll();
        BigDecimal saldo = BigDecimal.ZERO;

        for (Caixa c : movimentos) {
            if (c.getTipo().equals("ENTRADA")) {
                saldo = saldo.add(c.getValor());
            } else {
                saldo = saldo.subtract(c.getValor());
            }
        }
        return saldo;
    }
}
