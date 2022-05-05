package com.example.insurance.data.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * The type Refresh token.
 */
@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity user;

    private String token;

    @Column(name = "expiration_date")
    private Date expirationDate;

}
