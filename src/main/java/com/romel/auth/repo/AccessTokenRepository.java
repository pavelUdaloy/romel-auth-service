package com.romel.auth.repo;

import com.romel.auth.domain.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
}
