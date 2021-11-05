package com.example.insurance.Data.Repositories;

import com.example.insurance.Data.Entities.RefreshToken;
import com.example.insurance.Data.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByToken(String token);

    void deleteAllByUser(UserEntity userEntity);

    List<RefreshToken> findByUser(UserEntity userEntity);
}
