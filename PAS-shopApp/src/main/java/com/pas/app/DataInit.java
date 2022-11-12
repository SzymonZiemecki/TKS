package com.pas.app;

import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import com.pas.repository.AddressRepository;
import com.pas.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Startup
@Singleton
public class DataInit {

    @Inject
    private UserRepository userRepository;

    @Inject
    private AddressRepository addressRepository;

    @PostConstruct
    public void init() {
        Address initAddress = new Address("Poland", "Lodz", "Pomorska", "20", "90-001");
        User baseUser = new BaseUser("Szymon", "Ziemecki" ,"baseUser", "testpass", true, initAddress, new Cart(), false, 200d);
        User manager = new Manager("Szymon", "Ziemecki" ,"manager", "testpass", true, initAddress, new Cart(), false, 200d);
        User admin = new Admin("Szymon", "Ziemecki" ,"admin", "testpass", true, initAddress, new Cart(), false, 200d);

        addressRepository.add(initAddress);
        userRepository.add(baseUser);
        userRepository.add(manager);
        userRepository.add(admin);
    }
}
