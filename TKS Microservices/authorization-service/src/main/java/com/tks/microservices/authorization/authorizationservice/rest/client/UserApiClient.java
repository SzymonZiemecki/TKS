package com.tks.microservices.authorization.authorizationservice.rest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tks.microservices.authorization.authorizationservice.config.CustomUserDetails;
import com.tks.microservices.authorization.authorizationservice.rest.dto.FindUserResponse;

@FeignClient(name = "user-service")
public interface UserApiClient {

    @GetMapping("/find")
    FindUserResponse findUserForAuthorization(@RequestParam("login") String login);

}
