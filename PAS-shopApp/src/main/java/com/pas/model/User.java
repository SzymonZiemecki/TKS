package com.pas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class User extends IdTrait {
    private String firstName;
    private String lastName;

    public User(String firstName, String lastName) {
        super(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
