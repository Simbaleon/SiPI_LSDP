package com.example.insurance.Controllers;

import com.example.insurance.Data.Entities.UserEntity;
import com.example.insurance.Data.InputModels.UserRegistrationInput;
import com.example.insurance.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type User controller.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/clients")
public class UserController {

    private final UserService userService;

    /**
     * Registration.
     *
     * @param userRegistrationInput the user registration input
     */
    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@RequestBody UserRegistrationInput userRegistrationInput) {
        try {
            userService.addNewUser(userRegistrationInput);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserEntity getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    /**
     * Gets all users.
     *
     * @return the all users
     */
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Edit user entity.
     *
     * @param id         the id
     * @param userEntity the user entity
     * @return the user entity
     */
    @PutMapping("/edit/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntity editUser(@PathVariable Long id,
                               @RequestBody UserEntity userEntity) {
        return userService.editUser(id, userEntity);
    }

    /**
     * Delete user.
     *
     * @param id the id
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {

    }
}
