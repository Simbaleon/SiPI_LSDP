package com.example.insurance.data.requestdto;

import com.example.insurance.data.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationInputDTO {

    private String fullName;

    private String email;

    private String telephoneNumber;

    private String password;

}
