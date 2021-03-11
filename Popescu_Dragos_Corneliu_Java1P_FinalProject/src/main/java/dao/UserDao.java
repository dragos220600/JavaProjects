package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {
    private PreparedStatement createStatement;
    private PreparedStatement findByUsernameStatement;
    private PreparedStatement findByIDStatement;
    private PreparedStatement findByEmailStatement;
    private PreparedStatement updateUsername;
    private PreparedStatement updateEmail;
    private PreparedStatement updatePassword;

    public UserDao(Connection connection) {

        try {
            createStatement = connection.prepareStatement("INSERT INTO users VALUES (null, ?, ?, ?)");
            findByIDStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            findByUsernameStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            findByEmailStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            updateUsername = connection.prepareStatement("UPDATE users SET username = ? WHERE username = ?");
            updateEmail = connection.prepareStatement("UPDATE users SET email = ? WHERE email = ?");
            updatePassword = connection.prepareStatement("UPDATE users SET parola = ? WHERE parola = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean create(User u) {
        try {
            createStatement.setString(1, u.getUsername());
            createStatement.setString(2, u.getEmail());
            createStatement.setString(3,u.getPassw());
            return createStatement.executeUpdate() != 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Optional<User> findBy(String username) {
        try {
            findByUsernameStatement.setString(1, username);
            ResultSet rs = findByUsernameStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("parola")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        try {
            findByEmailStatement.setString(1, email);
            ResultSet rs = findByEmailStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("parola")
                        ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> findById(int id) {
        try {
            findByIDStatement.setInt(1, id);
            ResultSet rs = findByIDStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(
                        new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("parola")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean updateUser(String oldUsername, String newUsername) {
        try {
            updateUsername.setString(1, newUsername);
            updateUsername.setString(2, oldUsername);

            return updateUsername.executeUpdate() != 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       return false;
    }

    public boolean updateMail(String oldMail, String newMail) {
        try {
            updateEmail.setString(1, newMail);
            updateEmail.setString(2, oldMail);

            return updateEmail.executeUpdate() != 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean updatePassw(String oldPassw, String newPassw) {
        try {
            updatePassword.setString(1, newPassw);
            updatePassword.setString(2, oldPassw);

            return updatePassword.executeUpdate() != 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
