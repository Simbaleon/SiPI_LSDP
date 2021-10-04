package com.example.insurance.Repositories;

import com.example.insurance.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    User findByTelephoneNumber(String telephoneNumber);
}
