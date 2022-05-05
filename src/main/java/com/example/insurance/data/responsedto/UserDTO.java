package com.example.insurance.data.responsedto;

import com.example.insurance.data.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * The type User dto.
 */
@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String email;

    private String fullName;

    private Set<Role> roles;
}
