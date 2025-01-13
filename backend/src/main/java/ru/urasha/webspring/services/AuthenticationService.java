package ru.urasha.webspring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.urasha.webspring.models.UserAccount;
import ru.urasha.webspring.repositories.UserAccountRepository;

@Service
public class AuthenticationService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    public AuthenticationService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public Optional<String> login(UserAccount userAccount) {
        Optional<UserAccount> foundUser = userAccountRepository.findByUsername(userAccount.getUsername());
        if (foundUser.isPresent()) {
            // generate jwt for him

            return Optional.of("JWT TOKEN GENERATED");
        } 
        
        return Optional.empty();
    }
}
