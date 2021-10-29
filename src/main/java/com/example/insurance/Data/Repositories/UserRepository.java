package com.example.insurance.Data.Repositories;

import com.example.insurance.Data.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserById(Long id);

    UserEntity findByUsername(String username);
}
