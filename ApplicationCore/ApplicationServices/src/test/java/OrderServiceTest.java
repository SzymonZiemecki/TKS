import com.tks.User.User;
import com.tks.exceptions.BusinessLogicException;
import com.tks.infrastructure.orders.AddOrderPort;
import com.tks.infrastructure.orders.DeleteOrderPort;
import com.tks.infrastructure.orders.GetOrderPort;
import com.tks.infrastructure.orders.UpdateOrderPort;
import com.tks.infrastructure.users.GetUserPort;
import com.tks.infrastructure.users.UpdateUserPort;
import com.tks.model.Cart;
import com.tks.model.Order;
import com.tks.services.OrderService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import data.user.UserEnt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest extends InjectionTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    GetOrderPort getOrderPort;

    @Mock
    UpdateOrderPort updateOrderPort;

    @Mock
    DeleteOrderPort deleteOrderPort;

    @Mock
    AddOrderPort addOrderPort;

    @Mock
    GetUserPort getUserPort;

    @Mock
    UpdateUserPort updateUserPort;

    @Test
    void shouldCreateOrder() {
        User user = prepareBaseUser();
        user.setId(UUID.randomUUID());
        user.setCart(new Cart(prepareOrderItems()));
        user.setAccountBalance(1000.0);
        Order order = prepareOrder();
        order.setId(UUID.randomUUID());
        order.setCustomer(user);
        given(getUserPort.findUserById(user.getId())).willReturn(user);
        given(addOrderPort.createOrder(any())).willReturn(order);

        assertNotNull(orderService.createOrder(user.getId(), prepareAddress()));
    }

    @Test
    void shouldFailWhenUserIsSuspended() {
        User user = prepareBaseUser();
        user.setId(UUID.randomUUID());
        user.setCart(new Cart(prepareOrderItems()));
        user.setAccountBalance(1000.0);
        user.setSuspended(true);
        Order order = prepareOrder();
        order.setId(UUID.randomUUID());
        order.setCustomer(user);
        given(getUserPort.findUserById(user.getId())).willReturn(user);

        Exception ex = assertThrows(BusinessLogicException.class, () -> orderService.createOrder(user.getId(), prepareAddress()));
        assertTrue(ex.getMessage().contains("suspended"));
    }

    @Test
    void shouldFailWhenUserHaveInsufficientBalance() {
        User user = prepareBaseUser();
        user.setId(UUID.randomUUID());
        user.setCart(new Cart(prepareOrderItems()));
        user.setAccountBalance(1.0);
        Order order = prepareOrder();
        order.setId(UUID.randomUUID());
        order.setCustomer(user);
        given(getUserPort.findUserById(user.getId())).willReturn(user);

        Exception ex = assertThrows(BusinessLogicException.class, () -> orderService.createOrder(user.getId(), prepareAddress()));
        assertTrue(ex.getMessage().contains("enough money"));
    }

    @Test
    void shouldFailWhenQuantityOfItemsIsTooMany() {
        User user = prepareBaseUser();
        user.setId(UUID.randomUUID());
        user.setCart(new Cart(prepareOrderWithTooManyItems()));
        user.setAccountBalance(1000.0);
        Order order = prepareOrder();
        order.setId(UUID.randomUUID());
        order.setCustomer(user);
        given(getUserPort.findUserById(user.getId())).willReturn(user);

        Exception ex = assertThrows(BusinessLogicException.class, () -> orderService.createOrder(user.getId(), prepareAddress()));
        assertTrue(ex.getMessage().contains("Couldn't create order"));
    }
}
