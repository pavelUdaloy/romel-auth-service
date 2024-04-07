package com.romel.auth.repo;

import com.romel.auth.domain.cache.AccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, UUID> {

    Optional<AccessToken> findByLoginAndToken(String login, String token);
}