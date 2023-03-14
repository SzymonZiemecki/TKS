package com.tks.repository;

import com.tks.BaseTest;
import com.tks.data.product.ProductEnt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProductRepositoryTest extends BaseTest {

    @Test
    void addProductTest() {
        Assertions.assertEquals(productEntRepository.findAll().size(), 0);
        productEntRepository.add(prepareTv());
        Assertions.assertEquals(productEntRepository.findAll().size(), 1);
        productEntRepository.add(prepareTv());
        Assertions.assertEquals(productEntRepository.findAll().size(), 2);
        productEntRepository.add(prepareTv());
        Assertions.assertEquals(productEntRepository.findAll().size(), 3);
    }

    @Test
    void deleteProductTest() {
        ProductEnt productEnt = prepareTv();
        Assertions.assertEquals(productEntRepository.findAll().size(), 0);
        productEntRepository.add(productEnt);
        Assertions.assertEquals(productEntRepository.findAll().size(), 1);
        productEntRepository.delete(productEnt.getId());
        Assertions.assertEquals(productEntRepository.findAll().size(), 0);
        productEntRepository.add(prepareTv());
        Assertions.assertEquals(productEntRepository.findAll().size(), 1);
        productEntRepository.delete(UUID.randomUUID());
        Assertions.assertEquals(productEntRepository.findAll().size(), 1);
    }

    @Test
    void updateProductTest() {
        ProductEnt productEnt = prepareTv();
        Assertions.assertEquals(productEntRepository.findAll().size(), 0);
        productEntRepository.add(productEnt);
        Assertions.assertEquals(productEntRepository.findAll().size(), 1);
        Assertions.assertEquals(productEntRepository.findById(productEnt.getId()).get().getPrice(), 20);
        productEnt.setPrice(1000.0);
        productEnt.setProducer("szymonnn");

        productEntRepository.update(productEnt.getId(), productEnt);
        Assertions.assertEquals(productEntRepository.findById(productEnt.getId()).get().getPrice(), 1000.0);
        Assertions.assertEquals(productEntRepository.findById(productEnt.getId()).get().getProducer(), "szymonnn");
    }
}
