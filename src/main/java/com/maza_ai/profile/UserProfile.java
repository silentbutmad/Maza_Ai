package com.maza_ai.profile;

import jakarta.persistence.*;

@Entity
public class UserProfile {

    @Id
    private String email; // Same as User email (acts as primary key)

    private String username; // Initially set as User's name

    private String bio; // Default: empty


    @Lob // Use @Lob for LONGTEXT
    @Column(columnDefinition = "LONGTEXT")
    private String photoUrl; // Default: empty

    public UserProfile() {
    }

    public UserProfile(String email, String username) {
        this.email = email;
        this.username = username;
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
}
