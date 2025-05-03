package com.origemacai.config;

import org.springframework.context.ApplicationContext;

// Essa classe vai guardar o ApplicationContext do Spring, para acessarmos em qualquer lugar
public class SpringContext {
    private static ApplicationContext context;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }
}
