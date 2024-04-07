package com.romel.auth.domain.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@RedisHash("refreshToken")
@TypeAlias("refreshToken")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class RefreshToken {

    @Id
    private UUID uuid;
    @Indexed
    private String login;
    @Indexed
    private String token;
}
