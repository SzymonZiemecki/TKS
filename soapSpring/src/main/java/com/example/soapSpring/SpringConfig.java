package com.example.soapSpring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tks.aggregates.ProductRepositoryAdapter;
import com.tks.api.ProductRepository;
import com.tks.repository.ProductEntRepository;
import com.tks.security.ProductRepositoryPort;
import com.tks.services.ProductServiceImpl;
import com.tks.userinterface.ProductService;

@Configuration
public class SpringConfig {

    @Bean
    public ProductService productService() {
        return new ProductServiceImpl();
    }
}
