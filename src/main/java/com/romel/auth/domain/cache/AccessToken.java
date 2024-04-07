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

@RedisHash("accessToken")
@TypeAlias("accessToken")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class AccessToken {

    @Id
    private UUID uuid;
    @Indexed
    private String login;
    @Indexed
    private String token;
}
