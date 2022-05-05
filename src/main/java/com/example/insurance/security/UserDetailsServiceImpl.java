package com.example.insurance.security;

import com.example.insurance.data.entities.UserEntity;
import com.example.insurance.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The type User details service.
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        UserEntity registeredUser = userService.getUserEntityByEmail(email);
        if (registeredUser == null)
            throw new UsernameNotFoundException("Пользователь с таким адресом эл.почты не зарегистрирован");
        return new User(registeredUser.getEmail(), registeredUser.getPassword(), registeredUser.getRoles());
    }
}
