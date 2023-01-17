package com.pas.controller.Auth;

import com.pas.restClient.AuthApiClient;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@ViewScoped
@Named
@Getter
@Setter
public class AuthController implements Serializable {
    String login;
    String password;
    @Inject
    JwtTokenHolderBean jwtTokenHolderBean;
    @Inject
    AuthApiClient authApiClient;

    public String login(){
        String jwtToken = authApiClient.login(login, password);
        jwtTokenHolderBean.setJwtToken(jwtToken);
        return "Start";
    }
}
