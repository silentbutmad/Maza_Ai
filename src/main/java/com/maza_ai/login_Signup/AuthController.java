package com.maza_ai.login_Signup;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;



@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping
public class AuthController {

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // Clears session data, including in-memory chat storage
        return ResponseEntity.ok("Logged out successfully, chat memory cleared.");
    }
}

