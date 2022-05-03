package com.example.insurance.Data.Repositories;

import com.example.insurance.Data.Entities.UserEntity;
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

    Optional<UserEntity> findByEmailOrTelephoneNumber(@NonNull String email, @NonNull String telephoneNumber);

    UserEntity getByEmail(@NonNull String email);
}
