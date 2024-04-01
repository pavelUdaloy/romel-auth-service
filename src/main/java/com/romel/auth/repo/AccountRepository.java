package com.romel.auth.repo;

import com.romel.auth.domain.AccessToken;
import com.romel.auth.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByLogin(String login);
}
