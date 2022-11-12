package com.pas.endpoint;

import com.pas.manager.AddressManager;
import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;
import com.pas.repository.AddressRepository;
import com.pas.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/init")
public class initAPI {
    @Inject
    private AddressManager addressRepository;
    @Inject
    private UserRepository userRepository;

    @POST
    public void init(){
        Address initAddress = new Address("Poland", "Lodz", "Pomorska", "20", "90-001");
        User baseUser = new BaseUser("Szymon", "Ziemecki" ,"baseUser", "testpass", true, initAddress, new Cart(), false, 200d);
        User manager = new Manager("Szymon", "Ziemecki" ,"manager", "testpass", true, initAddress, new Cart(), false, 200d);
        User admin = new Admin("Szymon", "Ziemecki" ,"admin", "testpass", true, initAddress, new Cart(), false, 200d);

        addressRepository.addAddress(initAddress);
        userRepository.add(baseUser);
        userRepository.add(manager);
        userRepository.add(admin);
    }

}
