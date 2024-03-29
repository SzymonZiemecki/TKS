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
@EqualsAndHashCode
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
    public static AddressEnt addressToEntModel(Address address) {
        AddressEnt addressEnt = new AddressEnt();
        addressEnt.setCountry(address.getCountry());
        addressEnt.setCity(address.getCity());
        addressEnt.setZipCode(address.getZipCode());
        addressEnt.setStreet(address.getStreet());
        addressEnt.setHouseNumber(address.getHouseNumber());
        return addressEnt;
    }

    @SneakyThrows
    public static Address addressEntToDomainModel(AddressEnt addressEnt) {
        Address address = new Address();
        address.setCountry(addressEnt.getCountry());
        address.setCity(addressEnt.getCity());
        address.setZipCode(addressEnt.getZipCode());
        address.setStreet(addressEnt.getStreet());
        address.setHouseNumber(addressEnt.getHouseNumber());
        return address;
    }
}
