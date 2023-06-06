package com.fincons.itsle.rest.clientjwt.client;

import com.fincons.itsle.rest.clientjwt.model.User;

import java.util.List;

public interface UserClient {

    public User findOneById(long id);
    public List<User> findAll();
    public User createUser(User newUser);
    public User updateUser(User newUser);
    public void deleteUser(long id);
}
