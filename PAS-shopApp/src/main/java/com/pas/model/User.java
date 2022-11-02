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
    @JsonProperty
    private Address address;
    @JsonProperty
    private Cart cart;
}
