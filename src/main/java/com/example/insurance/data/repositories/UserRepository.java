package com.example.insurance.data.repositories;

import com.example.insurance.data.entities.UserEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Find user by id user entity.
     *
     * @param id the id
     * @return the user entity
     */
    UserEntity findUserById(Long id);

    /**
     * Find by email user entity.
     *
     * @param email the email
     * @return the user entity
     */
    Optional<UserEntity> findByEmail(@NonNull String email);

    /**
     * Find by email or telephone number optional.
     *
     * @param email           the email
     * @param telephoneNumber the telephone number
     * @return the optional
     */
    Optional<UserEntity> findByEmailOrTelephoneNumber(@NonNull String email, @NonNull String telephoneNumber);

    /**
     * Gets by email.
     *
     * @param email the email
     * @return the by email
     */
    UserEntity getByEmail(@NonNull String email);
}
