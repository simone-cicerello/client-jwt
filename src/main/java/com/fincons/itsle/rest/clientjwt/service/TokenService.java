package com.fincons.itsle.rest.clientjwt.service;

import com.fincons.itsle.rest.clientjwt.config.Configs;
import com.fincons.itsle.rest.clientjwt.model.JwtRequest;
import com.fincons.itsle.rest.clientjwt.model.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class TokenService {

    @Autowired
    private Configs configs;

    private final AtomicReference<String> tokenString = new AtomicReference<>();

    private Optional<JwtResponse> optionalJwt;

    @Autowired
    public TokenService() {
        cleanToken();
    }

    public void cleanToken() {
        optionalJwt = Optional.empty();
    }


    public String getToken()  {
        optionalJwt.ifPresentOrElse(
                jwtResp -> {
                    log.debug("JWT is present...");

                    if (Boolean.TRUE.equals(jwtResp.isNotExpired())) {
                        log.debug("JWT is still valid!");
                        tokenString.set(jwtResp.getJwtToken());
                    } else {
                        log.debug("JWT is NOT valid!");
                        fetchJwt();
                        tokenString.set(optionalJwt.orElseThrow().getJwtToken());
                    }
                },
                () -> {
                    log.debug("JWT is NOT present...");
                    fetchJwt();
                    tokenString.set(optionalJwt.orElseThrow().getJwtToken());
                });
        return tokenString.get();
    }
    private void fetchJwt() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            var accessUri = configs.getServerAuthUrl();
            log.info("Getting JWT from serve {}", accessUri);

            var username = configs.getAuthUsername();
            var password = configs.getAuthPassword();
            var reqBody = new JwtRequest(username, password);
            ResponseEntity<JwtResponse> response = restTemplate.postForEntity(accessUri, reqBody, JwtResponse.class);

            if (response.getStatusCode().value() != 200) {
                optionalJwt = Optional.empty();
            } else {
                optionalJwt = Optional.ofNullable(response.getBody());
            }
        } catch (Exception e){
            log.error("Error during token retrieving");
            e.printStackTrace();
        }
    }
}
