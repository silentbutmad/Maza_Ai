package com.maza_ai.login_Signup;
import com.maza_ai.profile.UserProfile;
import com.maza_ai.profile.UserProfileRepository;
import com.maza_ai.profile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserProfileService userProfileService;
    @Transactional
    public User addUser(User user) {
        if (userRepository.existsById(user.getEmail())) {
            throw new IllegalArgumentException("User already exists");
        }

        // Create and set user profile
        UserProfile userProfile = new UserProfile(user.getEmail(), user.getName(), user);
        user.setUserProfile(userProfile);

        // Save user (cascade will automatically save the profile)
        User savedUser = userRepository.save(user);

        return savedUser;
    }


    public Boolean loginUser(LoginRequest loginRequest)
    {
       Optional<User> user=userRepository.findById(loginRequest.getUserId());
        if(user.isEmpty())
        {
            return false;
        }
        User user1=user.get();
        if(!user1.getPassword().equals(loginRequest.getPassword()))
        {
            return false;
        }
        return true;
    }

}
