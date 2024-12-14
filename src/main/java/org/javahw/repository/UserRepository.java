package org.javahw.repository;

import org.javahw.model.User;

import java.util.Optional;

public interface UserRepository {
    /**
     * Saves a new user to the database
     * @param user New user data.
     * @return Saved user object.
     */
    User create(User user);

    /**
     * Updates an existing user in the database
     * @param user User's data to modify in database.
     * @return Updated user object.
     */
    User update(User user);

    /**
     * Finds a user by their ID.
     * @param id input field to look for a user.
     * @return User object if found, otherwise empty object.
     */
    Optional<User> findById(Long id);
}
