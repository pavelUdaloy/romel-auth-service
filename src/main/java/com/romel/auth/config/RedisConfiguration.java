package com.romel.auth.config;

import com.romel.auth.config.RedisConfiguration.MyKeyspaceConfiguration;
import com.romel.auth.config.prop.TokenTtlProperties;
import com.romel.auth.domain.cache.AccessToken;
import com.romel.auth.domain.cache.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.KeyspaceConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories(keyspaceConfiguration = MyKeyspaceConfiguration.class)
public class RedisConfiguration {

    private final TokenTtlProperties tokenTtlProperties;

    @SuppressWarnings("NullableProblems")
    public class MyKeyspaceConfiguration extends KeyspaceConfiguration {

        @Override
        protected Iterable<KeyspaceSettings> initialConfiguration() {
            KeyspaceSettings accessTokenKeyspaceSettings = new KeyspaceSettings(AccessToken.class, "accessToken");
            accessTokenKeyspaceSettings.setTimeToLive(tokenTtlProperties.getAccess());

            KeyspaceSettings refreshTokenKeyspaceSettings = new KeyspaceSettings(RefreshToken.class, "refreshToken");
            refreshTokenKeyspaceSettings.setTimeToLive(tokenTtlProperties.getRefresh());

            return List.of(accessTokenKeyspaceSettings, refreshTokenKeyspaceSettings);
        }
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
