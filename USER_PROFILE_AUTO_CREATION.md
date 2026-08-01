# Automatic User Profile Creation Implementation

## Problem
The project had a User table but was not automatically creating UserProfile entries when new users registered.

## Root Cause
1. User and UserProfile entities lacked proper JPA relationship mapping
2. UserService had commented-out user save operation
3. No cascade mechanism to automatically create profile

## Solution Implemented

### 1. Updated User Entity (`src/main/java/com/maza_ai/login_Signup/User.java`)
- Added `@OneToOne` relationship with UserProfile
- Configured `cascade = CascadeType.ALL` for automatic profile creation
- Added getter/setter for userProfile with bidirectional sync

### 2. Updated UserProfile Entity (`src/main/java/com/maza_ai/profile/UserProfile.java`)
- Added `@OneToOne` relationship with User
- Used `@MapsId` to share primary key with User
- Added constructor that accepts User object
- Added getter/setter for user field

### 3. Updated UserService (`src/main/java/com/maza_ai/login_Signup/UserService.java`)
- Uncommented and fixed user save operation
- Created UserProfile before saving user
- Set bidirectional relationship between User and UserProfile
- Leveraged JPA cascade to automatically save profile

## How It Works

When a new user registers via `/addUser` endpoint:

1. UserService creates a new UserProfile with:
   - email: user's email (primary key)
   - username: user's name
   - bio: empty string
   - photoUrl: empty string

2. Sets bidirectional relationship between User and UserProfile

3. Saves User entity with cascade type ALL

4. JPA automatically saves the UserProfile in the same transaction

## Database Schema

### users table
- email (PK)
- name
- password

### user_profile table
- email (PK, FK to users.email)
- username
- bio
- photo_url (LONGTEXT)

## Environment Variables (Render)
The profile creation works with any database configured via:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

## Build Status
✅ Project compiles successfully with `mvnw clean compile`

## Testing
To test the implementation:
1. Start the application
2. POST to `/addUser` with user details
3. Verify both `users` and `user_profile` tables have entries
4. Check that profile has default values (username = user's name, bio = empty, photoUrl = empty)