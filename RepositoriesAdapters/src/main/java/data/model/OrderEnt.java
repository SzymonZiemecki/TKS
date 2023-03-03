package data.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEnt extends IdTraitEnt {
    private UserEnt customer;
    private AddressEnt address;
    private List<CartItemEnt> items;
    private Date creationDate;
    private boolean isPaid;
    private double discountPercent;
    private boolean isDelivered;
}
