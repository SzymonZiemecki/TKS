package data.model;

import java.util.Date;
import java.util.List;

import data.user.CartItemEnt;
import data.user.UserEnt;
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
    private Date deliveryDate;
    private Double price;
}
