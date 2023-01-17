package com.pas.controller.Auth;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@SessionScoped
@Named
@Getter
@Setter
public class JwtTokenHolderBean implements Serializable {

    String jwtToken;
}
