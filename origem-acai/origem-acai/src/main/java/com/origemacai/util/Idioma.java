package com.origemacai.util;

import java.util.Locale;
import java.util.ResourceBundle;

// Singleton responsável por gerenciar o idioma atual da aplicação
public class Idioma {

    // Instância única da classe (padrão Singleton)
    private static Idioma instancia;

    // ResourceBundle que contém os textos carregados do arquivo .properties
    private ResourceBundle bundle;

    // Construtor privado (evita criação de instâncias fora da própria classe)
    private Idioma() {
        setLocale("pt", "BR"); // Idioma padrão inicial (Português-BR)
    }

    // Método de acesso à instância única
    public static Idioma getInstance() {
        if (instancia == null) {
            instancia = new Idioma();
        }
        return instancia;
    }

    // Define o idioma e carrega o arquivo correspondente
    public void setLocale(String idioma, String pais) {
        Locale locale = new Locale(idioma, pais);
        bundle = ResourceBundle.getBundle("com.origemacai.i18n.mensagens", locale);
    }

    // Retorna o texto correspondente à chave (de acordo com o idioma atual)
    public String get(String chave) {
        return bundle.getString(chave);
    }
}
