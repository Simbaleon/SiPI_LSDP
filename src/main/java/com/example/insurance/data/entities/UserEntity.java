package com.example.insurance.data.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

/**
 * The type User entity.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "users")
public class UserEntity extends BaseEntity {

    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
//    private List<RefreshToken> refreshTokens;

}
