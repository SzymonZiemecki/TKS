package com.tks.microservices.userservice.repository.model.security;

import java.util.Set;

import lombok.Data;

@Data
public class CustomUserDetails {

    String login;
    String userPassword;
    String authority;
    boolean active;

}
