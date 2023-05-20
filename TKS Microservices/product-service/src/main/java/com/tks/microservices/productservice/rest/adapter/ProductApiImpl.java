package com.tks.microservices.productservice.rest.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tks.microservices.productservice.core.model.Product;
import com.tks.microservices.productservice.port.ui.ProductService;
import com.tks.microservices.productservice.rest.dto.ProductDTO;

import jakarta.validation.Valid;

@RestController
public class ProductApiImpl {

    private ProductService productService;

    public ProductApiImpl(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity getProducts(@RequestParam("producer") Optional<String> producer, @RequestParam("name") Optional<String> name) {

        List<Product> productList= productService.getProducts(producer, name);
        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product product: productList) {
            productDTOList.add(ProductDTO.productToDTO(product));
        }

        return ResponseEntity.ok().body(productDTOList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getProductById (@PathVariable("id") UUID id) {
        return ResponseEntity.ok().body(ProductDTO.productToDTO(productService.findById(id)));
    }

    @PostMapping(value = "/create")
    public ResponseEntity addProduct(@Valid @RequestBody ProductDTO product) {
        System.out.println(product);
        Product sdt = ProductDTO.productDTOToDomainModel(product);
        return ResponseEntity.ok().body(productService.addItem(ProductDTO.productDTOToDomainModel(product)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateProduct(@PathVariable("id") UUID id, @Valid ProductDTO product) {
        return ResponseEntity.ok().body(ProductDTO.productToDTO(productService.updateProduct(id, ProductDTO.productDTOToDomainModel(product))));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteProduct(@PathVariable(name = "id") UUID id) {
        productService.removeItem(id);
        return ResponseEntity.ok().build();
    }
}
