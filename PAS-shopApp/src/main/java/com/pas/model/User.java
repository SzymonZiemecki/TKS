package com.pas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class User extends IdTrait {
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String login;
    @JsonProperty
    private String password;
    @JsonIgnore
    private List<Role> roles;
    @JsonProperty
    private Boolean active;

    public User(String firstName, String lastName, String login, String password, List<Role> roles, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.roles = roles;
        this.active = isActive;
    }


}
