package com.romel.auth.service.security;

import com.romel.auth.domain.Account;
import com.romel.auth.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final AccountService accountService;

    /**
     * Load admin details by admin login.
     *
     * @param login the username identifying the user whose data is required
     * @return login, password and role
     */
    @Override
    public AccountDetails loadUserByUsername(String login) {
        Account account = accountService.findByLogin(login);
        return AccountDetails.fromAccountToAccountDetails(account);
    }
}
