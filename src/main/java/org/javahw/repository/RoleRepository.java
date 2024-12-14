package org.javahw.repository;

import org.javahw.model.Role;

import java.util.Optional;

public interface RoleRepository {
    /**
     * Saves a new role to the database.
     * @param role
     * @return Saved Role object.
     */
    Role create(Role role);

    /**
     * Finds a role by its ID.
     * @param id input field to look for a user.
     * @return Role object if found, otherwise empty object.
     */
    Optional<Role> findById(Long id);
}
