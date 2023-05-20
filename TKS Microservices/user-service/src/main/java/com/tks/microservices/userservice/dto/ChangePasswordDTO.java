package com.tks.microservices.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
