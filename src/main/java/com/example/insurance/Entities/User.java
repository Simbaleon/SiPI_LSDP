package com.example.insurance.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User extends BaseEntity {

    private String name;

    private String surname;

    private String email;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    private String password;

}
