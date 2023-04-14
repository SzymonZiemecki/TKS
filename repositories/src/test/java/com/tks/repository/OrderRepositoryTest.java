package com.tks.repository;

import com.tks.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import com.tks.data.model.OrderEnt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OrderRepositoryTest extends BaseTest {


    @Test
    void addOrderTest() {
        Assertions.assertEquals(orderEntRepository.findAll().size(), 0);
        orderEntRepository.add(prepareOrder());
        Assertions.assertEquals(orderEntRepository.findAll().size(), 1);
        orderEntRepository.add(prepareOrder());
        Assertions.assertEquals(orderEntRepository.findAll().size(), 2);
        orderEntRepository.add(prepareOrder());
        Assertions.assertEquals(orderEntRepository.findAll().size(), 3);
    }

    @Test
    void deleteOrderTest() {
        OrderEnt orderEnt = prepareOrder();
        Assertions.assertEquals(orderEntRepository.findAll().size(), 0);
        orderEntRepository.add(orderEnt);
        Assertions.assertEquals(orderEntRepository.findAll().size(), 1);
        orderEntRepository.delete(orderEnt.getId());
        Assertions.assertEquals(orderEntRepository.findAll().size(), 0);
        orderEntRepository.add(prepareOrder());
        Assertions.assertEquals(orderEntRepository.findAll().size(), 1);
        orderEntRepository.delete(UUID.randomUUID());
        Assertions.assertEquals(orderEntRepository.findAll().size(), 1);
    }

    @Test
    void updateOrderTest() {
        OrderEnt orderEnt = prepareOrder();
        Assertions.assertEquals(orderEntRepository.findAll().size(), 0);
        orderEntRepository.add(orderEnt);
        Assertions.assertEquals(orderEntRepository.findAll().size(), 1);
        Assertions.assertNotEquals(orderEntRepository.findById(orderEnt.getId()).getPrice(), 1000.0);
        orderEnt.setPrice(1000.0);
        orderEntRepository.update(orderEnt.getId(), orderEnt);
        Assertions.assertEquals(orderEntRepository.findById(orderEnt.getId()).getPrice(), 1000.0);
    }
}
