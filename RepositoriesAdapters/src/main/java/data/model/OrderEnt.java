package data.model;

import com.tks.model.Order;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEnt extends IdTraitEnt {
    private UserEnt customer;
    private AddressEnt address;
    private List<CartItemEnt> items;
    private Date creationDate;
    private boolean isPaid;
    private double discountPercent;
    private boolean isDelivered;

    public static OrderEnt toOrderEnt(Order order) {
        return OrderEnt.builder()
                .address(AddressEnt.toAddressEnt(order.getAddress()))
                .customer(order.getCustomer())
                .items(order.getItems())
                .creationDate(order.getCreationDate())
                .isPaid(order.isPaid())
                .discountPercent(order.getDiscountPercent())
                .isDelivered(order.isDelivered())
                .build();
    }
}
