package com.tks.microservices.authorization.authorizationservice.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tks.microservices.authorization.authorizationservice.rest.client.UserApiClient;
import com.tks.microservices.authorization.authorizationservice.rest.dto.FindUserResponse;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserApiClient userApiClient;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        FindUserResponse credential = userApiClient.findUserForAuthorization(username);
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setLogin(credential.getLogin());
        customUserDetails.setUserPassword(credential.getUserPassword());
        customUserDetails.setActive(credential.isActive());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(credential.getAuthority()));
        customUserDetails.setAuthorities(authorities);
        return customUserDetails;
    }
}
