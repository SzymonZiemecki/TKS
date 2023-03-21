package com.tks.dto;

import com.tks.data.model.AddressEnt;
import com.tks.data.model.OrderEnt;
import com.tks.data.user.CartItemEnt;
import com.tks.data.user.UserEnt;
import com.tks.model.Order;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Date;
import java.util.List;

public class OrderDTO {
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
        BeanUtils.copyProperties(orderDTO, order);

        return orderDTO;
    }

    @SneakyThrows
    public static Order orderDTOToDomainModel(OrderDTO orderDTO) {
        Order order = new Order();

        BeanUtils.copyProperties(order, orderDTO);
        return order;
    }
}
