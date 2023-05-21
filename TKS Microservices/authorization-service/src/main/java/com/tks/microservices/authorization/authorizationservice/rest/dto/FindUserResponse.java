package com.tks.microservices.authorization.authorizationservice.rest.dto;

import java.util.Set;

import com.tks.microservices.authorization.authorizationservice.config.Permission;

import lombok.Data;

@Data
public class FindUserResponse {
    String login;
    String userPassword;
    String authority;
    boolean active;
}
