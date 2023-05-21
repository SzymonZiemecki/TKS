package com.tks.microservices.userservice.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseUserDTO extends UserDTO {
    public BaseUserDTO(UserDTO dto) {
        super(dto);
    }

    public BaseUserDTO(RegisterDTO dto) {
        setFirstName(dto.getFirstName());
        setLastName(dto.getLastName());
        setLogin(dto.getLogin());
        setAddress(dto.getAddress());
        setAccountBalance(100);
    }
}
