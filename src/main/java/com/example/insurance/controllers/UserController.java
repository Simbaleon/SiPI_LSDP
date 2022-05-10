package com.example.insurance.controllers;

import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.data.requestdto.ChangeUserDescriptionInputDTO;
import com.example.insurance.data.requestdto.UserRegistrationInputDTO;
import com.example.insurance.data.responsedto.UserDTO;
import com.example.insurance.services.UserService;
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
     * @param userRegistrationInputDTO the user registration input
     * @return the response entity
     */
    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@RequestBody UserRegistrationInputDTO userRegistrationInputDTO) {
        try {
            userService.addNewUser(userRegistrationInputDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    @GetMapping("/getUserByUsername")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
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
     * @param dto the dto
     * @return the user entity
     */
    @PatchMapping("/changeUserDescription")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeUserDescription(@RequestBody ChangeUserDescriptionInputDTO dto) {
        userService.changeUserDescription(dto);
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
