package com.example.insurance.services;

import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.data.requestdto.UserRegistrationInputDTO;
import com.example.insurance.exceptions.UserNotFoundException;
import com.example.insurance.data.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Find user by email user entity.
     *
     * @param email the email
     * @return the user entity
     */
    public UserEntity getUserEntityByEmail(String email) {
        return userRepository.getByEmail(email);
    }


    /**
     * Add new user.
     *
     * @param userRegistrationInputDTO the user registration input
     */
    public void addNewUser(UserRegistrationInputDTO userRegistrationInputDTO) {
        Optional<UserEntity> dbUserByEmail = userRepository.findByEmailOrTelephoneNumber(
                userRegistrationInputDTO.getEmail(),
                userRegistrationInputDTO.getTelephoneNumber()
        );
        if (dbUserByEmail.isEmpty()) {
            UserEntity user = new UserEntity()
                    .setEmail(userRegistrationInputDTO.getEmail())
                    .setFullName(userRegistrationInputDTO.getFullName())
                    .setTelephoneNumber(userRegistrationInputDTO.getTelephoneNumber())
                    .setPassword(bCryptPasswordEncoder.encode(userRegistrationInputDTO.getPassword()))
                    .setRoles(Collections.singleton(userRegistrationInputDTO.getRole()));
            userRepository.save(user);
        } else {
            throw new UnsupportedOperationException();
        }
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
        } else
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
