package com.tks.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminDTO extends UserDTO {
    public AdminDTO(final UserDTO dto) {
        super(dto);
    }
}
