package com.fincons.itsle.rest.clientjwt.controller;

import com.fincons.itsle.rest.clientjwt.service.jwt.TaskServiceJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jwt")
public class ClientControllerJwt {

    @Autowired
    private TaskServiceJwt taskServiceJwt;

    @GetMapping("1")
    public void executeTaskOne(){
        taskServiceJwt.taskOne();
    }

    @GetMapping("2")
    public void executeTaskTwo(){
        taskServiceJwt.taskTwo();
    }

    @GetMapping("33")
    public void executeTaskThree(){
        taskServiceJwt.taskThree();
    }

    @GetMapping("4")
    public void executeTaskFour(){
        taskServiceJwt.taskFour();
    }
}
