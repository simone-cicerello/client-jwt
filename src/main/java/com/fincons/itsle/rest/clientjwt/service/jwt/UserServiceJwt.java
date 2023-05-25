package com.fincons.itsle.rest.clientjwt.service.jwt;

import com.fincons.itsle.rest.clientjwt.config.Configs;
import com.fincons.itsle.rest.clientjwt.model.User;
import com.fincons.itsle.rest.clientjwt.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceJwt {

    @Autowired
    private Configs configs;

    @Autowired
    private TokenService tokenService;

    public User findOneById(long id){
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = configs.getServerBaseUrl() + "/" + id;

        var jwt = tokenService.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);

        HttpEntity<String> request = new HttpEntity<>("parameters", headers);

        log.info("Calling: {}...", resourceUrl);
        ResponseEntity<User> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, request, User.class);
        log.info("Response status {}", response.getStatusCode());
        return response.getBody();
    }

    public List<User> findAll(){
        //https://www.baeldung.com/spring-rest-template-list
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = configs.getServerBaseUrl();

        var jwt = tokenService.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);

        HttpEntity<String> request = new HttpEntity<>("parameters", headers);

        log.info("Calling: {}...", resourceUrl);
        ResponseEntity<User[]> returnList = restTemplate.exchange(resourceUrl, HttpMethod.GET, request, User[].class);
        return Arrays.stream(Objects.requireNonNull(returnList.getBody())).toList();
    }

    public User createUser(User newUser) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = configs.getServerBaseUrl();
        log.info("Calling: {}...", resourceUrl);

        var jwt = tokenService.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);

        HttpEntity<User> request = new HttpEntity<>(newUser, headers);

        ResponseEntity<User> response = restTemplate.exchange(resourceUrl, HttpMethod.POST, request, User.class);
        log.info("Response status {}", response.getStatusCode());
        log.info("Created user: {} {} {}",
                Objects.requireNonNull(response.getBody()).getFirstName(),
                Objects.requireNonNull(response.getBody()).getLastName(),
                Objects.requireNonNull(response.getBody()).getEmail());
        return response.getBody();
    }

    public User updateUser(User newUser) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = configs.getServerBaseUrl() + "/" + newUser.getId();

        var jwt = tokenService.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);

        log.info("Calling: {}...", resourceUrl);

        HttpEntity<User> request = new HttpEntity<>(newUser, headers);
        log.info("Updating user: {} {} {}", newUser.getFirstName(), newUser.getLastName(), newUser.getEmail());

        ResponseEntity<User> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, request, User.class);
        log.info("Response status {}", response.getStatusCode());
        log.info("Updated user with id: {}", Objects.requireNonNull(response.getBody()).getId());
        return response.getBody();
    }

    public void deleteUser(long id){
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = configs.getServerBaseUrl() + "/" + id;
        log.info("Calling: {}...", resourceUrl);

        var jwt = tokenService.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);

        HttpEntity<String> request = new HttpEntity<>("parameters", headers);

        restTemplate.exchange(resourceUrl, HttpMethod.DELETE, request, String.class);
    }
}
