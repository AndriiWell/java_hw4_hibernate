package org.javahw;

import org.javahw.model.User;
import org.javahw.model.Role;
import org.javahw.repository.RoleRepository;
import org.javahw.repository.UserRepository;
import org.javahw.repository.impl.RoleRepositoryImpl;
import org.javahw.repository.impl.UserRepositoryImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        RoleRepository roleRepository = new RoleRepositoryImpl();

        // Add roles to database.
        Role admin = new Role("Admin");
        System.out.println("Role Admin show before save:");
        System.out.println(admin);
        admin = roleRepository.create(admin);
        System.out.println("Role Admin added:");
        System.out.println(admin);
        System.out.println();

        Role staff = new Role();
        staff.setName("Staff");
        System.out.println("Role Staff show before save:");
        System.out.println(staff);
        staff = roleRepository.create(staff);
        System.out.println("Role Staff added:");
        System.out.println(staff);
        System.out.println();

        Role client = new Role("client");
        client.setName("Client");
        System.out.println("Role Client show before save:");
        System.out.println(client);
        client = roleRepository.create(client);
        System.out.println("Role Client added:");
        System.out.println(client);
        System.out.println();

        Role instructor = new Role(null, "Instructor");
        System.out.println("Role Instructor show before changes:");
        System.out.println(instructor);
        instructor.setName("Instructor");
        System.out.println("Role Instructor show before save:");
        System.out.println(instructor);
        instructor = roleRepository.create(instructor);
        System.out.println("Role instructor added:");
        System.out.println(instructor);
        System.out.println();

        Role boss = new Role("Bos");
        System.out.println("Role Boss show before save:");
        System.out.println(boss);
        boss = roleRepository.create(boss);
        System.out.println("Role Boss added:");
        System.out.println(boss);
        System.out.println();

        Role notSavedRole = new Role(null, "notSavedRole");
        System.out.println("Role not saved show before changes:");
        System.out.println(notSavedRole);
        notSavedRole.setName("non saved");
        notSavedRole.setId(1_000_000_000L);
        System.out.println("Role not saved show after changes:");
        System.out.println(notSavedRole);
        System.out.println();

        // List of roles for staff type of users.
        List<Role> rolesetOwner = List.of(staff, instructor);

        // New user.
        User userStaff = new User();
        userStaff.setName("Oll");
        userStaff.setEmail("");
        userStaff.setRoles(rolesetOwner);
        System.out.println("User with staff role before save:");
        System.out.println(userStaff);
        System.out.println();

        UserRepository userRepository = new UserRepositoryImpl();

        // Saving staff to database.
        userStaff = userRepository.create(userStaff);
        System.out.println("User with staff roles saved:");
        System.out.println(userStaff);
        System.out.println();
// Question 1st:
        System.out.println();
        System.out.println("1 role set to user");
        userStaff.setRoles(List.of(staff)); // New set of roles, 2 selects and 1 deletion and 1 insertion.
        userStaff = userRepository.update(userStaff);
        System.out.println("User with staff roles updated:");
        System.out.println(userStaff);
        System.out.println(userStaff.getRoles());

        System.out.println();
        System.out.println("2s role set to user");
        userStaff.setRoles(List.of(staff, admin)); // New set of roles, 3 selects and 1 deletion and 2 insertion(because 2 roles).
        userStaff = userRepository.update(userStaff);
        System.out.println("User with staff roles updated:");
        System.out.println(userStaff);
        System.out.println(userStaff.getRoles());

        System.out.println();
        System.out.println("5 roles set to user");
        userStaff.setRoles(List.of(staff, instructor, client, boss, admin)); // New set of roles, 5 selects and 1 deletion and 5 insertion(because 5 roles).
        userStaff = userRepository.update(userStaff);
        System.out.println("User with staff roles updated:");
        System.out.println(userStaff);
        System.out.println(userStaff.getRoles());
// Questions is: such amount of requests of select or insert without optimization, like - one request to insert data - is normal and I can't configure it?
// It looks like inserts need to execute on one request, and Maybe possible to optimize selects. Should I care about it or it just need to know and it is a small minus of ready solution for fast start?

        // Preparation for next question
        System.out.println();
        System.out.println("2 roles set to user");
        userStaff.setRoles(List.of(staff, instructor));
        userStaff = userRepository.update(userStaff);
        System.out.println("User with staff roles updated:");
        System.out.println(userStaff);
        System.out.println(userStaff.getRoles());

// Question 2nd:
// I want to modify roles set, but not like in previous cases - just to set new set each time when I want to add or remove role:
        // userStaff.setRoles(List.of(staff, instructor, client, boss, admin));
        // or
        // userStaff.setRoles(List.of(staff));
// I want to start from current set of roles, for example user have 5 roles as last change above `List.of(staff, instructor, client, boss, admin)`
// I want to modify this set, for example if user has role `staff` to remove role `instructor` from his set. And I can't do it, see below:
        List<Role> roles = userStaff.getRoles(); // Load current set of roles
        System.out.println("User with staff roles:");
        System.out.println(userStaff);
        System.out.println(roles);
        //roles.remove(instructor); // will throw an error. I think because userStaff.getRoles() returned not correct List.

        // But in such way it works!
        List<Role> rolesetDeveloper = new java.util.ArrayList<>(List.of(admin, staff, client, instructor));
        System.out.println("Initial roles:");
        System.out.println(rolesetDeveloper);
        rolesetDeveloper.remove(client);
        System.out.println("Modified roles(deletion):");
        System.out.println(rolesetDeveloper);
        rolesetDeveloper.add(client);
        System.out.println("Modified roles(adding):");
        System.out.println(rolesetDeveloper);

        System.out.println();
        System.out.println("Deletion a role of user:");
        // So I should make some cover for roles returned from DB to modify list them instead of all time set new List.
        roles = new java.util.ArrayList<>(userStaff.getRoles()); // Added cover class ArrayList
        roles.remove(instructor); // Modifying.
        userStaff.setRoles(roles); // Set new list of roles.
        userStaff = userRepository.update(userStaff); // Save data.
        System.out.println("User with staff roles updated:");
        System.out.println(userStaff);
        System.out.println(userStaff.getRoles());

        System.out.println();
        System.out.println("Adding roles to user:");
        roles = new java.util.ArrayList<>(userStaff.getRoles()); // Added cover class ArrayList
        roles.add(admin); // Modifying.
        roles.add(client);
        roles.add(boss);
        userStaff.setRoles(roles); // Set new list of roles.
        userStaff = userRepository.update(userStaff); // Save data.
        System.out.println("User with staff roles updated:");
        System.out.println(userStaff);
        System.out.println(userStaff.getRoles());
// I think I found answer during question formulation :)
    }

    /**
     * IDEA generated this method,
     * Consumer - as I understand is just covers operation what I wanted to do: one input and no output.
     */
    @NotNull
    @Contract(pure = true)
    private static Consumer<User> showUserData() {
        return user -> {
            System.out.println("User found:");
            System.out.println(user);
            System.out.println("User's roles:");
            System.out.println(user.getRoles());
        };
    }
}