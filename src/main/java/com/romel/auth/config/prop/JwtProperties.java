package com.romel.auth.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String key;
    private String tokenHeader;
    private String tokenPrefix;
    private String tokenIssuer;
}
