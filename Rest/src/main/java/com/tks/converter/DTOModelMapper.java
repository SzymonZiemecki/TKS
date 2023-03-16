package com.tks.converter;

import com.tks.IdTrait;
import com.tks.Product.Laptop;
import com.tks.Product.MobilePhone;
import com.tks.Product.Tv;
import com.tks.User.Admin;
import com.tks.User.BaseUser;
import com.tks.User.Manager;
import com.tks.data.model.AddressEnt;
import com.tks.data.model.IdTraitEnt;
import com.tks.data.model.OrderEnt;
import com.tks.data.product.LaptopEnt;
import com.tks.data.product.MobilePhoneEnt;
import com.tks.data.product.TvEnt;
import com.tks.data.user.*;
import com.tks.dto.CartDTO;
import com.tks.dto.OrderDTO;
import com.tks.dto.product.LaptopDTO;
import com.tks.dto.product.MobilePhoneDTO;
import com.tks.dto.product.TvDTO;
import com.tks.model.Address;
import com.tks.model.Cart;
import com.tks.model.CartItem;
import com.tks.model.Order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DTOModelMapper {

    private static org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

    public static <S, T> T toDomainModel(S source) {
        return (T) mapper.map(source, toDomainMappings().get(source.getClass()));
    }

    public static <S, T> T toDTOModel(S source) {
        return (T) mapper.map(source, toDTOMappings().get(source.getClass()));
    }

    public static <S, T> List<T> listToDomainModel(List<S> source) {
        return (List<T>) source.stream()
                .map(obj -> toDomainModel(obj))
                .collect(Collectors.toList());
    }

    public static <S, T> List<T> listToDTO(List<S> source) {
        return (List<T>) source.stream()
                .map(obj -> toDTOModel(obj))
                .collect(Collectors.toList());
    }

    private static Map<Class, Class> toDTOMappings() {
        return Map.ofEntries
                (Map.entry(Order.class, OrderDTO.class),
                        Map.entry(Laptop.class, LaptopDTO.class),
                        Map.entry(Tv.class, TvDTO.class),
                        Map.entry(MobilePhone.class, MobilePhoneDTO.class));


    }

    private static Map<Class, Class> toDomainMappings() {
        return Map.ofEntries
                (Map.entry(OrderDTO.class, Order.class),
                        Map.entry(LaptopDTO.class, Laptop.class),
                        Map.entry(TvDTO.class, Tv.class),
                        Map.entry(MobilePhoneDTO.class, MobilePhone.class));
    }

}
