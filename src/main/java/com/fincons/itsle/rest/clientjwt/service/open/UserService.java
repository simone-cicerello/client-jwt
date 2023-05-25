package com.fincons.itsle.rest.clientjwt.service.open;

import com.fincons.itsle.rest.clientjwt.config.Configs;
import com.fincons.itsle.rest.clientjwt.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserService {

    @Autowired
    private Configs configs;

    public User findOneById(long id){
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = configs.getServerBaseUrl() + "/" + id;
        log.info("Calling: {}...", resourceUrl);

        ResponseEntity<User> response = restTemplate.getForEntity(resourceUrl, User.class);
        log.info("Response status {}", response.getStatusCode());
        return response.getBody();
    }

    public List<User> findAll(){
        //https://www.baeldung.com/spring-rest-template-list
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = configs.getServerBaseUrl();
        log.info("Calling: {}...", resourceUrl);
        User[] returnList = restTemplate.getForObject(resourceUrl, User[].class);
        assert returnList != null;
        return Arrays.stream(returnList).toList();
    }

    public User createUser(User newUser) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = configs.getServerBaseUrl();
        log.info("Calling: {}...", resourceUrl);

        ResponseEntity<User> response = restTemplate.postForEntity(resourceUrl, newUser, User.class);
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
        log.info("Calling: {}...", resourceUrl);

        HttpEntity<User> request = new HttpEntity<>(newUser);
        log.info("Updating user: {} {} {}", newUser.getFirstName(), newUser.getLastName(), newUser.getEmail());

        ResponseEntity<User> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, request, User.class);
        log.info("Response status {}", response.getStatusCode());
        log.info("Updated user with id: {}", response.getBody().getId());
        return response.getBody();
    }

    public void deleteUser(long id){
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = configs.getServerBaseUrl() + "/" + id;
        log.info("Calling: {}...", resourceUrl);

        restTemplate.delete(resourceUrl);
    }
}
