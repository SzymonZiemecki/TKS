package com.pas.repository;

import com.pas.model.Address;
import com.pas.model.Cart;
import com.pas.model.User.Admin;
import com.pas.model.User.BaseUser;
import com.pas.model.User.Manager;
import com.pas.model.User.User;

import javax.annotation.PostConstruct;


public class AddressRepository extends IRepositoryImpl<Address>{
    @PostConstruct
    public void init(){
        Address initAddress = new Address("Poland", "Lodz", "Pomorska", "20", "90-001");
        this.add(initAddress);
    }

}
