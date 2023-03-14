import com.tks.Product.Product;
import com.tks.Product.Tv;
import com.tks.User.Admin;
import com.tks.User.BaseUser;
import com.tks.User.Manager;
import com.tks.User.User;
import com.tks.security.OrderRepositoryPort;
import com.tks.security.ProductRepositoryPort;
import com.tks.security.UserRepositoryPort;
import com.tks.model.Address;
import com.tks.model.Cart;
import com.tks.model.CartItem;
import com.tks.model.Order;

import com.tks.services.OrderServiceImpl;
import com.tks.services.ProductServiceImpl;
import com.tks.services.UserServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class InjectionTest {

    @InjectMocks
    protected ProductServiceImpl productService;
    @InjectMocks
    protected OrderServiceImpl orderService;
    @InjectMocks
    protected UserServiceImpl userService;

    @Mock
    protected ProductRepositoryPort productRepository;
    @Mock
    protected UserRepositoryPort userRepository;
    @Mock
    protected OrderRepositoryPort orderRepository;

    public User prepareBaseUser() {
        return BaseUser.builder()
                .firstName(generateRandomStringOf(10))
                .lastName(generateRandomStringOf(10))
                .login(generateRandomStringOf(10))
                .password(generateRandomStringOf(10))
                .address(new Address(generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10)))
                .cart(new Cart(new ArrayList<>()))
                .suspended(false)
                .accountBalance(20d)
                .build();
    }

    public User prepareManager() {
        return Manager.builder()
                .firstName(generateRandomStringOf(10))
                .lastName(generateRandomStringOf(10))
                .login(generateRandomStringOf(10))
                .password(generateRandomStringOf(10))
                .address(new Address(generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10)))
                .cart(new Cart())
                .suspended(false)
                .accountBalance(20d)
                .build();
    }

    public User prepareAdmin() {
        return Admin.builder()
                .firstName(generateRandomStringOf(10))
                .lastName(generateRandomStringOf(10))
                .login(generateRandomStringOf(10))
                .password(generateRandomStringOf(10))
                .address(prepareAddress())
                .cart(new Cart())
                .suspended(false)
                .accountBalance(20d)
                .build();
    }

    public Address prepareAddress() {
        return Address.builder()
                .houseNumber(generateRandomStringOf(10))
                .street(generateRandomStringOf(10))
                .country(generateRandomStringOf(10))
                .zipCode(generateRandomStringOf(10))
                .city(generateRandomStringOf(10))
                .build();
    }

    public Order prepareOrder() {
        return Order.builder()
                .customer(prepareBaseUser())
                .address(prepareAddress())
                .creationDate(new Date())
                .isPaid(false)
                .discountPercent(0)
                .isDelivered(false)
                .deliveryDate(null)
                .price(20d)
                .items(prepareOrderItems()).build();
    }

    public Product prepareTv() {
        return Tv.builder()
                .availableAmount(10)
                .price(20d)
                .name(generateRandomStringOf(10))
                .producer(generateRandomStringOf(10))
                .productDescription(generateRandomStringOf(10))
                .screenSize(generateRandomStringOf(10))
                .resolution(generateRandomStringOf(10))
                .refreshRate(generateRandomStringOf(10))
                .panelType(generateRandomStringOf(10))
                .build();
    }

    public List<CartItem> prepareOrderItems() {
        return List.of(new CartItem(prepareTv(), 5l),
                new CartItem(prepareTv(), 3l));
    }

    public List<CartItem> prepareOrderWithTooManyItems() {
        return List.of(new CartItem(prepareTv(), 5l),
                new CartItem(prepareTv(), 11l));
    }

    private String generateRandomStringOf(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }
}
