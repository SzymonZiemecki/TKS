import org.junit.jupiter.api.Test;

import java.util.UUID;

import data.model.OrderEnt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OrderRepositoryTest extends BaseTest {


    @Test
    void addOrderTest() {
        assertEquals(orderEntRepository.findAll().size(), 0);
        orderEntRepository.add(prepareOrder());
        assertEquals(orderEntRepository.findAll().size(), 1);
        orderEntRepository.add(prepareOrder());
        assertEquals(orderEntRepository.findAll().size(), 2);
        orderEntRepository.add(prepareOrder());
        assertEquals(orderEntRepository.findAll().size(), 3);
    }

    @Test
    void deleteOrderTest() {
        OrderEnt orderEnt = prepareOrder();
        assertEquals(orderEntRepository.findAll().size(), 0);
        orderEntRepository.add(orderEnt);
        assertEquals(orderEntRepository.findAll().size(), 1);
        orderEntRepository.delete(orderEnt.getId());
        assertEquals(orderEntRepository.findAll().size(), 0);
        orderEntRepository.add(prepareOrder());
        assertEquals(orderEntRepository.findAll().size(), 1);
        orderEntRepository.delete(UUID.randomUUID());
        assertEquals(orderEntRepository.findAll().size(), 1);
    }

    @Test
    void updateOrderTest() {
        OrderEnt orderEnt = prepareOrder();
        assertEquals(orderEntRepository.findAll().size(), 0);
        orderEntRepository.add(orderEnt);
        assertEquals(orderEntRepository.findAll().size(), 1);
        assertNotEquals(orderEntRepository.findById(orderEnt.getId()).get().getPrice(), 1000.0);
        orderEnt.setPrice(1000.0);
        orderEntRepository.update(orderEnt.getId(), orderEnt);
        assertEquals(orderEntRepository.findById(orderEnt.getId()).get().getPrice(), 1000.0);
    }
}
