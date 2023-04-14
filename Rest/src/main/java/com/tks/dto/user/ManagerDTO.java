package com.tks.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManagerDTO extends UserDTO {
    public ManagerDTO(final UserDTO dto) {
        super(dto);
    }
}
