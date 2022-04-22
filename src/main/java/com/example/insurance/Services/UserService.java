package com.example.insurance.Services;

import com.example.insurance.Data.Entities.Role;
import com.example.insurance.Data.Entities.UserEntity;
import com.example.insurance.Exceptions.UserNotFoundException;
import com.example.insurance.Data.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * The type User service.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Find user by email user entity.
     *
     * @param email the email
     * @return the user entity
     */
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Add new user.
     *
     * @param userEntity the user entity
     */
    public void addNewUser(UserEntity userEntity) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Collections.singleton(Role.USER));
        userRepository.save(userEntity);
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    public UserEntity getUser(Long id) {
        UserEntity userEntity = userRepository.findUserById(id);
        if (userEntity == null)
            throw new UserNotFoundException();
        else
            return userEntity;
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Edit user entity.
     *
     * @param id         the id
     * @param userEntity the user entity
     * @return the user entity
     */
    public UserEntity editUser(Long id, UserEntity userEntity) {
        UserEntity userEntityFromDB = userRepository.findUserById(id);
        if (userEntityFromDB != null) {
            userEntityFromDB.setEmail(userEntity.getEmail());
            userEntityFromDB.setFullName(userEntity.getFullName());
            userEntityFromDB.setTelephoneNumber(userEntity.getTelephoneNumber());
            userRepository.save(userEntityFromDB);
            return userEntityFromDB;
        }
        else
            throw new UserNotFoundException();
    }

    /**
     * Delete user.
     *
     * @param id the id
     */
    public void deleteUser(Long id) {
        if (userRepository.findUserById(id) == null)
            throw new UserNotFoundException();
        userRepository.deleteById(id);
    }
}
