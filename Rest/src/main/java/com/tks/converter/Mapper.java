package com.tks.converter;

import com.tks.IdTrait;
import com.tks.Product.Laptop;
import com.tks.Product.MobilePhone;
import com.tks.Product.Product;
import com.tks.Product.Tv;
import com.tks.User.Admin;
import com.tks.User.BaseUser;
import com.tks.User.Manager;
import com.tks.User.User;
import com.tks.data.model.IdTraitEnt;
import com.tks.data.model.OrderEnt;
import com.tks.data.product.LaptopEnt;
import com.tks.data.product.MobilePhoneEnt;
import com.tks.data.product.TvEnt;
import com.tks.data.user.*;
import com.tks.dto.*;
import com.tks.dto.product.MobilePhoneDTO;
import com.tks.dto.product.ProductDTO;
import com.tks.dto.product.TvDTO;
import com.tks.model.Address;
import com.tks.model.Cart;
import com.tks.model.CartItem;
import com.tks.model.Order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {

    private static org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

    public static <S, T> T toDomainModel(S source) {
        return (T) mapper.map(source, toDomainMappings().get(source.getClass()));
    }

    public static <S, T> T toDTOModel(S source) {
        return (T) mapper.map(source, toDTOMappings().get(source.getClass()));
    }

    public static <S,T> List<T> listToDomainModel(List<S> source){
        return (List<T>) source.stream()
                .map( obj -> toDomainModel(obj))
                .collect(Collectors.toList());
    }

    public static <S,T> List<T> listToEntModel(List<S> source){
        return (List<T>) source.stream()
                .map( obj -> toDTOModel(obj))
                .collect(Collectors.toList());
    }

    private static Map<Class, Class> toDTOMappings() {
        return Map.ofEntries
                (Map.entry(MobilePhone.class, MobilePhoneDTO.class),
                        Map.entry(Product.class, ProductDTO.class),
                        Map.entry(Tv.class, TvDTO.class),
                        Map.entry(Address.class, AddressDTO.class),
                        Map.entry(Cart.class, CartDTO.class),
                        Map.entry(CartItem.class, CartItemDTO.class),
                        Map.entry(Order.class, OrderDTO.class),
                        Map.entry(User.class, UserDTO.class));
    }



    private static Map<Class, Class> toDomainMappings() {
        return Map.ofEntries
                (Map.entry(MobilePhoneDTO.class, MobilePhone.class),
                        Map.entry(ProductDTO.class, Product.class),
                        Map.entry(TvDTO.class, Tv.class),
                        Map.entry(AddressDTO.class, Address.class),
                        Map.entry(CartDTO.class, Cart.class),
                        Map.entry(CartItemDTO.class, CartItem.class),
                        Map.entry(OrderDTO.class, Order.class),
                        Map.entry(UserDTO.class, User.class));
    }
}
