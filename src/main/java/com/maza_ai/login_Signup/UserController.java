package com.maza_ai.login_Signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {

    @Autowired
    UserService  userService;

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user)
    {

        return userService.addUser(user);

    }

    @PostMapping("/loginUser")
    public Boolean loginUser(@RequestBody LoginRequest loginRequest )
    {
        System.out.println("Hi");
        System.out.println(loginRequest.getUserId()+" "+loginRequest.getPassword());
        return userService.loginUser(loginRequest);
    }


}
