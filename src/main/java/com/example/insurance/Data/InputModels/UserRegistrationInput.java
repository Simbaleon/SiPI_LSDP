package com.example.insurance.Data.InputModels;

import com.example.insurance.Data.Entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationInput {

    private String fullName;

    private String email;

    private String telephoneNumber;

    private String password;

    private Role role;

}
