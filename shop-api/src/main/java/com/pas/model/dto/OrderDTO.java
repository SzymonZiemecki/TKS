package com.pas.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pas.model.Address;
import com.pas.model.Product.Product;
import com.pas.utils.ItemMapDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.pas.model.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @JsonProperty
    UUID id;
    @JsonProperty
    private UserDTO customer;
    @JsonProperty
    private Address address;
    @JsonProperty
    @JsonDeserialize(keyUsing = ItemMapDeserializer.class)
    private Map<Product, Long> items;
    @JsonProperty
    private Date creationDate;
    @JsonProperty
    private boolean isPaid;
    @JsonProperty
    private double discountPercent;
    @JsonProperty
    private boolean isDelivered;

    public static OrderDTO fromEntityToDTO(Order order) {
        return new OrderDTO(order.getId(), UserDTO.fromEntityToDTO(order.getCustomer()), order.getAddress(), order.getItems(), order.getCreationDate(), order.isPaid(), order.getDiscountPercent(), order.isDelivered());
    }

    public static List<OrderDTO> entityListToDTO(List<Order> orders) {
        return orders.stream().map(OrderDTO::fromEntityToDTO).collect(Collectors.toList());
    }

}
