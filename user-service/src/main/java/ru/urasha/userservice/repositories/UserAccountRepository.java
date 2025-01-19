package ru.urasha.userservice.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import ru.urasha.userservice.models.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
    
    Optional<UserAccount> findByUsername(String username);
    boolean existsByUsername(String username);
}
