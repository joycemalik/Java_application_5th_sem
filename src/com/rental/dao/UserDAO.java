// com/rental/dao/UserDAO.java
package com.rental.dao;

import com.rental.model.User;

/**
 * Data Access Object interface for User operations
 */
public interface UserDAO {
    /**
     * Authenticate user with email and password
     * @param email user's email
     * @param password user's password
     * @return User object if login successful, null otherwise
     */
    User login(String email, String password);
    
    /**
     * Register a new user
     * @param user User object containing registration details
     * @return User object with generated ID if successful, null otherwise
     */
    User register(User user);
}
