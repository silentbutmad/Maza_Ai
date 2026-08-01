package com.maza_ai.profile;

import com.maza_ai.login_Signup.User;
import jakarta.persistence.*;

@Entity
public class UserProfile {

    @Id
    private String email; // Same as User email (acts as primary key)

    private String username; // Initially set as User's name

    private String bio; // Default: empty


    @Lob
    private String photoUrl; // Default: empty

    @OneToOne
    @MapsId
    @JoinColumn(name = "email")
    private User user;

    public UserProfile() {
    }

    public UserProfile(String email, String username) {
        this.email = email;
        this.username = username;
        this.bio = "";
        this.photoUrl = "";
    }

    public UserProfile(String email, String username, User user) {
        this.email = email;
        this.username = username;
        this.user = user;
        this.bio = "";
        this.photoUrl = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
