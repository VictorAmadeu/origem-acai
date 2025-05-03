package com.origemacai.config;

import org.springframework.context.ApplicationContext;

public class SpringContext {

    private static ApplicationContext context;

    // Armazena o contexto principal da aplicação Spring
    public static void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    // Permite obter qualquer bean do Spring usando sua classe
    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }
}
