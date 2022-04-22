package com.example.insurance.Data.Repositories;

import com.example.insurance.Data.Entities.RefreshToken;
import com.example.insurance.Data.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
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
    UserEntity findByEmail(String email);
}
