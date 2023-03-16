package com.tks.mapper;

import com.tks.IdTrait;
import com.tks.Product.Laptop;
import com.tks.Product.MobilePhone;
import com.tks.Product.Tv;
import com.tks.User.Admin;
import com.tks.User.BaseUser;
import com.tks.User.Manager;
import com.tks.data.model.AddressEnt;
import com.tks.data.model.IdTraitEnt;
import com.tks.data.product.LaptopEnt;
import com.tks.data.product.MobilePhoneEnt;
import com.tks.data.user.*;
import com.tks.model.Address;
import com.tks.model.Cart;
import com.tks.model.CartItem;
import com.tks.model.Order;
import com.tks.data.model.OrderEnt;
import com.tks.data.product.TvEnt;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityModelMapper {

    private static org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

    public static <S, T> T toDomainModel(S source) {
        return (T) mapper.map(source, toDomainMappings().get(source.getClass()));
    }

    public static <S, T> T toEntModel(S source) {
        return (T) mapper.map(source, toEntMappings().get(source.getClass()));
    }

    public static <S, T> List<T> listToDomainModel(List<S> source) {
        return (List<T>) source.stream()
                .map(obj -> toDomainModel(obj))
                .collect(Collectors.toList());
    }

    public static <S, T> List<T> listToEntModel(List<S> source) {
        return (List<T>) source.stream()
                .map(obj -> toEntModel(obj))
                .collect(Collectors.toList());
    }

    private static Map<Class, Class> toEntMappings() {
        return Map.ofEntries
                (Map.entry(Address.class, AddressEnt.class),
                        Map.entry(IdTrait.class, IdTraitEnt.class),
                        Map.entry(Order.class, OrderEnt.class),
                        Map.entry(Laptop.class, LaptopEnt.class),
                        Map.entry(Tv.class, TvEnt.class),
                        Map.entry(MobilePhone.class, MobilePhoneEnt.class),
                        Map.entry(Admin.class, AdminEnt.class),
                        Map.entry(BaseUser.class, BaseUserEnt.class),
                        Map.entry(Manager.class, ManagerEnt.class),
                        Map.entry(Cart.class, CartEnt.class),
                        Map.entry(CartItem.class, CartItemEnt.class));
    }

    private static Map<Class, Class> toDomainMappings() {
        return Map.ofEntries
                (Map.entry(Address.class, Address.class),
                        Map.entry(IdTraitEnt.class, IdTrait.class),
                        Map.entry(OrderEnt.class, Order.class),
                        Map.entry(LaptopEnt.class, Laptop.class),
                        Map.entry(TvEnt.class, Tv.class),
                        Map.entry(MobilePhoneEnt.class, MobilePhone.class),
                        Map.entry(AdminEnt.class, Admin.class),
                        Map.entry(BaseUserEnt.class, BaseUser.class),
                        Map.entry(ManagerEnt.class, Manager.class),
                        Map.entry(CartEnt.class, Cart.class),
                        Map.entry(CartItemEnt.class, CartItem.class));
    }

}
