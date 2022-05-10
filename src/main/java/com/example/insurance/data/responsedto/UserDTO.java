package com.example.insurance.data.responsedto;

import com.example.insurance.data.entities.Role;
import com.example.insurance.data.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * The type User dto.
 */
@Data
@AllArgsConstructor
public class UserDTO {

    private String fullName;

    private String email;

    private String telephoneNumber;

    private String description;

    private Set<Role> roles;

    public static UserDTO copyEntityToDTO(UserEntity entity) {
        UserDTO dto = new UserDTO(
                entity.getFullName(),
                entity.getEmail(),
                entity.getTelephoneNumber(),
                entity.getDescription(),
                entity.getRoles()
        );
        return dto;
    }

}
