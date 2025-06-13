package com.sidyenni.chatapp.services.auth;

import com.sidyenni.chatapp.exceptions.IncorrectPasswordException;
import com.sidyenni.chatapp.exceptions.UserAlreadyExistsInDBException;
import com.sidyenni.chatapp.exceptions.UserNotFoundInDBException;
import com.sidyenni.chatapp.models.Role;
import com.sidyenni.chatapp.models.User;
import com.sidyenni.chatapp.repositories.RoleRepository;
import com.sidyenni.chatapp.repositories.UserRepository;
import com.sidyenni.chatapp.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           RoleRepository roleRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User signup(String username, String password) throws UserAlreadyExistsInDBException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsInDBException("Username is taken by another user");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        if (roleRepository.findByValue("USER").isEmpty()) {
            roleRepository.save(new Role("USER"));
        }

        Role role = roleRepository.findByValue("USER").get();
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public String login(String username, String password) throws UserNotFoundInDBException, IncorrectPasswordException {
        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundInDBException("User not found"));

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect Password");
        }

        return jwtUtil.generateToken(user.getId(), user.getUsername());
    }
}
