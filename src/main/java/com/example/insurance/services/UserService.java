package com.example.insurance.services;

import com.example.insurance.data.entities.Order;
import com.example.insurance.data.entities.Role;
import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.data.requestdto.ChangeUserDescriptionInputDTO;
import com.example.insurance.data.requestdto.UserRegistrationInputDTO;
import com.example.insurance.data.responsedto.UserDTO;
import com.example.insurance.exceptions.UserNotFoundException;
import com.example.insurance.data.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                    .setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Gets user.
     *
     * @param email the email
     * @return the user
     */
    public UserDTO getUserByUsername(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElse(null);
        if (userEntity == null)
            throw new UserNotFoundException();
        else
            return UserDTO.copyEntityToDTO(userEntity);
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

    /**
     * Change user description.
     *
     * @param dto the dto
     */
    public void changeUserDescription(ChangeUserDescriptionInputDTO dto) {
        userRepository.findByEmail(dto.getUsername())
                .map(u -> u.setDescription(dto.getDescription()))
                .map(userRepository::save)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * Gets user order responses.
     *
     * @param email the email
     * @return the user order responses
     */
    public List<Long> getUserOrderResponses(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return user.getOrderResponses().stream().map(Order::getId).collect(Collectors.toList());
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
