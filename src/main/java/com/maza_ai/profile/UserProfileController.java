package com.maza_ai.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    // Fetch profile
    @GetMapping("/{email}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable String email) {
        Optional<UserProfile> profile = userProfileService.getProfile(email);
        return profile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update profile
    @PostMapping("/update")
    public ResponseEntity<UserProfile> updateProfile(@RequestBody UserProfile profileUpdate) {
        UserProfile updatedProfile = userProfileService.updateProfile(
                profileUpdate.getEmail(),
                profileUpdate.getUsername(),
                profileUpdate.getBio(),
                profileUpdate.getPhotoUrl()
        );

        if (updatedProfile != null) {
            return ResponseEntity.ok(updatedProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
