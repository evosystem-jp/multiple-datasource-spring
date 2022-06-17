package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.db1.entity.User;
import com.example.db1.repository.UserRepository;
import com.example.db2.entity.Task;
import com.example.db2.repository.TaskRepository;

@Controller
@RequestMapping(path = "/")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(path = "/user")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/task")
    public @ResponseBody Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

}
