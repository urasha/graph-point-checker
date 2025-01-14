package ru.urasha.webspring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.urasha.webspring.models.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    
    Optional<UserAccount> findByUsername(String username);
    boolean existsByUsername(String username);
}
