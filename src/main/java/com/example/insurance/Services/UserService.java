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

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addNewUser(UserEntity userEntity) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Collections.singleton(Role.User));
        userRepository.save(userEntity);
    }

    public UserEntity getUser(Long id) {
        UserEntity userEntity = userRepository.findUserById(id);
        if (userEntity == null)
            throw new UserNotFoundException();
        else
            return userEntity;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity editUser(Long id, UserEntity userEntity) {
        UserEntity userEntityFromDB = userRepository.findUserById(id);
        if (userEntityFromDB != null) {
            userEntityFromDB.setName(userEntity.getName());
            userEntityFromDB.setSurname(userEntity.getSurname());
            userEntityFromDB.setEmail(userEntity.getEmail());
            userEntityFromDB.setTelephoneNumber(userEntity.getTelephoneNumber());
            userRepository.save(userEntityFromDB);
            return userEntityFromDB;
        }
        else
            throw new UserNotFoundException();
    }

    public void deleteUser(Long id) {
        if (userRepository.findUserById(id) == null)
            throw new UserNotFoundException();
        userRepository.deleteById(id);
    }
}
