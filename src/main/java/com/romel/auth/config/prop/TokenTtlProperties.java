package com.romel.auth.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("token-ttl")
public class TokenTtlProperties {

    // in seconds
    private Long access;
    private Long refresh;
}
