package com.romel.auth.service.security;

import com.romel.auth.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AccountDetails implements UserDetails {

    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static AccountDetails fromAccountToAccountDetails(Account account) {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.login = account.getLogin();
        accountDetails.password = account.getPassword();
        accountDetails.grantedAuthorities = List.of(new SimpleGrantedAuthority(toAuthorityValue("romel_user")));
        return accountDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(grantedAuthorities);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private static String toAuthorityValue(String value) {
        return "ROLE_" + value;
    }
}
