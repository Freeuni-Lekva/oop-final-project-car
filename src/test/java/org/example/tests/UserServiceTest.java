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
    void testSaveNewUser_Success() {
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
    void testAuthenticate_Success() {
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
    void testAuthenticate_WrongPassword() {
        UserService.save("FailTest", "rightPass", false);

        User result = UserService.authenticate("FailTest", "wrongPass");

        assertNull(result);
    }

    @Test
    void testAuthenticate_NonExistentUser() {
        User result = UserService.authenticate("GhostUser", "ghostPass");
        assertNull(result);
    }
}
