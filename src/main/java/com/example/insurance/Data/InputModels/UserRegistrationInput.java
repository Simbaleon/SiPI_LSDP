package com.example.insurance.Data.InputModels;

import lombok.Data;

@Data
public class UserRegistrationInput {

    private String fullName;

    private String email;

    private String telephoneNumber;

    private String password;

}
