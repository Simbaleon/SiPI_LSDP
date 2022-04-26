package com.example.insurance.Security;

import com.example.insurance.Data.Entities.UserEntity;
import com.example.insurance.Services.UserService;
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
        UserEntity registeredUser = userService.findUserByEmail(email);
        if (registeredUser == null)
            throw new UsernameNotFoundException("Пользователь с таким адресом эл.почты не зарегистрирован");
        return new User(registeredUser.getEmail(), registeredUser.getPassword(), registeredUser.getRoles());
    }
}
