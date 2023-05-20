package com.tks.microservices.productservice.rest.api;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tks.microservices.productservice.rest.dto.ProductDTO;

public interface ProductApi {
    ResponseEntity getProducts(@RequestParam(name = "producer") Optional<String> producer, @RequestParam("name") Optional<String> name);
    ResponseEntity getProductById(@PathVariable("id") UUID id);
    ResponseEntity addProduct(@Validated ProductDTO product);
    ResponseEntity updateProduct(@PathVariable("id") UUID id, @Validated ProductDTO product);
    ResponseEntity deleteProduct(@PathVariable("id") UUID id);
}
