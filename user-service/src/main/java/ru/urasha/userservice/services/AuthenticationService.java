package ru.urasha.userservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.urasha.JwtTokenUtil;
import ru.urasha.userservice.models.Role;
import ru.urasha.userservice.models.UserAccount;
import ru.urasha.userservice.repositories.UserAccountRepository;

@Service
public class AuthenticationService {

    private final UserAccountRepository userAccountRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserAccountRepository userAccountRepository,
                                 JwtTokenUtil jwtTokenUtil,
                                 PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<String> login(UserAccount userAccount) {
        Optional<UserAccount> foundUser = userAccountRepository.findByUsername(userAccount.getUsername());
        return foundUser.map(account -> jwtTokenUtil.generateToken(account.getUsername()));
    }

    public void register(UserAccount userAccount) throws BadCredentialsException {
        if (userAccountRepository.existsByUsername(userAccount.getUsername())) {
            throw new BadCredentialsException("Такой пользователь уже существует!");
        }

        UserAccount user = new UserAccount();
        user.setUsername(userAccount.getUsername());
        user.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        user.setRole(Role.USER);

        userAccountRepository.save(user);
    }
}
