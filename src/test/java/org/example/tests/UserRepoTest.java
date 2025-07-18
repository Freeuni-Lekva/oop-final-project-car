package org.example.tests;

import org.example.car.DBConnector;
import org.example.car.User.Model.User;
import org.example.car.User.Repository.UserRepository;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepoTest {

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
    void testSaveUser() {
        User user = new User(0, "Alice Wonderland", "hashedpass123", false);
        boolean saved = UserRepository.save(user);
        assertTrue(saved);

        List<User> users = UserRepository.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("Alice Wonderland", users.get(0).getFull_name());
    }

    @Test
    void testGetUserById() throws SQLException {
        User user = new User(0, "Bob Builder", "secure123", true);
        UserRepository.save(user);

        User fetched = UserRepository.getAllUsers().get(0);
        int id = fetched.getId();

        User result = UserRepository.getUserById(id);
        assertNotNull(result);
        assertEquals("Bob Builder", result.getFull_name());
        assertTrue(result.isAdmin());
    }

    @Test
    void testGetAllUsers() {
        UserRepository.save(new User(0, "User One", "pw1", false));
        UserRepository.save(new User(0, "User Two", "pw2", true));

        List<User> users = UserRepository.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testDeleteUser() throws SQLException {
        UserRepository.save(new User(0, "ToDelete", "pw", false));
        User user = UserRepository.getAllUsers().get(0);

        UserRepository.deleteUser(user.getId());

        User deleted = UserRepository.getUserById(user.getId());
        assertNull(deleted);
    }

    @Test
    void testFindByFullNameAndPassword() throws SQLException {
        User user = new User(0, "LoginUser", "hashedpw", false);
        UserRepository.save(user);

        User result = UserRepository.findByFullNameAndPassword("LoginUser", "hashedpw");
        assertNotNull(result);
        assertEquals("LoginUser", result.getFull_name());

        User wrong = UserRepository.findByFullNameAndPassword("LoginUser", "wrongpw");
        assertNull(wrong);
    }

    @Test
    void testExistsByFullName() {
        assertFalse(UserRepository.existsByFullName("Ghost"));

        UserRepository.save(new User(0, "Existing", "pw", false));
        assertTrue(UserRepository.existsByFullName("Existing"));
    }

    @Test
    void testSearchUsersByName_PartialAndCaseInsensitive() {
        UserRepository.save(new User(0, "Alice Wonderland", "pw1", false));
        UserRepository.save(new User(0, "Bob Builder", "pw2", true));
        UserRepository.save(new User(0, "Alicia Keys", "pw3", false));
        UserRepository.save(new User(0, "Charlie", "pw4", false));

        var results = UserRepository.searchUsersByName("ali");
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(u -> u.getFull_name().equals("Alice Wonderland")));
        assertTrue(results.stream().anyMatch(u -> u.getFull_name().equals("Alicia Keys")));

        var exact = UserRepository.searchUsersByName("Bob Builder");
        assertEquals(1, exact.size());
        assertEquals("Bob Builder", exact.get(0).getFull_name());

        var none = UserRepository.searchUsersByName("Zebra");
        assertTrue(none.isEmpty());
    }
}
