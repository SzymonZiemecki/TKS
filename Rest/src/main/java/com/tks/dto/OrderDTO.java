package com.tks.dto;

import com.tks.data.user.CartItemEnt;
import com.tks.dto.user.UserDTO;
import com.tks.model.Order;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID id;
    private UserDTO customer;
    private AddressDTO address;
    private List<CartItemDTO> items;
    private Date creationDate;
    private boolean isPaid;
    private double discountPercent;
    private boolean isDelivered;
    private Date deliveryDate;
    private Double price;

    @SneakyThrows
    public static OrderDTO orderToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCustomer(UserDTO.userToDTO(order.getCustomer()));
        orderDTO.setItems(order.getItems().stream().map(CartItemDTO::cartItemToDTO).collect(Collectors.toList()));
        orderDTO.setAddress(AddressDTO.addressToDTO(order.getAddress()));
        orderDTO.setCreationDate(order.getCreationDate());
        orderDTO.setPaid(order.isPaid());
        orderDTO.setDiscountPercent(order.getDiscountPercent());
        orderDTO.setDelivered(order.isDelivered());
        orderDTO.setPrice(order.getPrice());
        return orderDTO;
    }

    @SneakyThrows
    public static Order orderDTOToDomainModel(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setCustomer(UserDTO.userDTOToDomainModel(orderDTO.getCustomer()));
        order.setItems(orderDTO.getItems().stream().map(CartItemDTO::cartItemDTOToDomainModel).collect(Collectors.toList()));
        order.setAddress(AddressDTO.addressDTOToDomainModel(orderDTO.getAddress()));
        order.setCreationDate(orderDTO.getCreationDate());
        order.setPaid(orderDTO.isPaid());
        order.setDiscountPercent(orderDTO.getDiscountPercent());
        order.setDelivered(orderDTO.isDelivered());
        order.setPrice(orderDTO.getPrice());
        return order;
    }
}
