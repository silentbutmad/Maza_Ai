package com.testdb.testdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;


    public List<test> getAllUsers()
    {
        return userRepository.findAll();
    }

}
