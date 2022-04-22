package com.example.insurance.Data.Repositories;

import com.example.insurance.Data.Entities.RefreshToken;
import com.example.insurance.Data.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Refresh token repository.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Find by token refresh token.
     *
     * @param token the token
     * @return the refresh token
     */
    RefreshToken findByToken(String token);

    /**
     * Delete all by user.
     *
     * @param userEntity the user entity
     */
    void deleteAllByUser(UserEntity userEntity);

    /**
     * Find by user list.
     *
     * @param userEntity the user entity
     * @return the list
     */
    List<RefreshToken> findByUser(UserEntity userEntity);
}
