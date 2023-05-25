package com.fincons.itsle.rest.clientjwt.controller;

import com.fincons.itsle.rest.clientjwt.service.open.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    private TaskService taskService;

    @GetMapping("1")
    public void executeTaskOne(){
        taskService.taskOne();
    }

    @GetMapping("2")
    public void executeTaskTwo(){
        taskService.taskTwo();
    }

    @GetMapping("3")
    public void executeTaskThree(){
        taskService.taskThree();
    }

    @GetMapping("4")
    public void executeTaskFour(){
        taskService.taskFour();
    }
}
