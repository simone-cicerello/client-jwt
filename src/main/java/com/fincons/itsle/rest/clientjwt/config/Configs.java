package com.fincons.itsle.rest.clientjwt.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Configs {

    @Value("${auth.username}")
    private String authUsername;

    @Value("${auth.password}")
    private String authPassword;

    @Value("${restful-server.url.base}")
    private String serverBaseUrl;

    @Value("${restful-server.url.auth}")
    private String serverAuthUrl;
}
