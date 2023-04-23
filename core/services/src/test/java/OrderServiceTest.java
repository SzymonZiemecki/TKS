//import com.tks.User.User;
//import com.tks.exception.BusinessLogicException;
//import com.tks.model.Cart;
//import com.tks.model.Order;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(MockitoExtension.class)
//public class OrderServiceTest extends InjectionTest {
//    @Test
//    void shouldCreateOrder() {
//        User user = prepareBaseUser();
//        user.setId(UUID.randomUUID());
//        user.setCart(new Cart(prepareOrderItems()));
//        user.setAccountBalance(1000.0);
//        Order order = prepareOrder();
//        order.setId(UUID.randomUUID());
//        order.setCustomer(user);
//        given(userRepository.findById(user.getId())).willReturn(user);
//        given(orderRepository.add(any())).willReturn(order);
//
//        assertNotNull(orderService.createOrder(user.getId(), prepareAddress()));
//    }
//
//    @Test
//    @DisplayName("Should fail when user is suspended")
//    void shouldFailWhenUserIsSuspended() {
//        User user = prepareBaseUser();
//        user.setId(UUID.randomUUID());
//        user.setCart(new Cart(prepareOrderItems()));
//        user.setAccountBalance(1000.0);
//        user.setSuspended(false);
//        Order order = prepareOrder();
//        order.setId(UUID.randomUUID());
//        order.setCustomer(user);
//        given(userRepository.findById(user.getId())).willReturn((user));
//
//        Exception ex = assertThrows(BusinessLogicException.class, () -> orderService.createOrder(user.getId(), prepareAddress()));
//        assertTrue(ex.getMessage().contains("suspended"));
//    }
//
//    @Test
//    void shouldFailWhenUserHaveInsufficientBalance() {
//        User user = prepareBaseUser();
//        user.setId(UUID.randomUUID());
//        user.setCart(new Cart(prepareOrderItems()));
//        user.setAccountBalance(1.0);
//        Order order = prepareOrder();
//        order.setId(UUID.randomUUID());
//        order.setCustomer(user);
//        given(userRepository.findById(user.getId())).willReturn(user);
//
//        Exception ex = assertThrows(BusinessLogicException.class, () -> orderService.createOrder(user.getId(), prepareAddress()));
//        assertTrue(ex.getMessage().contains("enough money"));
//    }
//
//    @Test
//    void shouldFailWhenQuantityOfItemsIsTooMany() {
//        User user = prepareBaseUser();
//        user.setId(UUID.randomUUID());
//        user.setCart(new Cart(prepareOrderWithTooManyItems()));
//        user.setAccountBalance(1000.0);
//        Order order = prepareOrder();
//        order.setId(UUID.randomUUID());
//        order.setCustomer(user);
//        given(userRepository.findById(user.getId())).willReturn(user);
//
//        Exception ex = assertThrows(BusinessLogicException.class, () -> orderService.createOrder(user.getId(), prepareAddress()));
//        assertTrue(ex.getMessage().contains("out of stock"));
//    }
//}
