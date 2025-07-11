package org.example.tests;

import org.example.car.DBConnector;
import org.example.car.User.Model.User;
import org.example.car.User.Repository.UserRepository;
import org.example.car.User.Service.UserService;
import org.example.car.User.Service.PasswordHashingService;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @BeforeAll
    void setupDatabase() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    full_name VARCHAR(255) NOT NULL,
                    password_hash VARCHAR(255) NOT NULL,
                    is_admin BOOLEAN NOT NULL
                );
            """);
        }
    }

    @AfterEach
    void clearUsers() throws SQLException {
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM users");
        }
    }

    @Test
    void testSaveNewUser_Success() throws SQLException {
        boolean result = UserService.save("Tekla", "mySecret123", false);
        assertTrue(result);

        User user = UserRepository.findByFullNameAndPassword(
                "Tekla",
                PasswordHashingService.hashPassword("mySecret123")
        );

        assertNotNull(user);
        assertEquals("Tekla", user.getFull_name());
        assertFalse(user.isAdmin());
    }

    @Test
    void testSaveExistingUser_Fails() {
        boolean firstSave = UserService.save("Duplicate", "pass1", false);
        assertTrue(firstSave);

        boolean secondSave = UserService.save("Duplicate", "pass2", false);
        assertFalse(secondSave);

        assertEquals(1, UserRepository.getAllUsers().size());
    }

    @Test
    void testAuthenticate_Success() throws SQLException {
        String plainPassword = "AdminPass";
        String hashed = PasswordHashingService.hashPassword(plainPassword);

        User user = new User("Admin", hashed, true);
        UserRepository.save(user);

        User authenticated = UserService.authenticate("Admin", plainPassword);

        assertNotNull(authenticated);
        assertEquals("Admin", authenticated.getFull_name());
        assertTrue(authenticated.isAdmin());
    }

    @Test
    void testAuthenticate_WrongPassword() throws SQLException {
        UserService.save("FailTest", "rightPass", false);

        User result = UserService.authenticate("FailTest", "wrongPass");

        assertNull(result);
    }

    @Test
    void testAuthenticate_NonExistentUser() throws SQLException {
        User result = UserService.authenticate("GhostUser", "ghostPass");
        assertNull(result);
    }

    @Test
    void testGetAllUsers_ReturnsCorrectList() throws SQLException {
        UserService.save("Alice", "pass1", false);
        UserService.save("Bob", "pass2", false);

        assertEquals(2, UserService.getAllUsers().size());

        assertTrue(
                UserService.getAllUsers().stream()
                        .anyMatch(u -> u.getFull_name().equals("Alice"))
        );
    }

    @Test
    void testDeleteUser_RemovesCorrectUser() throws SQLException {
        UserService.save("ToDelete", "pass3", false);
        User user = UserRepository.findByFullNameAndPassword(
                "ToDelete",
                PasswordHashingService.hashPassword("pass3")
        );

        assertNotNull(user);
        boolean deleted = UserService.deleteUser(user.getId());

        assertTrue(deleted);
        assertNull(UserRepository.getUserById(user.getId()));
    }

    @Test
    void testGetUserById_ReturnsCorrectUser() throws SQLException {
        UserService.save("LookupUser", "lookup123", true);
        User user = UserRepository.findByFullNameAndPassword(
                "LookupUser",
                PasswordHashingService.hashPassword("lookup123")
        );

        User fetched = UserService.getUserById(user.getId());
        assertNotNull(fetched);
        assertEquals("LookupUser", fetched.getFull_name());
        assertTrue(fetched.isAdmin());
    }



}
