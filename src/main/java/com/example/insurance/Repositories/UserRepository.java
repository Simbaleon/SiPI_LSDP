package com.example.insurance.Repositories;

import com.example.insurance.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserById(Long id);

    UserEntity findByTelephoneNumber(String telephoneNumber);

    UserEntity findByEmail(String email);
}
