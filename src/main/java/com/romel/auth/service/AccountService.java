package com.romel.auth.service;

import com.romel.auth.domain.Account;
import com.romel.auth.error.BaseRomelException;
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
    public void register(String login, String password) {
        boolean exists = accountRepository.existsByLogin(login);
        if (exists) {
            throw new BaseRomelException("User with email " + login + " already exists");
        }
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new BaseRomelException("Error per saving new user to db");
        }
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
