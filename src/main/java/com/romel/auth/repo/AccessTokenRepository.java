package com.romel.auth.repo;

import com.romel.auth.domain.cache.AccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, UUID> {

    List<AccessToken> findAllByLogin(String login);

    AccessToken findByToken(String token);
}