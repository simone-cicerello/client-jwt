package com.fincons.itsle.rest.clientjwt;

import com.fincons.itsle.rest.clientjwt.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientJwtApplication implements CommandLineRunner {

	@Autowired
	private TaskService taskService;

	public static void main(String[] args) {
		SpringApplication.run(ClientJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		taskService.taskOne();
		taskService.taskTwo();
		taskService.taskThree();
		taskService.taskFour();
	}
}
