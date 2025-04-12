package com.maza_ai.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    // Create profile when a new user signs up
    public void createProfile(String email, String username) {
        UserProfile profile = new UserProfile(email, username);
        userProfileRepository.save(profile);
    }

    // Fetch profile by email
    public Optional<UserProfile> getProfile(String email) {
        return userProfileRepository.findById(email);
    }

    // Update profile
    public UserProfile updateProfile(String email, String username, String bio, String photoUrl) {
        Optional<UserProfile> existingProfile = userProfileRepository.findById(email);
        if (existingProfile.isPresent()) {
            UserProfile profile = existingProfile.get();
            profile.setUsername(username);
            profile.setBio(bio);
            profile.setPhotoUrl(photoUrl);
            return userProfileRepository.save(profile);
        }
        return null;
    }
}
