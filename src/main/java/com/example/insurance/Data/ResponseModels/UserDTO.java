package com.example.insurance.Data.ResponseModels;

import com.example.insurance.Data.Entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String email;

    private String fullName;

    private Set<Role> roles;
}
