package com.tks.dto;

import com.tks.model.Address;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

public class AddressDTO {
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
    public static AddressDTO cartToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        BeanUtils.copyProperties(addressDTO, address);

        return addressDTO;
    }

    @SneakyThrows
    public static Address addressDTOToDomainModel(AddressDTO addressDTO) {
        Address address = new Address();

        BeanUtils.copyProperties(address, addressDTO);
        return address;
    }

}
