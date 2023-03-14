package com.tks;

import com.tks.data.model.AddressEnt;
import com.tks.data.model.OrderEnt;
import com.tks.data.product.ProductEnt;
import com.tks.data.product.TvEnt;
import com.tks.data.user.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;

import com.tks.repository.OrderEntRepository;
import com.tks.repository.ProductEntRepository;
import com.tks.repository.UserEntRepository;

import java.util.Date;
import java.util.List;

public class BaseTest {

    protected UserEntRepository userEntRepository;
    protected OrderEntRepository orderEntRepository;
    protected ProductEntRepository productEntRepository;

    @BeforeEach
    public void init() {
        userEntRepository = new UserEntRepository();
        orderEntRepository = new OrderEntRepository();
        productEntRepository = new ProductEntRepository();
    }

    public UserEnt prepareBaseUser() {
        return BaseUserEnt.builder()
                .firstName(generateRandomStringOf(10))
                .lastName(generateRandomStringOf(10))
                .login(generateRandomStringOf(10))
                .password(generateRandomStringOf(10))
                .address(new AddressEnt(generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10)))
                .cart(new CartEnt())
                .suspended(false)
                .accountBalance(20d)
                .build();
    }

    public UserEnt prepareManager() {
        return ManagerEnt.builder()
                .firstName(generateRandomStringOf(10))
                .lastName(generateRandomStringOf(10))
                .login(generateRandomStringOf(10))
                .password(generateRandomStringOf(10))
                .address(new AddressEnt(generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10), generateRandomStringOf(10)))
                .cart(new CartEnt())
                .suspended(false)
                .accountBalance(20d)
                .build();
    }

    public UserEnt prepareAdmin() {
        return AdminEnt.builder()
                .firstName(generateRandomStringOf(10))
                .lastName(generateRandomStringOf(10))
                .login(generateRandomStringOf(10))
                .password(generateRandomStringOf(10))
                .address(prepareAddress())
                .cart(new CartEnt())
                .suspended(false)
                .accountBalance(20d)
                .build();
    }

    public AddressEnt prepareAddress() {
        return AddressEnt.builder()
                .houseNumber(generateRandomStringOf(10))
                .street(generateRandomStringOf(10))
                .country(generateRandomStringOf(10))
                .zipCode(generateRandomStringOf(10))
                .city(generateRandomStringOf(10))
                .build();
    }

    public OrderEnt prepareOrder() {
        return OrderEnt.builder()
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

    public ProductEnt prepareTv() {
        return TvEnt.builder()
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

    public List<CartItemEnt> prepareOrderItems() {
        return List.of(new CartItemEnt(prepareTv(), 5l),
                new CartItemEnt(prepareTv(), 3l));
    }

    private String generateRandomStringOf(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }
}
