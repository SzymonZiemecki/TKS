package com.tks.data.model;

import com.tks.data.user.CartEnt;
import com.tks.model.Address;
import com.tks.model.Cart;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.beanutils.BeanUtils;

@Data
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddressEnt {
    @NotNull
    @Size(min = 2, max = 20)
    private String country;
    @NotNull
    @Size(min = 2, max = 20)
    private String city;
    @NotNull
    @Size(min = 2, max = 20)
    private String street;
    @NotNull
    @Size(min = 1, max = 10)
    private String houseNumber;
    @NotNull
    @Size(min = 6, max = 10)
    private String zipCode;

    @SneakyThrows
    public static AddressEnt cartToEnt(Address address) {
        AddressEnt addressEnt = new AddressEnt();
        BeanUtils.copyProperties(addressEnt, address);

        return addressEnt;
    }

    @SneakyThrows
    public static Address addressEntToDomainModel(AddressEnt addressEnt) {
        Address address = new Address();

        BeanUtils.copyProperties(address, addressEnt);
        return address;
    }
}
