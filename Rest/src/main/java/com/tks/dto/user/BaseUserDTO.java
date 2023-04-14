package com.tks.dto.user;

import com.tks.dto.AddressDTO;

import jakarta.validation.constraints.Size;
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
}
