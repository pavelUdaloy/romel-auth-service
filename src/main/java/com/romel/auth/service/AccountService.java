package com.romel.auth.service;

import com.romel.auth.domain.Account;
import com.romel.auth.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public boolean register(String login, String password) {
        boolean exists = accountRepository.existsByLogin(login);
        if (exists) {
            return false;
        }
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional(readOnly = true)
    public Account findByLogin(String login) {
        Optional<Account> account = accountRepository.findByLogin(login);
        if (account.isEmpty()) {
            throw new UsernameNotFoundException("Cannot find account with login = " + login);
        }
        return account.get();
    }
}
